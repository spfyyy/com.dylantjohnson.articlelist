package com.dylantjohnson.articlelist;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

class ArticleListView extends FrameLayout {
    private static final String TAG = ArticleListView.class.getCanonicalName();
    private ProgressBar mLoader;
    private RecyclerView mRecycler;
    @Nullable private ArticleService mService;

    ArticleListView(Context context, Consumer<Article> onClickListener) {
        super(context);
        mLoader = new ProgressBar(context);
        mRecycler = new RecyclerView(context);

        addView(mLoader);

        int columns = getResources().getBoolean(R.bool.is_tablet) ? 3 : 2;
        GridLayoutManager manager = new GridLayoutManager(context, columns);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? columns : 1;
            }
        });
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(new ArticleAdapter(onClickListener));
        mRecycler.setVisibility(INVISIBLE);

        addView(mRecycler);

        try {
            mService = new ArticleService(context.getString(R.string.feed_url));
        } catch (Exception ex) {
            Log.w(TAG, ex);
        }
    }

    ArticleListView(Context context) {
        this(context, article -> {});
    }

    void refresh() {
        mLoader.setVisibility(VISIBLE);
        mRecycler.setVisibility(INVISIBLE);
        if (mService != null) {
            mService.get(articles -> {
                mRecycler.post(() -> {
                    mLoader.setVisibility(INVISIBLE);
                    mRecycler.setVisibility(VISIBLE);
                    ArticleAdapter adapter = (ArticleAdapter) mRecycler.getAdapter();
                    if (adapter != null) {
                        adapter.submitList(articles);
                    }
                });
            });
        }
    }
}

package com.dylantjohnson.articlelist;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is responsible for adapting a list of Articles into a format suitable for a
 * RecyclerView.
 */
class ArticleAdapter extends ListAdapter<Article, ArticleHolder> {
    private Consumer<Article> mOnClickListener;

    /**
     * Construct a new adapter.
     *
     * @param onClickListener the code to run when an article is selected
     */
    ArticleAdapter(Consumer<Article> onClickListener) {
        super(new DiffUtil.ItemCallback<Article>() {
            @Override
            public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.getTitle().equals(newItem.getTitle())
                        && oldItem.getImageUrl().equals(newItem.getImageUrl())
                        && oldItem.getDescription().equals(newItem.getDescription())
                        && oldItem.getLink().equals(newItem.getLink());
            }
        });
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ArticleHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        holder.bind(getItem(position), position == 0, mOnClickListener);
    }
}


/**
 * This class holds the View that will be used for displaying an article.
 */
class ArticleHolder extends RecyclerView.ViewHolder {
    private ArticlePeakView mView;

    private ArticleHolder(ArticlePeakView view) {
        super(view);
        mView = view;
    }

    /**
     * Create a new ViewHolder.
     *
     * @param parent the node that this View will be a child of
     * @return a fresh ArticleHolder
     */
    static ArticleHolder from(ViewGroup parent) {
        return new ArticleHolder(new ArticlePeakView(parent.getContext()));
    }

    /**
     * Load a view with a specific Article.
     *
     * @param article the Article to display
     * @param first whether this Article is the first on display or not
     * @param onClick the code to run when an Article is selected
     */
    void bind(Article article, boolean first, Consumer<Article> onClick) {
        mView.load(article, first, onClick);
    }
}

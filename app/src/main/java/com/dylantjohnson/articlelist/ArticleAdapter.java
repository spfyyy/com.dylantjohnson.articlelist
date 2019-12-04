package com.dylantjohnson.articlelist;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

class ArticleAdapter extends ListAdapter<Article, ArticleHolder> {
    private Consumer<Article> mOnClickListener;

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
                        && oldItem.getContent().equals(newItem.getContent());
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

class ArticleHolder extends RecyclerView.ViewHolder {
    private ArticlePeakView mView;

    private ArticleHolder(ArticlePeakView view) {
        super(view);
        mView = view;
    }

    static ArticleHolder from(ViewGroup parent) {
        return new ArticleHolder(new ArticlePeakView(parent.getContext()));
    }

    void bind(Article article, boolean first, Consumer<Article> onClick) {
        mView.load(article, first, onClick);
    }
}

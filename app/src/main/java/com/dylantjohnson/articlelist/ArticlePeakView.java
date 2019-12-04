package com.dylantjohnson.articlelist;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;

/**
 * This View is used for displaying Article previews.
 */
public class ArticlePeakView extends LinearLayout {
    private LoadableImageView mPicture;
    private TextView mTitle;
    private TextView mSummary;

    /**
     * Create a new ArticlePeakView.
     * <p>
     * This View is aware that the first Article in the list is displayed differently and will hide
     * or update its children accordingly.
     *
     * @param context the context for this View
     */
    ArticlePeakView(Context context) {
        super(context);
        setOrientation(VERTICAL);

        LayoutParams layout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.grid_margin);
        layout.setMargins(margin, margin, margin, margin);
        setLayoutParams(layout);

        mPicture = new LoadableImageView(context);
        addView(mPicture);

        mTitle = new TextView(context);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setTextSize(context.getResources().getDimension(R.dimen.title_text));
        addView(mTitle);

        mSummary = new TextView(context);
        mSummary.setLines(2);
        mSummary.setEllipsize(TextUtils.TruncateAt.END);
        mSummary.setTextSize(context.getResources().getDimension(R.dimen.summary_text));
        addView(mSummary);
    }

    /**
     * Change the contents of this View.
     *
     * @param article the Article this View will preview
     * @param first whether this Article is the first on the list or not
     * @param onClick the code to run when an article is selected
     */
    void load(Article article, boolean first, Consumer<Article> onClick) {
        mSummary.setVisibility(first ? VISIBLE : GONE);
        mPicture.loadImage(article.getImageUrl());
        mTitle.setText(article.getTitle());
        mSummary.setText(article.getDescription());
        mTitle.setLines(first ? 1 : 2);
        setOnClickListener(view -> onClick.accept(article));
    }
}

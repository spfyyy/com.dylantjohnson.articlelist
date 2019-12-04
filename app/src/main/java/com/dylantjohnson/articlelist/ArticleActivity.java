package com.dylantjohnson.articlelist;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity is used for displaying an embedded browser to view an article's contents.
 */
public class ArticleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle params = getIntent().getExtras();

        FrameLayout root = new FrameLayout(this);
        WebView view = new WebView(this);

        root.addView(view);

        if (params != null) {
            Article article = (Article) params.getSerializable(getString(R.string.article_param));
            if (article != null) {
                view.loadUrl(article.getLink().toString());
                ActionBar navBar = getSupportActionBar();
                if (navBar != null) {
                    navBar.setTitle(article.getTitle());
                    navBar.setDisplayHomeAsUpEnabled(true);
                    navBar.setDisplayShowHomeEnabled(true);
                }
            }
        }

        setContentView(root);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

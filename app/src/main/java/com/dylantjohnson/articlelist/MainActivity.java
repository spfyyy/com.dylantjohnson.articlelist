package com.dylantjohnson.articlelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The starting activity. This activity shows the list of articles and is in charge of navigating to
 * the individual article pages.
 */
public class MainActivity extends AppCompatActivity {
    private ArticleListView mArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticleList = new ArticleListView(this, this::navigateToArticle);
        setContentView(mArticleList);
        mArticleList.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem refresh = menu.add("refresh");
        refresh.setIcon(R.drawable.ic_refresh);
        refresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mArticleList.refresh();
        return true;
    }

    /**
     * Switch to a specific article page view.
     *
     * @param article the article to show
     */
    private void navigateToArticle(Article article) {
        Intent intent = new Intent(this, ArticleActivity.class);
        Bundle params = new Bundle();
        params.putSerializable(getString(R.string.article_param), article);
        intent.putExtras(params);
        startActivity(intent);
    }
}

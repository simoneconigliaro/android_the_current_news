package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.analytics.Analytics;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleDbUtils;
import com.project.simoneconigliaro.thecurrentnews.widget.NewsWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailArticleActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.share_fab)
    FloatingActionButton floatingActionButton;

    private Article mArticle;
    private String mIdArticle;
    private final static String ARTICLE_SAVED = "Article saved";
    private final static String ARTICLE_REMOVED = "Article removed";
    private final static String ARTICLE_KEY = "article";
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null){
            mArticle = intent.getParcelableExtra(ARTICLE_KEY);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient());
            if(mArticle.getUrl() != null) {
                mWebView.loadUrl(mArticle.getUrl());
            }
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArticle.getUrl() != null) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, mArticle.getUrl());
                    startActivity(Intent.createChooser(i, getString(R.string.action_share)));
                    Analytics.logEventShare(getApplicationContext(), mArticle);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mOptionsMenu = menu;
        mIdArticle = ArticleDbUtils.checkIfArticleIsAlreadyStored(this, mArticle);
        changeIconFavoriteArticle(mIdArticle);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_favorite){
            setActionFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeIconFavoriteArticle(String idArticle){
        if(idArticle != null){
            mOptionsMenu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_black_24dp);
        } else {
            mOptionsMenu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_border_black_24dp);
        }
    }

    public void setActionFavorite(){
        mIdArticle = ArticleDbUtils.checkIfArticleIsAlreadyStored(this, mArticle);
        /* if mIdArticle isn't null it means the article is present in the db */
        if(mIdArticle != null){
            ArticleDbUtils.deleteFavoriteArticle(this, mIdArticle);
            Toast.makeText(this, ARTICLE_REMOVED, Toast.LENGTH_SHORT).show();
            mOptionsMenu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_border_black_24dp);
        } else {
            ArticleDbUtils.addFavoriteArticle(this, mArticle);
            Toast.makeText(this, ARTICLE_SAVED, Toast.LENGTH_SHORT).show();
            mOptionsMenu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_black_24dp);
        }
    }
}

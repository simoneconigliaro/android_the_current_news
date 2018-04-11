package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailArticleActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    private Article mArticle;
    private String mIdArticle;
    private boolean mIsArticleSaved = false;
    private final static String ARTICLE_SAVED = "Article saved";
    private final static String ARTICLE_REMOVED = "Article removed";
    private final static String ARTICLE_KEY = "article";
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mWebView.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();

        if (intent != null){
            mArticle = intent.getParcelableExtra(ARTICLE_KEY);
            mWebView.setWebViewClient(new WebViewClient());
            if(mArticle.getUrl() != null) {
                mWebView.loadUrl(mArticle.getUrl());
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mOptionsMenu = menu;
        checkIfArticleIsAlreadyStored();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_favorite){
            if(mIsArticleSaved){
                deleteFavoriteArticle();
                changeIconFavoriteArticle(mIsArticleSaved);
            } else {
                addFavoriteArticle();
                changeIconFavoriteArticle(mIsArticleSaved);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeIconFavoriteArticle(boolean isArticleSaved){
        if(isArticleSaved){
            mOptionsMenu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_black_24dp);
        } else {
            mOptionsMenu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_border_black_24dp);
        }
    }

    private void addFavoriteArticle(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_TITLE, mArticle.getTitle());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_NAME, mArticle.getName());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_DATE, mArticle.getPublishedAt());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_URL, mArticle.getUrl());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_URL_IMAGE, mArticle.getUrlToImage());
        Uri uri = getContentResolver().insert(ArticleContract.ArticleEntry.CONTENT_URI, contentValues);
        if(uri != null){
            mIsArticleSaved = true;
            Toast.makeText(this, ARTICLE_SAVED, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfArticleIsAlreadyStored(){
        Cursor cursor = getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String url = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry.COLUMN_URL));
                if (url != null) {
                    if (url.equals(mArticle.getUrl())) {
                        mIsArticleSaved = true;
                        changeIconFavoriteArticle(mIsArticleSaved);
                        mIdArticle = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry._ID));
                    }
                }
            }
            cursor.close();
        }
    }

    private void deleteFavoriteArticle(){
        if(mIdArticle != null){
            Uri uri = ArticleContract.ArticleEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(mIdArticle).build();
            getContentResolver().delete(uri, null, null);
            mIsArticleSaved = false;
            Toast.makeText(this, ARTICLE_REMOVED, Toast.LENGTH_SHORT).show();
        }
    }
}

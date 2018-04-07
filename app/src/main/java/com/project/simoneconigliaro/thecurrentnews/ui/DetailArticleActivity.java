package com.project.simoneconigliaro.thecurrentnews.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.simoneconigliaro.thecurrentnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailArticleActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    private final static String URL_KEY = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        ButterKnife.bind(this);


        mWebView.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();

        if (intent != null){
            String urlArticle = intent.getStringExtra(URL_KEY);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.loadUrl(urlArticle);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

package com.project.simoneconigliaro.thecurrentnews.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.ui.ArticleAdapter;
import com.project.simoneconigliaro.thecurrentnews.utils.InternetUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FetchArticlesTask extends AsyncTask<String, Void, List<Article>> {

    ArticleAdapter mArticleAdapter;
    String mCategoryArticle;
    String mCountry;
    RecyclerView mRecyclerView;
    Parcelable mLayoutState;
    TextView noCountryTextView;
    Context context;

    private static final String GLOBAL = "Global";
    private static final String LOCAL = "Local";


    public FetchArticlesTask(ArticleAdapter articleAdapter, RecyclerView recyclerView, Parcelable layoutState, String categoryArticle) {
        this.mArticleAdapter = articleAdapter;
        this.mCategoryArticle = categoryArticle;
        this.mRecyclerView = recyclerView;
        this.mLayoutState = layoutState;
    }

    public FetchArticlesTask(ArticleAdapter articleAdapter,RecyclerView recyclerView, Parcelable layoutState, String categoryArticle, String country, TextView noCountryTextView, Context context) {
        this.mArticleAdapter = articleAdapter;
        this.mCategoryArticle = categoryArticle;
        this.mCountry = country;
        this.mRecyclerView = recyclerView;
        this.mLayoutState = layoutState;
        this.noCountryTextView = noCountryTextView;
        this.context = context;
    }

    @Override
    protected List<Article> doInBackground(String... strings) {

        URL newsURL = null;

        switch (mCategoryArticle){
            case GLOBAL:
                newsURL = NetworkUtils.buildGlobalNewsURL();
                break;
            case LOCAL:
                newsURL = NetworkUtils.buildLocalNewsURL(mCountry);
                break;
        }

        try {
            String jsonArticlesResponse = NetworkUtils.getResponseFromHttpUrl(newsURL);
            List<Article> articles = OpenArticleJsonUtils.getArticlesFromJson(jsonArticlesResponse);
            return articles;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



    @Override
    protected void onPostExecute(List<Article> articles) {
        super.onPostExecute(articles);
        mArticleAdapter.setArticlesData(articles);
        if (mLayoutState != null){
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mLayoutState);
        }

        if(mCategoryArticle == LOCAL && InternetUtils.checkConnection(context)) {
            if (mArticleAdapter.getItemCount() == 0) {
                noCountryTextView.setVisibility(View.VISIBLE);
            } else {
                noCountryTextView.setVisibility(View.GONE);
            }
        }
    }
}

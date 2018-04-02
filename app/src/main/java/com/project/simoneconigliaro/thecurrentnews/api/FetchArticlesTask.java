package com.project.simoneconigliaro.thecurrentnews.api;

import android.os.AsyncTask;
import android.util.Log;

import com.project.simoneconigliaro.thecurrentnews.data.Article;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FetchArticlesTask extends AsyncTask<String, Void, List<Article>> {

    static final String LOG_TAG = FetchArticlesTask.class.getSimpleName();

    @Override
    protected List<Article> doInBackground(String... strings) {

        URL globalNewsURL = NetworkUtils.buildGlobalNewsURL();
        try {
            String jsonArticlesResponse = NetworkUtils.getResponseFromHttpUrl(globalNewsURL);
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

        for(int i = 0; i<articles.size(); i++){
            Log.d(LOG_TAG, i + "  " + articles.get(i).getId());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getName());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getAuthor());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getTitle());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getDescription());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getUrl());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getUrlToImage());
            Log.d(LOG_TAG, i + "  " + articles.get(i).getPublishedAt());
        }

    }
}

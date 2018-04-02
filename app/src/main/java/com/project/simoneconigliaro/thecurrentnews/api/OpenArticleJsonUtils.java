package com.project.simoneconigliaro.thecurrentnews.api;

import android.text.TextUtils;
import android.util.Log;

import com.project.simoneconigliaro.thecurrentnews.data.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility function to handle news JSON data.
 */
public class OpenArticleJsonUtils {
    private static String LOG_TAG = OpenArticleJsonUtils.class.getSimpleName();
    public static List<Article> getArticlesFromJson(String articlesJsonString){

        if (TextUtils.isEmpty(articlesJsonString)) {
            return null;
        }

        List<Article> articles = new ArrayList<>();

        try {
            JSONObject articlesJson = new JSONObject(articlesJsonString);

            JSONArray results = articlesJson.getJSONArray("articles");

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentArticle = results.getJSONObject(i);

                JSONObject source = currentArticle.getJSONObject("source");
                String id = source.optString("id");
                String name = source.optString("name");

                String author = currentArticle.getString("author");
                String title = currentArticle.getString("title");
                String description = currentArticle.getString("description");
                String url = currentArticle.getString("url");
                String urlToImage = currentArticle.getString("urlToImage");
                String publishedAt = currentArticle.getString("publishedAt");

                articles.add(new Article(id, name, author, title, description, url, urlToImage, publishedAt));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the movie JSON results", e);
        }
        return articles;
    }
}

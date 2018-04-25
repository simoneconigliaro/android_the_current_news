package com.project.simoneconigliaro.thecurrentnews.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.project.simoneconigliaro.thecurrentnews.widget.NewsWidgetProvider;

public class ArticleDbUtils {

    public static String checkIfArticleIsAlreadyStored(Context context, Article article){
        Cursor cursor = context.getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String url = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry.COLUMN_URL));
                if (url != null) {
                    if (url.equals(article.getUrl())) {
                        String idArticle = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry._ID));
                        return idArticle;
                    }
                }
            }
            cursor.close();
        }
        return null;
    }

    public static void addFavoriteArticle(Context context, Article currentArticle){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_TITLE, currentArticle.getTitle());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_NAME, currentArticle.getName());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_DATE, currentArticle.getPublishedAt());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_URL, currentArticle.getUrl());
        contentValues.put(ArticleContract.ArticleEntry.COLUMN_URL_IMAGE, currentArticle.getUrlToImage());
        context.getContentResolver().insert(ArticleContract.ArticleEntry.CONTENT_URI, contentValues);
        NewsWidgetProvider.sendRefreshBroadcast(context);
    }

    public static void deleteFavoriteArticle(Context context, String idArticle){
        Uri uri = ArticleContract.ArticleEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idArticle).build();
        context.getContentResolver().delete(uri, null, null);
        NewsWidgetProvider.sendRefreshBroadcast(context);
    }


}

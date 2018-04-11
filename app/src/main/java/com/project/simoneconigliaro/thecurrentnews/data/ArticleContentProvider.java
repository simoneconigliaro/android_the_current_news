package com.project.simoneconigliaro.thecurrentnews.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.project.simoneconigliaro.thecurrentnews.data.ArticleContract.ArticleEntry.TABLE_NAME;

public class ArticleContentProvider extends ContentProvider {

    public static final int ARTICLES = 100;
    public static final int ARTICLES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ArticleContract.AUTHORITY, ArticleContract.PATH_ARTICLES, ARTICLES);
        uriMatcher.addURI(ArticleContract.AUTHORITY, ArticleContract.PATH_ARTICLES + "/#", ARTICLES_WITH_ID);
        return uriMatcher;
    }

    private ArticleDbHelper mArticleDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mArticleDbHelper = new ArticleDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projecton, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mArticleDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match){
            case ARTICLES:
                retCursor = db.query(TABLE_NAME,
                        projecton,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mArticleDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ARTICLES:

                long id = db.insert(TABLE_NAME, null, contentValues);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(ArticleContract.ArticleEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final SQLiteDatabase db = mArticleDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int articlesDeleted;

        switch (match) {

            case ARTICLES_WITH_ID:
            String id = uri.getPathSegments().get(1);
            articlesDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
            break;

        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(articlesDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return articlesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

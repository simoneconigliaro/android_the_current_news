package com.project.simoneconigliaro.thecurrentnews.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.project.simoneconigliaro.thecurrentnews.ui.FavoritesAdapter;

import static com.project.simoneconigliaro.thecurrentnews.ui.FavoriteNewsFragment.ID_FAVORITES_LOADER;

public class FavoriteCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private FavoritesAdapter favoritesAdapter;
    private TextView noFavoritesTextView;

    public FavoriteCursorLoader(Context context, FavoritesAdapter favoritesAdapter, TextView noFavoriteTextView) {
        this.context = context;
        this.favoritesAdapter = favoritesAdapter;
        this.noFavoritesTextView = noFavoriteTextView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case ID_FAVORITES_LOADER:
                String[] projection = {
                        ArticleContract.ArticleEntry._ID,
                        ArticleContract.ArticleEntry.COLUMN_TITLE,
                        ArticleContract.ArticleEntry.COLUMN_NAME,
                        ArticleContract.ArticleEntry.COLUMN_DATE,
                        ArticleContract.ArticleEntry.COLUMN_URL,
                        ArticleContract.ArticleEntry.COLUMN_URL_IMAGE

                };
                return new CursorLoader(context,
                        ArticleContract.ArticleEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader failed: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        favoritesAdapter.swapCursor(cursor);

        if(cursor.getCount() == 0){
            noFavoritesTextView.setVisibility(View.VISIBLE);
        } else {
            noFavoritesTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoritesAdapter.swapCursor(null);

    }
}

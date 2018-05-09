package com.project.simoneconigliaro.thecurrentnews.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.Article;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    private final static String ARTICLE_KEY = "article";

    public WidgetFactory(Context context, Intent intent) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(mCursor != null){
            mCursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        mCursor = mContext.getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (i == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(i)) {
            return null;
        }

        final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.tv_title_widget, mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_TITLE)));

        String name = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_NAME));
        String title = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_TITLE));
        String url = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_URL));
        String urlImage = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_URL_IMAGE));
        String date = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_DATE));
        Article article = new Article(name, title, url, urlImage, date);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(ARTICLE_KEY, article);
        remoteViews.setOnClickFillInIntent(R.id.widget_item_container, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return mCursor.moveToPosition(i) ? mCursor.getLong(mCursor.getColumnIndex(ArticleContract.ArticleEntry._ID)) : i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}

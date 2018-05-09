package com.project.simoneconigliaro.thecurrentnews.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.RemoteViews;

import com.project.simoneconigliaro.thecurrentnews.R;
import com.project.simoneconigliaro.thecurrentnews.data.ArticleContract;
import com.project.simoneconigliaro.thecurrentnews.ui.DetailArticleActivity;
import com.project.simoneconigliaro.thecurrentnews.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class NewsWidgetProvider extends AppWidgetProvider {

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, NewsWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    public static boolean checkFavorites(Context context){
        Cursor cursor = context.getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if(cursor.getCount() == 0){
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.news_widget_provider);

            if (checkFavorites(context)){
                remoteViews.setViewVisibility(R.id.tv_add_favorites, View.GONE);
            } else {
                remoteViews.setViewVisibility(R.id.tv_add_favorites, View.VISIBLE);
            }

            Intent configIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.tv_add_favorites, pendingIntent);

            Intent intent = new Intent(context, WidgetService.class);
            remoteViews.setRemoteAdapter(R.id.widget_list_view, intent);

            // template to handle the click listener for each item
            Intent clickIntentTemplate = new Intent(context, DetailArticleActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_list_view, clickPendingIntentTemplate);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, NewsWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


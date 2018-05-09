package com.project.simoneconigliaro.thecurrentnews.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.project.simoneconigliaro.thecurrentnews.data.Article;

public class Analytics {

    public static void logEventShare(Context context, Article article){

        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, article.getName());
        FirebaseAnalytics.getInstance(context).logEvent(
                FirebaseAnalytics.Event.SHARE, params);

    }
}

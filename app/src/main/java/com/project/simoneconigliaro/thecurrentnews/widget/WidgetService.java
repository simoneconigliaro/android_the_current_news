package com.project.simoneconigliaro.thecurrentnews.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(this.getApplicationContext(), intent);
    }
}

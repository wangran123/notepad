package com.notepad.home.notepad;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.Date;

/**
 * Created by wr on 2014/10/22.
 */
public class HelloAppWidgetProvider extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence text;
        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
        text = "http://blog.csdn.net/imyang2007" + "    Time:" + df.format(new Date());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        views.setTextViewText(R.id.appwidget_text, text);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

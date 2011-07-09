package org.knuth.tinystackdroid;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WidgetProvider extends AppWidgetProvider{
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds){
		// First check if network is connected:
		if (!isNetworkConnected(context)){
			return;
		}
		// Network is connected, perform updates:
		for (int widget_id : appWidgetIds){
			Intent i = new Intent(context, UpdateService.class);
			i.putExtra(Config.EXTRA_WIDGET_ID, widget_id);
			context.startService(i);
		}
	}
	
	public static void setup(Context context, int widgetID){
		// First check if Network is connected:
		if (!isNetworkConnected(context)){
			return;
		}
		Intent i = new Intent(context, UpdateService.class);
		i.putExtra(Config.EXTRA_WIDGET_ID, widgetID);
		context.startService(i);
	}
	
	private static boolean isNetworkConnected(Context context){
		ConnectivityManager manager = 
			(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info == null || !info.isConnected()){
			Log.e("OnlyLog", "No connection, exiting...");
			return false;
		}
		return true;
	}
	
}

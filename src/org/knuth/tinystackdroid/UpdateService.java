package org.knuth.tinystackdroid;

import org.knuth.tinystackdroid.api.ApiQuery;
import org.knuth.tinystackdroid.api.SOFlair;
import org.knuth.tinystackdroid.api.exception.ApiException;
import org.knuth.tinystackdroid.api.exception.ParseException;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class UpdateService extends Service{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Create updated Views
		Bundle b = intent.getExtras();
		int widget_id = b.getInt(Config.EXTRA_WIDGET_ID);
		if (widget_id == AppWidgetManager.INVALID_APPWIDGET_ID){
			Log.e("OnlyLog", "Falsche ID in onStartCommand erhalten");
		}
		try {
			// Get the user-id
			String user_id = Config.loadUserID(this, widget_id);
			RemoteViews updatedViews = this.buildUpdate(this, user_id);
			// Push to the Widget on the Home-Screen
			AppWidgetManager widget_manager = AppWidgetManager.getInstance(this);
			widget_manager.updateAppWidget(widget_id, updatedViews);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return START_NOT_STICKY;
	}
	
	public RemoteViews buildUpdate(Context context, String user_id)
			throws ApiException, ParseException{
		// Get the values form the API
		ApiQuery api = new ApiQuery(user_id);
		SOFlair flair = api.getFlair();
		// Fill out the Widget-Views
		RemoteViews views = new RemoteViews(
				context.getPackageName(), R.layout.widget);
		views.setTextViewText(R.id.name, flair.name);
		views.setTextViewText(R.id.reputation, flair.reputation+"");
		views.setBitmap(R.id.avatar, "setImageBitmap", flair.getAvatar());
		// Set the Badges:
		if (flair.badge_bronze > 0){
			views.setTextViewText(R.id.badge_bronze, flair.badge_bronze+"");
			views.setViewVisibility(R.id.badge_bronze_img, View.VISIBLE);
		}
		if (flair.badge_silver > 0){
			views.setTextViewText(R.id.badge_silver, flair.badge_silver+"");
			views.setViewVisibility(R.id.badge_silver_img, View.VISIBLE);
		}
		if (flair.badge_gold > 0){
			views.setTextViewText(R.id.badge_gold, flair.badge_gold+"");
			views.setViewVisibility(R.id.badge_gold_img, View.VISIBLE);
		}
		// Return the updated Views:
		return views;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
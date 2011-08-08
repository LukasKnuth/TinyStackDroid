package org.knuth.tinystackdroid;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * The Provider which updates the Widget.
 * @author Lukas Knuth
 *
 */
public class WidgetProvider extends AppWidgetProvider{
	
	/** Used to wait for a Internet-Connection */
	private ConnectionWatcher con_watch;
	/** Filter for the {@link ConnectionWatcher} */
	private final IntentFilter filter;
	
	/**
	 * Initializes the global variables.
	 */
	public WidgetProvider(){
		super();
		con_watch = new ConnectionWatcher();
		filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
	}
	
	/**
	 * Updates the Widget's using the {@link WidgetProvider}
	 * @see WidgetProvider 
	 */
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds){
		Log.d("OnlyLog", "Update!");
		// First check if network is connected:
		if (!isNetworkConnected(context)){
			// Couldn't update so register a BroadcastReceiver to do it,
			// when there is a connection again.
			context.getApplicationContext().registerReceiver(con_watch, filter);
			return;
		}
		// Network is connected, perform updates:
		for (int widget_id : appWidgetIds){
			Intent i = new Intent(context, UpdateService.class);
			i.putExtra(Config.EXTRA_WIDGET_ID, widget_id);
			context.startService(i);
		}
	}
	
	/**
	 * Called from {@link Config}-Activity to set up the
	 *  new Widget-Instance. 
	 * @param context The Application-context
	 * @param widgetID The Widget-ID
	 * @see Config
	 */
	public static void setup(Context context, int widgetID){
		// First check if Network is connected:
		if (!isNetworkConnected(context)){
			return;
		}
		Intent i = new Intent(context, UpdateService.class);
		i.putExtra(Config.EXTRA_WIDGET_ID, widgetID);
		context.startService(i);
	}
	
	/**
	 * BroadcastReceiver which gets notified, when the state
	 * of the Internet-connection changes. 
	 * @author Lukas Knuth
	 *
	 */
	public class ConnectionWatcher extends BroadcastReceiver {

		/**
		 * Checks if an Internet-connection is available
		 *  and requests updates.
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			boolean isConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			if (!isConnected){
				// Update Widget.
				Log.d("OnlyLog", "Update Widget");
				AppWidgetManager manager = AppWidgetManager.getInstance(context);
				ComponentName name = new ComponentName(context, WidgetProvider.class);
				onUpdate(context, manager, manager.getAppWidgetIds(name));
			}
		}

	}
	
	/**
	 * Checks if an Internet-connection is currently available.
	 * @param context The Application-Context
	 * @return "true" if there is a connection, otherwise "false".
	 */
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

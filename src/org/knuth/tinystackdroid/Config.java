package org.knuth.tinystackdroid;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.knuth.tinystackdroid.R;

/**
 * The Configuration-Activity for the Widget.
 * @author Lukas Knuth
 *
 */
public class Config extends Activity{
	
	/** The provided Widget ID */
	private int widget_id;
	/** The name for the preference */
	private static final String PREF_NAME = "org.knuth.tinystackdroid.WidgetProvider";
	/** The prefix for the preference-object to hold the user-id */
	private static final String PREF_KEY_PREFIX = "user_id_";
	/** The EditText which holds the user-id */
	private EditText user_id;
	/** A default User-ID */
	private final static String DEFAULT_USER_ID = "22656";
	/** The Name for the Intent-Extra, containing the Widget-ID */
	public final static String EXTRA_WIDGET_ID = "widget_id";
	
	/**
	 * Create the GUI and check if the Widget-ID is valid.
	 */
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		// Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        this.setResult(RESULT_CANCELED);
        this.setContentView(R.layout.config);
		// Get the Widget-ID from the Intent
		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null){
			widget_id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		// If there is no widget-id:
		if (widget_id == AppWidgetManager.INVALID_APPWIDGET_ID){
			finish();
		}
		// Find the EditText
		user_id = (EditText)this.findViewById(R.id.user_id);
		// Bind the onClick to the submit-button:
		this.findViewById(R.id.submit).setOnClickListener(submit);
	}
	
	/**
	 * onClickListener for the "submit"-button.
	 */
	View.OnClickListener submit = new View.OnClickListener() {
		
		@SuppressWarnings("static-access")
		@Override
		public void onClick(View v) {
			// Get the Context:
			final Context context = Config.this;
			// Save the user-ID
			String id = user_id.getText().toString();
			saveUserID(context, id);
			// Push the Update:
			WidgetProvider.setup(context, widget_id);
			// Return
			AppWidgetManager widget_manager = AppWidgetManager.getInstance(context);
			Intent result = new Intent();
			result.putExtra(widget_manager.EXTRA_APPWIDGET_ID, widget_id);
			setResult(RESULT_OK, result);
			finish();
		}
	};
	
	/**
	 * Saves the User-ID to the new Widgets-ID using SharedPreferences.
	 * @param context The Application-Context
	 * @param user_id The User-ID
	 */
	private void saveUserID(Context context, String user_id){
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREF_NAME, 0).edit();
		prefs.putString(PREF_KEY_PREFIX	+ widget_id, user_id);
		prefs.commit();
	}
	
	/**
	 * Loads the saved User-ID by using the given Widget-ID.
	 * @param context The Application-Context
	 * @param widget_id The ID of the Widget which User-ID should
	 *  be loaded.
	 * @return The User-ID of the given Widget.
	 */
	public static String loadUserID(Context context, int widget_id){
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, 0);
		String prefix = prefs.getString(PREF_KEY_PREFIX + widget_id, null);
		if (prefix != null){
			return prefix;
		}
		return DEFAULT_USER_ID;	// Which is Jon Skeet
	}

}

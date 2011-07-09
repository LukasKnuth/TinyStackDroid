package org.knuth.tinystackdroid.api;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class SOFlair{
	
	private final String gravatar_url = "http://www.gravatar.com/avatar/";
	private final String gravatar_404 = "identicon";
	private final int gravatar_size = 30;
	
	public String name;
	public int reputation;
	private int badge_gold;
	private int badge_silver;
	private int badge_bronze;
	private String email_hash;
	
	public SOFlair(String name, int reputation,
			int badge_gold, int badge_silver, int badge_bronze,
			String email_hash){
		this.badge_bronze = badge_bronze;
		this.badge_gold = badge_gold;
		this.badge_silver = badge_silver;
		this.email_hash = email_hash;
		this.name = name;
		this.reputation = reputation;
	}
	
	public Bitmap getAvatar(){
		String url = this.gravatar_url +	// The Gravatar-request Base-URL
			this.email_hash +		// The E-Mail hash from the API
			"?s"+this.gravatar_size +	// The Size of the Image
			"?d"+this.gravatar_404;	// The returned standard-image
		// Create the Bitmap from the URL:
		Bitmap bm = null;
		try {
			URL src_url = new URL(url);
			URLConnection con = src_url.openConnection();
			InputStream in = con.getInputStream();
			bm = BitmapFactory.decodeStream(in);
		} catch (Exception e){
			Log.e("OnlyLog", e.getMessage());
		}
		return bm;
	}
	
	public String getBadgesString(){
		return "Gold: "+this.badge_gold +
			" | Silver: "+this.badge_silver +
			" | Bronze: "+this.badge_bronze;
	}
}
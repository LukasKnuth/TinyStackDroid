package org.knuth.tinystackdroid.api;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * This class holds informations about a single
 *  StackOverflow user (populated by {@link ApiQuery}).
 * @author Lukas Knuth
 *
 */
public class SOFlair{
	
	/** The Base-URL for the Gravatar-Site */
	private final String gravatar_url = "http://www.gravatar.com/avatar/";
	/** The 404-String to get an alternative if there
	 *  was no picture found.
	 */
	private final String gravatar_404 = "identicon";
	/** The size of the picture in Pixel */
	private final int gravatar_size = 80;
	
	/** The users Name */
	public String name;
	/** The users reputation-count */
	public int reputation;
	/** The users Gold-Badge count */
	public int badge_gold;
	/** The users Silver-Badge count */
	public int badge_silver;
	/** The users Bronze-Badge count */
	public int badge_bronze;
	/** The E-Mail hash (for the Gravatar request) */
	private String email_hash;
	
	/**
	 * Initializes the global fields.
	 * @param name The Users Name
	 * @param reputation The users reputation-count
	 * @param badge_gold The users Gold-Badge count
	 * @param badge_silver The users Silver-Badge count
	 * @param badge_bronze The users Bronze-Badge count
	 * @param email_hash The E-Mail hash (for the Gravatar request)
	 */
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
	
	/**
	 * Queries the Gravatar site to get the
	 *  users picture.
	 * @return The users picture (or the specified
	 *  404 picture).
	 */
	public Bitmap getAvatar(){
		String url = this.gravatar_url +	// The Gravatar-request Base-URL
			this.email_hash +		// The E-Mail hash from the API
			"?s="+this.gravatar_size +	// The Size of the Image
			"&d="+this.gravatar_404;	// The returned standard-image
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
}
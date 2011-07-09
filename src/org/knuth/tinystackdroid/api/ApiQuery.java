package org.knuth.tinystackdroid.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.knuth.tinystackdroid.api.exception.ApiException;
import org.knuth.tinystackdroid.api.exception.ParseException;

public class ApiQuery {
	
	private final String REQUEST_URL = "http://api.stackoverflow.com/1.1/users/";
	private String user_id;
	
	public ApiQuery(String user_id){
		this.user_id = user_id;
	}

	public SOFlair getFlair() throws ApiException, ParseException{
		// Get the JSON-String:
		String json;
		try {
			json = getJSONString();
		} catch (Exception e) {
			throw new ApiException("Couldn't query the API.", e);
		}
		try {
			return parseJSON(json);
		} catch (Exception e) {
			throw new ParseException("Error while Parsing the API-response", e);
		}
	}
	
	private SOFlair parseJSON(String json) throws Exception{
		// Get the "user"-Object:
		JSONObject response = new JSONObject(json);
		JSONArray users = response.getJSONArray("users");
		JSONObject user = users.getJSONObject(0);
		// Get the needed values:
		String name = user.getString("display_name");
		int reputation = user.getInt("reputation");
		String email_hash = user.getString("email_hash");
		// Get the badge-Object:
		JSONObject badge = user.getJSONObject("badge_counts");
		int badge_gold = badge.getInt("gold");
		int badge_silver = badge.getInt("silver");
		int badge_bronze = badge.getInt("bronze");
		// Create a "SOflair"-Object
		return new SOFlair(name, reputation,
				badge_gold, badge_silver, badge_bronze, email_hash);
	}
	
	private String getJSONString() throws Exception{
		// Create a connection to the request_url
		URL url = new URL(REQUEST_URL + user_id);
		URLConnection con = url.openConnection();
		con.setRequestProperty("Accept-Encoding", "gzip,deflate");
		// Read from the connection:
		InputStream gzip_s = null;
		InputStreamReader in = null;
		StringBuilder build = new StringBuilder();
		try{
			gzip_s = new GZIPInputStream(con.getInputStream());
			in = new InputStreamReader(gzip_s, "UTF-8");
			// Read the JSON-String
			int chars_read = 0;
			char[] buffer = new char[512];
			while ( (chars_read = in.read(buffer, 0, buffer.length)) != -1 ){
				build.append(buffer, 0, chars_read);
			}
		} finally {
			in.close();
			gzip_s.close();
		}
		return build.toString();
	}
	
}

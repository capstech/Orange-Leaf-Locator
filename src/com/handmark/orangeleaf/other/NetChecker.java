/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf.other;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.handmark.orangeleaf.R;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

public class NetChecker {
        public static boolean isOnline(Application app) {
        	try {
                //ConnectivityManager is used to check available network(s)
                ConnectivityManager cm = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);

                /* check for airplane mooood */
                if (cm.getActiveNetworkInfo() == null){
                        //no network is available
                        return false;
                } else {
                        //at least one type of network is available
                        return true;
                }

    		} catch (Exception e) {
    			// this is the line of code that sends a real error message to the
    			//Log.e("ERROR", "ERROR IN CODE: " + e.toString());

    			/* Print the error */
    			//e.printStackTrace();
    			return false;
    		}
        }

	public static String checkNet(Application app, String pathURL) {
		/* Do we have 3G? Wifi? Anything? */
		if (isOnline(app) == false) {
			return (app.getString(R.string.conn_missing));
		}
		/* Are we reachable */
		try {
			URL url = new URL(pathURL);
			System.setProperty("http.keepAlive", "false");
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	        urlc.setRequestProperty("User-Agent", "Android Application: Orange Leaf Froyo");
	        urlc.setRequestProperty("Connection", "close");
			//String version = System.getProperty("os.name");
			urlc.setConnectTimeout(1000 * 30); /* seconds */
			
			urlc.connect();
			if (urlc.getResponseCode() == 200) {
				// http response is OK
				return (app.getString(R.string.netok));
			}
		} catch (MalformedURLException e1) {
			return (app.getString(R.string.badurl));
		} catch (IOException e) {
			return (app.getString(R.string.nonet));
		}
		return (app.getString(R.string.netok));
	}
}


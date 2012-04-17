/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf.other;


import android.content.Context;
import android.widget.Toast;

public class CommonFunctions {
	/* Short message box */
	public static void MessageBoxS(String message, Context act){
	    Toast.makeText(act.getApplicationContext(),(String) message,Toast.LENGTH_SHORT).show();
	}
	
	/* Long message box */
	public static void MessageBoxL(String message, Context act){
	    Toast.makeText(act.getApplicationContext(),(String) message,Toast.LENGTH_LONG).show();
	}
	
}

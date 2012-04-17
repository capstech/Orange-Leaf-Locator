/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends Activity {

	//------------------------------------------------------------
	// Vars
	//------------------------------------------------------------
	private final int SPLASH_LENGTH = 1500;

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splashscreen);
		
		/* New Handler to start the Menu-Activity 
		 * and close after 1000 ms. */
		new Handler().postDelayed(new Runnable(){
			public void run() {
				/* Create an Intent that will start the Menu-Activity. */
				Intent mainIntent = new Intent(splash.this,orangeleaf.class);
				splash.this.startActivity(mainIntent);
				splash.this.finish();
			}
		}, SPLASH_LENGTH);
	}
}
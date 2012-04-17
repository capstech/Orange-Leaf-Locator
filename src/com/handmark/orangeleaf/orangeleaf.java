/*

 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

/* Main class */
public class orangeleaf extends TabActivity {
	
	
	/* onCreate function */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/* vars */
		Resources res = getResources();
		final TabHost tabHost = getTabHost();
		/* api v9 fix */
		//tabHost.setId(android.R.id.tabhost);
		TabHost.TabSpec spec;
		Intent intent;
		
		// --------------------------------------
		// Tabs
		// --------------------------------------

		/* Store Tab */
		intent = new Intent().setClass(this, storeActivity.class);
		spec = tabHost.newTabSpec("store").setIndicator("Store",
				res.getDrawable(R.drawable.ic_tab_store)).setContent(intent);
		tabHost.addTab(spec);

		/* Flavor */
		/*
		intent = new Intent().setClass(this, flavorActivity.class);
		spec = tabHost.newTabSpec("flavor").setIndicator("Vote",
				res.getDrawable(R.drawable.ic_tab_vote)).setContent(intent);
		tabHost.addTab(spec);
		*/

		/* Ice Cream Maker tab */
		intent = new Intent().setClass(this, createActivity.class);
		spec = tabHost.newTabSpec("icecream").setIndicator("Create",
				res.getDrawable(R.drawable.ic_tab_create)).setContent(intent);
		tabHost.addTab(spec);

		/* Twitter tab */
		intent = new Intent().setClass(this, twitterActivity.class);
		spec = tabHost.newTabSpec("twitter").setIndicator("Tweet",
				res.getDrawable(R.drawable.ic_tab_tweet)).setContent(intent);
		tabHost.addTab(spec);

		//tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.grey_tab_72);
		
		/* set default tab */
		tabHost.setCurrentTab(0);
		
		
	 tabHost.setOnTabChangedListener(new OnTabChangeListener() {
		 public void onTabChanged(String arg0) {         
	            //tabHost.getCurrentTab();
	        }       
	    });  
}
}
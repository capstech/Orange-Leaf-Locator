/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.orangeleaf.saxHandlers.tweetData;
import com.handmark.orangeleaf.saxHandlers.tweetsDataHandler;
//import com.handmark.orangeleaf.imageHandling.ImageLoader;
import com.handmark.orangeleaf.imageHandling.LazyAdapter;
import com.handmark.orangeleaf.imageHandling.Utils;
import com.handmark.orangeleaf.other.NetChecker;
import com.handmark.orangeleaf.other.MyCounter;
import com.handmark.orangeleaf.imageHandling.ImageLoader;

/* Virtual Ice Cream tab */
public class twitterActivity extends Activity implements Runnable {
	
	//--------------------------------
	// Vars
	//--------------------------------
	private final String URLpath 	  = "http://search.twitter.com/search.atom?q=%23orangeleaf+OR+orangeleaf";
	//private final String twitterAPIKey = "ZnGEEryKyIjPnbHQ3tTglQ"; 
    public String[] tweetArray;
    public String[] tweetPics;
	/* temp arrays */
	public ArrayList<String> tweetArrayTEMP = new ArrayList<String>();
	public ArrayList<String> tweetPicsTEMP = new ArrayList<String>();
    public ProgressDialog pd;
    ListView list;
    LazyAdapter adapter;
	MyCounter counter = new MyCounter(5000,1000);
	Thread thread;
	
    /* onCreate stuff */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Debug step 1 */
		//Log.i("OrangeLeaf", "onCreate twitterActivity");
		this.onThreadStart();
	}
	
	/* Start the application */
	public void onStart(Bundle savedInstanceState) {
		super.onStart();
		//Log.i("OrangeLeaf", "onStart twitterActivity");
	}
	
	/* Options Menu */
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.tweet_menu, menu);
	    return true;
	}
    
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) 
        {
        	/* redo */
       		case R.id.redo:
       			this.redo();
       			break;
       		
        }
       
       return super.onOptionsItemSelected(item);
    }
    
	/* Start it on a thread, to not clog main UI */
	public void onThreadStart()
	{
		
		/* Check progressBox */
		this.pd = ProgressDialog.show(this,(getString(R.string.pleasewait)), "We are loading OrangeLeaf Tweets.",
				true);

		/* test */
		String test = NetChecker.checkNet(this.getApplication(), URLpath);

		/* Check if false */
		if (!(test.toString().equals("NETOK")))
		{
			/* End some stuff, we failed */
			if (this.pd.isShowing())
			{
				this.pd.cancel();
			}
			
			/* Pop the error */
			this.errorOut(this.getString(R.string.default_tweet_error));
			
		}
		else
		{
			/* Start the thread */
			thread = new Thread(this);
			thread.start();
			
			/* Run our timer */
			counter.start();
		}
	}
	
	/* We recache remove  */
	public OnClickListener listener=new OnClickListener(){
		public void onClick(View arg0) {
	        //adapter.imageLoader.clearCache();
	        //adapter.notifyDataSetChanged();
			counter.cancel();
	    	twitterActivity.this.onThreadStart();
	    }
	};
	
	public void run() {
		try
		{
			/* Make our tweet Activity,then run */
			this.firstTime();
		} 
		catch (Exception e) 
		{ 
		}
		handler.sendEmptyMessage(0);
	}	
	
	public Handler handler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{

			/* kill our progress bar */
			counter.cancel();
			
			/* Cancel our progress menu */
			if(pd.isShowing())
			{
				pd.dismiss();
			}
			
			/* content view nao */
			setContentView(R.layout.twitter);

			/* Inflate our array from our listview */
			list = (ListView)findViewById(R.id.list);

			
			/* draw me */
			twitterActivity.this.drawMe();
		}
	};

	/* Foreign method for drawing, to get outside of handler */
	public void drawMe()
	{
		/* Clear out the previous ones */
		tweetArrayTEMP.clear();
		tweetPicsTEMP.clear();
		
		/* Clean out the nulls */
		for (int x = 0; x < this.tweetArray.length; x++)
		{
			if (this.tweetArray[x] == null)
			{
				
			}
			else
			{
				tweetArrayTEMP.add(this.tweetArray[x]);
				tweetPicsTEMP.add(this.tweetPics[x]);
			}
		}
		//------------------------
		//todo: fix resources
		//------------------------
		this.tweetArray = new String[tweetArrayTEMP.size()];
		this.tweetPics  = new String[tweetArrayTEMP.size()];
		
		for (int x = 0; x < tweetArrayTEMP.size(); x++)
		{
			this.tweetArray[x] = tweetArrayTEMP.get(x);
			this.tweetPics[x]  = tweetPicsTEMP.get(x);
		}

	    /* Set the adapter */
	    adapter = new LazyAdapter(this, tweetArray, tweetPics);
	    list.setAdapter(adapter);
	}
	
	/* Force stop after timer elapses */
	public void endMe()
	{
		/* Cancel our progress menu */
		//if(this.pd.isShowing())
		//{		
			//this.pd.dismiss();
		//}
		
		/* Check if thread exists */
		//if (thread != null)
		//{
		//	
		//}
		
		/* pop the error */
		//this.errorOut(this.getString(R.string.default_tweet_error));
	}
	
	public void firstTime()
	{
		// --------------------------------------------
		// Start the work
		// --------------------------------------------
		try {
			
			/* Create a URL we want to load some xml-data from. */
			URL url = new URL(URLpath);

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create a new ContentHandler and apply it to the XML-Reader */
			tweetsDataHandler myTweetsData = new tweetsDataHandler();
			xr.setContentHandler(myTweetsData);
			
				/* Parse the xml-data from our URL. */
				String response = Utils.doGet(url.toString());
				
				if (response.toString().equalsIgnoreCase("-1"))
				{
					this.errorOut(this.getString(R.string.tweet_error_internet));
				}
		    	xr.parse(new InputSource(new StringReader(response)));
	
				/* Why we use this extra class, I don't know */
				tweetData tweetDataSet = myTweetsData.getParsedData();
	
				/* Converts our stuff and preps it */
				if (tweetDataSet.runFirst() == false)
				{
					this.errorOut(this.getString(R.string.tweet_error_internet));
				}
	
				/* Pull our tweets */
				this.tweetArray = (String[]) tweetDataSet.getExtractedStrings();
				this.tweetPics = (String[]) tweetDataSet.getExtractedPics();
				
				
			
				/* Check if failed */
				if ((this.tweetArray == null) || (this.tweetPics == null))
				{
					this.errorOut(this.getString(R.string.default_tweet_error));
				}
				else
				{   
				}	
		    /* 
		     * Should we add a recache setting?
		     * 
		     * Link both pic redo, and redo the pull in this 
		     * yet to be made function
		     *
		     * Button b=(Button)findViewById(R.id.button1);
		     * b.setOnClickListener(listener);
		     *
		     */

		} catch (Exception e) {
			// this is the line of code that sends a real error message to the
			//Log.e("ERROR", "ERROR IN CODE: " + e.toString());

			/* Print the error */
			//e.printStackTrace();
		}
	}
	
	/* Error Out */
	public void errorOut(String message){
	
	/* Error */
	this.uglyError("Error", message.toString(), "OK");
	
	/* set content view to our error */
	this.setContentView(R.layout.twitter_error);
	
	/* Load Textview to change error */
	TextView t = new TextView(this); 
    t = (TextView) findViewById(R.id.twitError);
    
	/* add button lisener.*/
    Button b=(Button)findViewById(R.id.retry_button);
    b.setOnClickListener(listener);
    
    /* setText */
    t.setText(R.string.default_tweet_error);
}

	/* uglyError */
	public void uglyError(String title, String message, String button){
		new AlertDialog.Builder(this).setTitle(title).setMessage(message).setNeutralButton(button, null).show();
	}

	/* Phone call / paused */
	public void onPause(Bundle savedInstanceState)
	{
		super.onPause();
		//Log.i("OrangeLeaf", "onPause twitterActivity");
	}
	
	/* Resume */
	public void onResume(Bundle savedInstanceState)
	{
		super.onResume();
		//Log.i("OrangeLeaf", "onResume twitterActivity");
	}
	
	/* destroy */
	public void onDestroy(Bundle saveInstanceState)
	{
		//Log.i("OrangeLeaf", "onDestroy twitterActivity");
	    adapter.imageLoader.stopThread();
	    list.setAdapter(null);
	    
	    /* Dump our vars */
	    super.onDestroy();
	}
	
	/*
	 * Regrab tweets & trash cache
	 */
	public void redo()
	{
		/* Delete images first */
		ImageLoader L = new ImageLoader(this.getApplicationContext());
		L.clearCache();
		L = null;
		
		/* redo */
		this.onThreadStart();
	}
	
}
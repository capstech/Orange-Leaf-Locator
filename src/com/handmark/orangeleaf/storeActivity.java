/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf;


import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.orangeleaf.imageHandling.Utils;
import com.handmark.orangeleaf.imageHandling.pikminAdapter;
import com.handmark.orangeleaf.other.DataBaseHelper;
import com.handmark.orangeleaf.other.MyCounterStore;
import com.handmark.orangeleaf.other.NetChecker;
import com.handmark.orangeleaf.saxHandlers.locationData;
import com.handmark.orangeleaf.saxHandlers.locationDataHandler;

/* Store Info, Nutritional Info tab */
public class storeActivity extends Activity implements Runnable {
	/* vars */
	//private final String MY_DEBUG_TAG = "OrangeLeaf";
	private final String URLpath 	  = "http://bvcapstechprojects.com/orangeleaf/index.php";
	public static String location     = null;
	public static String address      = null;
	public Boolean flag = false;
	public locationData locationDataSet;
    public ProgressDialog pd1;
    public ListView list;
    public pikminAdapter adapter1;
	//localhost loopout = 10.0.2.2
    static String[][] locations;
	MyCounterStore counter1 = new MyCounterStore(5000,1000);
	Thread thread1;
	
	/* onCreate  yeee*/
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		/* Check if sqlLite is stored */
		this.checkmySQL();
		
		if (this.flag)
		{
			this.onThreadStart();
		}
	
	}
	/* Start it on a thread, to not clog main UI */
	public void onThreadStart()
	{
		/* Check progressBox */
		this.pd1 = ProgressDialog.show(this,(this.getString(R.string.pleasewait)), this.getString(R.string.load_locations),
				true);

		/* test */
		String test = NetChecker.checkNet(this.getApplication(), URLpath);

		/* Check if false */
		if (!(test.toString().equals("NETOK")))
		{
			/* dismiss our popup */
			if (this.pd1.isShowing())
			{
				this.pd1.cancel();
			}
			
			/* Pop the error */
			this.errorOut();
			
		}
		else
		{
			
			/* Start the thread */
			thread1 = new Thread(this);
			thread1.start();
			
		}
	}
	
	/* We recache remove  */
	public OnClickListener listener1=new OnClickListener(){
		public void onClick(View arg0) {
	    	storeActivity.this.onThreadStart();
	    }
	};
	
	public void run() {
		try
		{
			/* Make our tweet Activity,then run */
			this.storeFirstTime();
		} 
		catch (Exception e) 
		{ 
		}
		/* cancel outside handler, to get permission */
		if (this.pd1.isShowing())
		{
			this.pd1.cancel();
		}
		
		handler.sendEmptyMessage(0);
		
	}	
	
	public Handler handler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			/* kill our progress bar */
			//counter1.cancel();

			/* content view nao */
			setContentView(R.layout.store);

			/* Inflate our array from our listview */
			list = (ListView)findViewById(R.id.list1);

			/* draw me */
			storeActivity.this.drawMe();
		}
	};

	/* Foreign method for drawing, to get outside of handler */
	public void drawMe()
	{		
	    if (this.flag == false)
	    {
			/* content view nao */
			setContentView(R.layout.store);

			/* Inflate our array from our listview */
			list = (ListView)findViewById(R.id.list1);
	    }
	    
	    /* Set the adapter */
	    this.adapter1 = new pikminAdapter(this, storeActivity.locations);
	    this.list.setAdapter(adapter1);
	    
	    /* I honestly don't know who put this here, or what it does */
	    this.list.setTextFilterEnabled(true);
	    
	    /* This makes our onClick listener, which prompts phone number & route */
	    this.list.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	    	
	    		/* set to static var, so we can access */
	    		storeActivity.location = storeActivity.locations[position][5].toString();
	    		
	    		/* Make our GEO location */
	    		storeActivity.address  = storeActivity.locations[position][0] + " "
	    		              + storeActivity.locations[position][1] + ", " 
	    		              + storeActivity.locations[position][4];
	    		
	    		//--------------------------------
	    		// Make our alertDialog
	    		//--------------------------------
			    AlertDialog.Builder adb=new AlertDialog.Builder(storeActivity.this);
			    
			    adb.setMessage("Store Information")
			    .setCancelable(false)       
			       /* ROUTE */
			       .setNeutralButton("Route", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   
			        	   startActivity(new Intent(
			        			   android.content.Intent.ACTION_VIEW,
			        			   Uri.parse("geo:0,0?q=" + storeActivity.address.toString())));
			           }
			       })
			       
				    .setPositiveButton("Call", new DialogInterface.OnClickListener() {
					    /* We are calling the store boys */
					    public void onClick(DialogInterface dialog, int id) {
					    	
					    	/* Check for null phone number */
					    	if (storeActivity.location.length() == 0)
					    	{					    	    
					    		dialog.cancel();
					    	}
					    	else
					    	{
					           startActivity(new Intent(
					                	Intent.ACTION_CALL, 
					                	Uri.parse("tel:" + storeActivity.location.replace("-", "").toString() + "\"")));
					    	}
					       }
					   })
			    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			       	/* Sorry were not calling */
			           public void onClick(DialogInterface dialog, int id) {
			                		dialog.cancel();
			           }
			       });
	
			    adb.setTitle("Store Information");
			    
			    /*
			     * STATE = 2
			     * ADDRESS = 0
			     * CITY = 1
			     * ZIP = 4
			     * TELE = 5
			     * HOURS = 3
			     */
			    adb.setMessage(storeActivity.locations[position][2] + "\n" 
			    		+ storeActivity.locations[position][0] + "\n"
			    		+ storeActivity.locations[position][1] + ", " 
			    		+ storeActivity.locations[position][4] + " \n"
			    		+ storeActivity.locations[position][5]);
			    adb.show();
			    }
		    });
	   

	    
	}
	
	/* Force stop after timer elapses */
	public void endMe()
	{
		/* Cancel our progress menu */
		if(this.pd1.isShowing())
		{
			this.pd1.cancel();
		}
		
		/* Check if thread exists */
		//if (thread1 != null)
		//{
		//}
		
		/* pop the error */
		this.errorOut();
	}
	

	public void checkmySQL()
	{
		//-------------------------------------
		// Go check for mysqli lite
		//-------------------------------------
		DataBaseHelper mydB = new DataBaseHelper(this.getApplicationContext());
		
		/* Lets try and create this database */
		try 
		{
			mydB.createDataBase();
					
		} 
		catch (IOException ioe)
		{
			throw new Error("Unable to create database");
		}
		
		/* New try & catch for the implentation of database */
		try 
		{ 
			/* now open */
			mydB.openWriteableDatabase();
			Boolean check = mydB.locationCheck();	
			
			/* If our table exists, get the data */
			if (check == true)
			{
				/* set locations */
				storeActivity.locations = mydB.getOurData();
				
				/* Flag if were null */
				if (storeActivity.locations == null)
				{
					this.flag = true;
				}
				else
				{
					this.flag = false;
					this.drawMe();
				}
			}
			else
			{
				this.flag = true;
			}
		}
		catch(SQLException sqle)
	 	{
	 		throw sqle;
	 	}
	}
	
	public void storeFirstTime(){
		
		try {
			/* Create a URL we want to load some xml-data from. */
			URL url = new URL(URLpath);

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
			
			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
			locationDataHandler myLocations = new locationDataHandler();
			xr.setContentHandler(myLocations);
			
			/* Parse the xml-data from our URL. */		
		    String response = Utils.doGet(url.toString());
		    
		    //if (response.toString().equalsIgnoreCase("-1"))
		    //{
		    	//this.errorOut();
		    //}
		    xr.parse(new InputSource(new StringReader(response)));
		
			/* Get our data from our locationDataSet */
			this.locationDataSet = myLocations.getParsedData();
			
			/* Lets turn this into a massive array */
			this.locationDataSet.generateArrays(this.getApplicationContext());
			
			/* send locations back */
			storeActivity.locations = this.locationDataSet.locations;
				
		
		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//tv.setText("Error: " + e.getMessage());
			//Log.e(MY_DEBUG_TAG, "LeafError", e);
		}
	}

	
	private void errorOut() {
		
		//----------------------
		// CHANGE ERRORS AND STRINGS TO REFLECT STORE LINGO NOT TWEET
		//----------------------
		
		/* Error */
		this.uglyError("Error", getString(R.string.default_store_error), "OK");
		
		/* set content view to our error */
		this.setContentView(R.layout.store_error);
		
		/* Load Textview to change error */
		TextView t = new TextView(this); 
	    t = (TextView) findViewById(R.id.storeError);
	    
		/* add button lisener.*/
	    Button b=(Button)findViewById(R.id.store_retry_button);
	    b.setOnClickListener(listener1);
	    
	    /* setText */
	    t.setText(R.string.default_store_error);
	}
	
	/* uglyError */
	public void uglyError(String title, String message, String button){
		new AlertDialog.Builder(this).setTitle(title).setMessage(message).setNeutralButton(button, null).show();
	}
	
	/* destroy */
	public void onDestroy(Bundle saveInstanceState)
	{
	    /* Dump our vars */
	    super.onDestroy();
	    
		//Log.i("OrangeLeaf", "onDestroy storeActivity");
	    list.setAdapter(null);
	    
	}
	
	/* Options Menu */
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.store_menu, menu);
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
	
	public void redo()
	{		
		/* redo */
		this.onThreadStart();
	}
	
}
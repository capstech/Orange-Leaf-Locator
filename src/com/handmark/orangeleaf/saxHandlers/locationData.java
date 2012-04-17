/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf.saxHandlers;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

import com.handmark.orangeleaf.R;
import com.handmark.orangeleaf.other.DataBaseHelper;
import com.handmark.orangeleaf.other.CommonFunctions;

public class locationData{
	private int counter = 0;
	private int error = 0;
	
	/* We pass out arrays, but use arraylists. */
	//public String[] address,city,state,hours,zip,tele;
	public String[][] locations;
	
	/* arraylists */
	public ArrayList<String> addressList;
	public ArrayList<String> cityList;
	public ArrayList<String> stateList;
	public ArrayList<String> hoursList;
	public ArrayList<String> zipList;
	public ArrayList<String> teleList;                                                                                                                                                                             
	
	public void storeData(String pulled, int type)
	{
		/* only initiate once */
		if (this.counter == 0)
		{
			this.addressList = new ArrayList<String>();
			this.cityList    = new ArrayList<String>();
			this.stateList   = new ArrayList<String>();
			this.hoursList   = new ArrayList<String>();
			this.zipList     = new ArrayList<String>();
			this.teleList    = new ArrayList<String>();
		}
			
			/* remove negatives */
			if (pulled.toString().equalsIgnoreCase("-1"))
			{
				pulled = "";
			}
			
			/* Switch for type */
			switch (type)
			{
				case 0: //address
					addressList.add(pulled);
				break;
				
				case 1: //city
					cityList.add(pulled);
				break;
				
				case 2: //state
					stateList.add(pulled);
				break;
				
				case 3: //hours
					hoursList.add(pulled);
				break;
				
				case 4: //zip
					zipList.add(pulled);
				break;
				
				case 5: //tele
					teleList.add(pulled);
				break;
				
			}
			//Log.i("bug 1", pulled.toString());
			
			/* add one */
			this.counter++;
			
			/* clear */
			pulled = "";
		}
	
	public void generateArrays(Context c){
		
		try {
		
		/* locations */
		if (addressList.size() == 0)
		{
			this.addressList = new ArrayList<String>();
			this.cityList    = new ArrayList<String>();
			this.stateList   = new ArrayList<String>();
			this.hoursList   = new ArrayList<String>();
			this.zipList     = new ArrayList<String>();
			this.teleList    = new ArrayList<String>();
			//Log.e("ERROR", "WE are at ZERO");
		}
		
		this.locations = new String[this.addressList.size()][6];
		
		
		/* Loop through arraylist */
		for (int i = 0; i < this.addressList.size(); i++)
		{
			this.locations[i][0] = this.addressList.get(i);
			this.locations[i][1] = this.cityList.get(i);
			this.locations[i][2] = this.stateList.get(i);
			this.locations[i][3] = this.hoursList.get(i);
			this.locations[i][4] = this.zipList.get(i);
			this.locations[i][5] = this.teleList.get(i);
		}
		
		
			/* Lets clean us up :o */
			this.cleanUp();
			
			/* Now we store */
			this.storeToSQL(c);

		} catch (Exception e) {
			/* Display any Error to the GUI. */
			//Log.e("locationData", "LeafError", e);
		}
	}
	
	private void storeToSQL(Context c)
	{
		//--------------------------------------
		// Storage in sqlite
		//--------------------------------------
		DataBaseHelper mydB = new DataBaseHelper(c.getApplicationContext());
		
		/* Lets try and create this database */
		try 
		{
			mydB.createDataBase();
			
		} 
		catch (IOException ioe)
		{
			throw new Error("Unable to create database");
		}
		
		//-------------------------------------
		// Now we open the dB
		//-------------------------------------
		try 
		{ 
			/* now open */
			mydB.openWriteableDatabase();
			
			/* now we work */
			if (mydB.checkOrMakeTables() == false)
			{
				/* we've failed twice :o */
				if (this.error != 0)
				{
					//error out
				}
				else
				{
					/* re run */
					CommonFunctions.MessageBoxL(c.getString(R.string.error_db_failed), c);
					this.error++;
					
					/* redo */
					mydB.checkOrMakeTables();
				}
			}
			
			//----------------------------------
			// Populate with our data 
			//----------------------------------
			

			/* now open */
			mydB.openWriteableDatabase();
			
			/* We need a content thiny */
			ContentValues values = new ContentValues();
			
			/* empty first */
			mydB.clearThis();
			
			/* Loop for each location to add into database */
			for (int x = 0; x < this.locations.length; x++)
			{
					values.clear();
					/* store our values */
					values.put("address", (String) this.locations[x][0]);
					values.put("city", (String) this.locations[x][1]);
					values.put("state", (String) this.locations[x][2]);
					values.put("hours", (String) this.locations[x][3]);
					values.put("zip", (String) this.locations[x][4]);
					values.put("tele", (String) this.locations[x][5]);
					
					
					/* Now insert */
					mydB.insertQuery(values);
			}
	 
	 	}
		
		//----------------------------------
		// nooooooooooooooooooooooooo
		//----------------------------------
		catch(SQLException sqle)
	 	{
	 		throw sqle;
	 	}

	}
	
	private void cleanUp()
	{
		/*cleanup */
		this.addressList.clear();
		this.cityList.clear();
		this.stateList.clear();
		this.hoursList.clear();
		this.zipList.clear();
		this.teleList.clear();
		this.counter = 0;
	}
}
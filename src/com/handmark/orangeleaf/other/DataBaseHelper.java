package com.handmark.orangeleaf.other;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

//-------------------------------------------------------------
// DataBaseHelper
//
// As for security, we are not hosting any secure things
// This is for just locations. Lets ignore cleaning any vars
// If someone wants to be crafty and mess with our dB on 
// their own phone. Let em, then clear it :o
//
// Remind me how much I hate cursors.
// mysql > sqlite :o
//-------------------------------------------------------------

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    private final Context myContext;
    
    /*app default path */
    private static String DB_PATH = "";
    private static String DB_NAME = "orange_fro";
    private SQLiteDatabase myDataBase; 
    
    /* recreate into arraylist */
	public ArrayList<String> addressList;
	public ArrayList<String> cityList;
	public ArrayList<String> stateList;
	public ArrayList<String> hoursList;
	public ArrayList<String> zipList;
	public ArrayList<String> teleList;  
	public String[][] locations;
     
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 2);
        this.myContext = context;
        
        /* Now we set the path w/ our package name */
        DB_PATH = "/data/data/" + this.myContext.getApplicationContext().getPackageName() + "/databases/";
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase(); //When we create, we want to write probably
    	
    	/* Check if database exists */
    	if(dbExist)
    	{
    		/* do nothing */
    	}
    	else
    	{
    		
    		/* make the readable */
    		this.getReadableDatabase();
    			
    		try 
    		{
    			this.copyDataBase();
    		} catch (IOException e) 
    		{
    			throw new Error("Error copying database");

    		}
    	}

    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		
    	checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    	
    	}catch(SQLiteException e){
    		/* doesn't exist */
 
    	}
    	/* If database exists, then close it */
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	/* True for existing, false if it doesn't */
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	/* Open local dB */
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	/* path to new dB */
    	String outFileName = DB_PATH + DB_NAME;
 
    	/* Output stream */
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	/* transfer bytes */
    	byte[] buffer = new byte[1024];
    	int length;
    	
    	/* looopie looopie */
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	/* close flush close close */
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    /*
     * Read only Open
     */
    public void openDataBase() throws SQLException{
 
    	/* Open our database */
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
    
    /*
     * Read & Write Open
     * 
     */
    public void openWriteableDatabase() throws SQLException{
    	
    	/* Make our path */
    	String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    /*
     * Make Locations table w/ fields
     * 
     */
    public boolean checkOrMakeTables() throws SQLException
    {
    	/* Check if we exists */
    	Cursor q = myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table';",null);
    	
    	/* grab our name context */
    	int nameColumn = q.getColumnIndex(("name"));
    	
		String tableName = null;
    	{
    		
    		/* are we on the first result? */
    		if (q.isFirst())
    		{
    			/* loopy, since we might have more than 1 table */
    			do {
    				    				
	    			/* grab the column */
	    			tableName = q.getString(nameColumn);
	    			
	    			/* Lets break out if we hit locations */
	    			if (tableName.toString().equals("locations"))
	    			{
	    				break;
	    			}
    			
    			} 
    			while (q.moveToNext());
    		}
    		
    		//------------------------------------------
    		// STUPID. YOU SHOULD BE FIRST
    		//------------------------------------------
    		else
    		{
    			/* move to first */
    			q.moveToFirst();
    			
    			/* loopy, since we might have more than 1 table */
    			do {
    				
	    			/* grab the column */
	    			tableName = q.getString(nameColumn);
	    			
	    			/* Lets break out if we hit locations */
	    			if (tableName.toString().equals("locations"))
	    			{
	    				break;
	    			}
    			
    			} 
    			while (q.moveToNext());
    		}
    	}
    	
    	/* close our cursor */
    	q.close();
    	
    	if (tableName.toString().equals("locations"))
    	{
        	myDataBase.close();
    		return true;
    	}
    	else
    	{
	    	/* creation of locations database  */
	    	myDataBase.rawQuery("BEGIN TRANSACTION;" +
	    			" CREATE TABLE 'locations' (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    			"address TEXT NOT NULL, city TEXT NOT NULL, state TEXT NOT NULL, hours TEXT NOT NULL," +
	    			" zip TEXT NOT NULL, tele TEXT NOT NULL); COMMIT;", null);
	    			
	    	
	    	/* add the android_metadata */
	    	myDataBase.rawQuery("BEGIN TRANSACTION;" + 
	    			"CREATE TABLE 'android_metadata' ('locale' TEXT DEFAULT 'en_US')" +
	    			"INSERT INTO 'android_metadata' VALUES ('en_US')" +
	    			"",null);
	    	
	    	myDataBase.close();
	    	return true;
    	}
    	
    	
    }
    
    public void clearThis()
    {
		myDataBase.delete("locations", null, null);
    }
    
    /* Check if they have location AND DATA */
    public boolean locationCheck()
    {
    	/* Check if we exists */
    	Cursor q = myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table';",null);
    	
    	/* grab our name context */
    	int nameColumn = q.getColumnIndex(("name"));
    	
		String tableName = null;
    	{
    		
    		/* are we on the first result? */
    		if (q.isFirst())
    		{
    			/* loopy, since we might have more than 1 table */
    			do {
    				    				
	    			/* grab the column */
	    			tableName = q.getString(nameColumn);
	    			
	    			/* Lets break out if we hit locations */
	    			if (tableName.toString().equals("locations"))
	    			{
	    				break;
	    			}
    			
    			} 
    			while (q.moveToNext());
    		}
    		
    		//------------------------------------------
    		// STUPID. YOU SHOULD BE FIRST
    		//------------------------------------------
    		else
    		{
    			/* move to first */
    			q.moveToFirst();
    			
    			/* loopy, since we might have more than 1 table */
    			do {
    				
	    			/* grab the column */
	    			tableName = q.getString(nameColumn);
	    			
	    			/* Lets break out if we hit locations */
	    			if (tableName.toString().equals("locations"))
	    			{
	    				break;
	    			}
    			
    			} 
    			while (q.moveToNext());
    		}
    	}
    	
    	/* close our cursor */
    	q.close();
    	
    	
    	/* NOW WE KNOW IF WE NEED TO HIT MY SERVER OR NOT */
    	if (tableName.toString().equals("locations"))
    	{
        	myDataBase.close();
    		return true;
    	}
    	else
    	{
    		myDataBase.close();
    		return false;
    	}
    }

    /* Pull from our DB, checking if exists, then return */
    public String[][] getOurData() throws SQLException
    {
    	this.openWriteableDatabase();
    	
    	/* Get our cursor */
    	Cursor q = myDataBase.rawQuery("SELECT * FROM locations", null);
    	
    	/* grab our columns */
    	int addressColumn = q.getColumnIndex(("address"));
    	int cityColumn    = q.getColumnIndex(("city"));
    	int stateColumn   = q.getColumnIndex(("state"));
    	int hoursColumn   = q.getColumnIndex(("hours"));
    	int zipColumn     = q.getColumnIndex(("zip"));
    	int teleColumn    = q.getColumnIndex(("tele"));
    	
    	/* ArrayLists */
    	this.addressList = new ArrayList<String>();
    	this.cityList = new ArrayList<String>();
    	this.stateList = new ArrayList<String>();
    	this.hoursList = new ArrayList<String>();
    	this.zipList = new ArrayList<String>();
    	this.teleList = new ArrayList<String>();
		
		
		
    	{
			
			/* move to first */
			q.moveToFirst();
			
    		/* are we on the first result? */
    		if (q.isFirst())
    		{
    			int i = 0;
    			/* Loop our results */
    			do {
    				i++;
    				
    				this.addressList.add(q.getString(addressColumn));
    				this.cityList.add(q.getString(cityColumn));
    				this.stateList.add(q.getString(stateColumn));
    				this.hoursList.add(q.getString(hoursColumn));
    				this.zipList.add(q.getString(zipColumn));
    				this.teleList.add(q.getString(teleColumn));
    				
    			}while(q.moveToNext());
    		}
    		
    		//------------------------------------------
    		// STUPID. YOU SHOULD BE FIRST
    		//------------------------------------------
    		else
    		{

    		}
    	
    	
    	/* close our cursor */
    	q.close();
    	

		}
		
		//-----------------------------------
		// We finished all of this junk
		//-----------------------------------
		if ((addressList.size() != cityList.size()) || (teleList.size() != zipList.size()) || (addressList.size() == 0))
		{
			return null;
		}
		else
		{
			// GO GO GO GO GO 
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
			
			return locations;
		}
  
}
    public Cursor directQuerywithReturn(String query) throws SQLException
    {
    	/* Make a dangerous direct Query */
    	return myDataBase.rawQuery(query, null);
    }
    
    /* 
     * directQuery
     * Parameters
	 * table  The table name to compile the query against. 
     * columns  A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used. 
     * selection  A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table. 
     * selectionArgs  You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings. 
     * groupBy  A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped. 
     * having  A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used. 
     * orderBy  How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered. 
     */
    public void directQuery(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy ) throws SQLException
    {
    	myDataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
    
    public void insertQuery(ContentValues values)
    {
    	myDataBase.insert("locations", null, values);
    }
    
    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    @Override
	public synchronized void close() {
 
    		/* close it */
    	    if(myDataBase != null)
    	    {
    	    	myDataBase.endTransaction();
    		    myDataBase.close();
    	    }
    	    super.close();
 
	}
 
    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
	
	public void onDestroy()
	{
	    if(myDataBase != null)
	    {
	    	myDataBase.endTransaction();
		    myDataBase.close();
	    }
	}
	
	public void onClose(SQLiteDatabase db)
	{
	    super.close();
		/* close it */
	    if(myDataBase != null)
	    {
	    	myDataBase.endTransaction();
		    myDataBase.close();
	    }
	}
 
	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}
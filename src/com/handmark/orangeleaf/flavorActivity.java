/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf;

import android.app.Activity;
import android.os.Bundle;

/* Flavor voting tab */
public class flavorActivity extends Activity {
	
	/* onCreate Method */
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//Log.e("onCreate flavorActivity", "hit");
	
	try 
	{   
		/* Our BG and Linear */
        setContentView(R.layout.vote_main);
        
		/* Lets get the width/height */
		//Display display = getWindowManager().getDefaultDisplay(); 
		
		//int width = display.getWidth();
		//int height = display.getHeight();
        
        // load the origial BitMap  and new
        //Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),R.drawable.vote_bg);
        //LinearLayout voteBG = (LinearLayout) findViewById(R.id.bg_vote);
        
        //voteBG.setBackgroundResource(R.drawable.vote_bg);
        
        /* Get height of image, and height of screen to scale */
        //int width = bitmapOrg.getWidth();
        //int height = bitmapOrg.getHeight();
        //int newWidth = (display.getWidth());
        //int newHeight = (display.getHeight());
        
        // calculate the scale - in this case = 0.4f
        //float scaleWidth = ((float) newWidth) / width;
        //float scaleHeight = ((float) newHeight) / height;
        
        // createa matrix for the manipulation
       // Matrix matrix = new Matrix();
        
        // resize the bit map
        //matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
      //  Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true); 
    
        // make a Drawable from Bitmap to allow to set the BitMap 
        // to the ImageView, ImageButton or what ever
       // BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        
        
        // set the Drawable on the ImageView
        //voteBG.setBackgroundDrawable(bmd);
		
		/* Get unique address */
		//TelephonyManager tManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		//String uid = tManager.getDeviceId();
		
		/* Set our content view */
		//setContentView(R.layout.flavor);
		
	    /* Name buttons */
	    //Button flavor1But = (Button) findViewById(R.id.flavor1);
	    //Button flavor2But = (Button) findViewById(R.id.flavor2);
	    //Button lastBut    = (Button) findViewById(R.id.Button03);
	    
	    /* Write text */
	    //flavor1But.setText("test");
	    //flavor2But.setText(uid);
	    //lastBut.setText("test");
		
	} 
	catch (Exception e)
	{
		// this is the line of code that sends a real error message to the log
		//Log.e("ERROR", "ERROR IN CODE: " + e.toString());
 
		/* Print the error */
		//e.printStackTrace();
	}
	}

	/* Our voting class */
	public static String vote()
	{
		
		return null;
	}
}
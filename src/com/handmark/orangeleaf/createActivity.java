package com.handmark.orangeleaf;


import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/* Virtual Ice Cream tab */
public class createActivity extends Activity {

	/* vars */
	private ImageView layer_1, layer_2, layer_3, layer_4, layer_5;
	private ImageView top_1, top_2, top_3;
	private int flavor_id = 1;
	private int topping_id = 0;
	public int hehe = 0;
	public String name = "";
	public Toast toast;

	/* onCreate function */
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
		
		this.firstTime();
	}
	
	/* Options Menu */
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.create_menu, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) 
        {
        	/* redo */
       		case R.id.reset:
       			this.reset();
       			break;
       		
        }
       
       return super.onOptionsItemSelected(item);
    }
	
	/* Get all the create images in the images */
	public void run() {
		try
		{
			/* Make our tweet Activity,then run */
			this.loadImages();
		} 
		catch (Exception e) 
		{ 
		}
	}	
	
	//---------------------------------
	// RUN FIRST 
	//---------------------------------
	public void firstTime()
	{		
		try
		{
			this.setContentView(R.layout.create);
			this.layer_1 = (ImageView) findViewById(R.id.layer_1_flavor);
			this.layer_2 = (ImageView) findViewById(R.id.layer_2_flavor);
			this.layer_3 = (ImageView) findViewById(R.id.layer_3_flavor);
			this.layer_4 = (ImageView) findViewById(R.id.layer_4_flavor);
			this.layer_5 = (ImageView) findViewById(R.id.layer_5_flavor);
			this.top_1   = (ImageView) findViewById(R.id.topping_1);
			this.top_2   = (ImageView) findViewById(R.id.topping_2);
			this.top_3   = (ImageView) findViewById(R.id.topping_3);
			
			/* v2.1 android doesn't pre-apply tints */
			this.setTints4v2_1();
			//this.setPaddingIceCream();
			
			/* Lets add default layer */
			
						
		}  
		catch (Exception e)
		{
			/* Log our exception */
			//Log.e("ERROR", "ERROR IN CODE: " + e.toString());
	 
			/* Print the error   */
			//e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	private void setPaddingIceCream() {
		
		/* Lets get the width/height */
		Display display = getWindowManager().getDefaultDisplay(); 
		int height = display.getHeight();
		
		
		if (height > 300)
		{
			this.top_1.setPadding(0, 0, 0, 55);
		}
		else
		{
			
		}
	}

	private void setTints4v2_1() 
	{
		//ImageView flavor_1 = (ImageView) findViewById(R.id.flv_banana_coconut);
		//flavor_1.clearColorFilter();
		//flavor_1.setColorFilter(Color.parseColor(this.getString(R.color.banana_coconut)), Mode.SRC_ATOP);
		//ImageView flavor_2 = (ImageView) findViewById(R.id.flv_blueberry);
		
		
	}

	public void switchToppings(View target)
	{
		int temp = 0;
		
		/* reset back to beginning */
		if (this.topping_id == 2)
		{
			this.topping_id = 0;
		}
		
		/* Switch for each and every flavor */
		switch (target.getId())
		{
		case R.id.almonds:
			temp = R.drawable.top_almonds;
			this.name = this.getString(R.string.top_almonds);
			break;	
		case R.id.banana:
			temp = R.drawable.top_bananas;
			this.name = this.getString(R.string.top_banana);
			break;	
		case R.id.blueberry:
			temp = R.drawable.top_blueberry;
			this.name = this.getString(R.string.top_blueberry);
			break;	
		case R.id.blackberry:
			temp = R.drawable.top_blackberry;
			this.name = this.getString(R.string.top_blackberry);
			break;	
		case R.id.brownie:
			temp = R.drawable.top_brownie;
			this.name = this.getString(R.string.top_brownie);
			break;		
		case R.id.butterfinger:
			temp = R.drawable.top_butterfinger;
			this.name = this.getString(R.string.top_butterfinger);
			break;	
		case R.id.captaincrunch:
			temp = R.drawable.top_captaincrunch;
			this.name = this.getString(R.string.top_captain_crunch);
			break;
		case R.id.cherries:
			temp = R.drawable.top_cherry;
			this.name = this.getString(R.string.top_cherries);
			break;	
		case R.id.coconut:
			temp = R.drawable.top_coconuts;
			this.name = this.getString(R.string.top_coconut);
			break;	
		case R.id.cocopebbles:
			temp = R.drawable.top_cocopebbles;
			this.name = this.getString(R.string.top_coco_pebbles);
			break;
		case R.id.fruitypebbles:
			temp = R.drawable.top_fruitypebbles;
			this.name = this.getString(R.string.top_fruity_pebbles);
			break;
		case R.id.hersheykiss:
			temp = R.drawable.top_hersheykiss;
			this.name = this.getString(R.string.top_hershey_kiss);
			break;	
		case R.id.jellybean:
			temp = R.drawable.top_jellybeans;
			this.name = this.getString(R.string.top_jelly_bean);
			break;		
		case R.id.kiwi:
			temp = R.drawable.top_kiwi;
			this.name = this.getString(R.string.top_kiwi);
			break;			
		case R.id.mango:
			temp = R.drawable.top_mango;
			this.name = this.getString(R.string.top_mango);
			break;	
		case R.id.marshmellow:
			temp = R.drawable.top_marshmellow;
			this.name = this.getString(R.string.top_marshmellow);
			break;	
		case R.id.mm:
			temp = R.drawable.top_mm;
			this.name = this.getString(R.string.top_mm);
			break;		
		case R.id.mochio:
			temp = R.drawable.top_mochiballs;
			this.name = this.getString(R.string.top_mochio);
			break;	
		//case R.id.orange :
			//temp = R.drawable.top_orange;
			//break;		
		case R.id.oreo:
			temp = R.drawable.top_oreo;
			this.name = this.getString(R.string.top_oreo);
			
			if (this.hehe == 2)
			{
				this.copyright();
			}
			break;		
		case R.id.peach:
			temp = R.drawable.top_peach;
			this.name = this.getString(R.string.top_peach);
			break;		
		case R.id.pear:
			temp = R.drawable.top_pear;
			this.name = this.getString(R.string.top_pear);
			break;		
		case R.id.pecan:
			temp = R.drawable.top_pecan;
			this.name = this.getString(R.string.top_pecan);
			break;		
		case R.id.raspberry:
			temp = R.drawable.top_rasberry;
			this.name = this.getString(R.string.top_raspberry);
			break;		
		case R.id.reeses:
			temp = R.drawable.top_reese;
			this.name = this.getString(R.string.top_reeses);
			break;		
		case R.id.sprinkles:
			temp = R.drawable.top_sprinkles;
			this.name = this.getString(R.string.top_sprinkles);
			break;		
		case R.id.strawberry:
			temp = R.drawable.top_strawberry;
			this.name = this.getString(R.string.top_strawberry);
			break;		
		case R.id.sunflower:
			temp = R.drawable.top_sunflowerseeds;
			this.name = this.getString(R.string.top_sunflower);
			break;		
		case R.id.watermelon:
			temp = R.drawable.top_watermelon;
			this.name = this.getString(R.string.top_watermelon);
			break;		
		}
		
		/* set filter now */
		if (this.name.equalsIgnoreCase(""))
		{
			
		}
		else
		{
			this.MessageBoxS(this.name + " added.");
			
			/* Lets add the toppings in order */
			switch (this.topping_id)
			{
			case 0:
				this.top_1.setImageDrawable(getResources().getDrawable(temp));
				break;
			case 1:
				this.top_2.setImageDrawable(getResources().getDrawable(temp));
				break;
			case 2:
				this.top_3.setImageDrawable(getResources().getDrawable(temp));
				break;
			default:
				this.top_1.setImageDrawable(getResources().getDrawable(temp));
				break;
			}
		}
		

		
		/* Slowly increase */
		this.topping_id++;

	}
	/* Function to switch images */
	public void switchImages(View target)
	{
		/* set temp var */
		int colorToUse = 0;
		
		/* Switch for each and every flavor */
		switch (target.getId())
		{
			case R.id.flv_banana_coconut:
				colorToUse = R.color.banana_coconut;
				name = this.getString(R.string.banana_coconut);
				break;
			case R.id.flv_blueberry:
				colorToUse = R.color.blueberry_solid;
				name = this.getString(R.string.blueberry);
				break;
			case R.id.flv_cake_batter:
				colorToUse = R.color.cake_batter;
				name = this.getString(R.string.cake_batter);
				break;
			case R.id.flv_cheesecake:
				colorToUse = R.color.cheesecake;
				name = this.getString(R.string.cheesecake);
				break;
			case R.id.flv_cherry:
				colorToUse = R.color.cherry;
				name = this.getString(R.string.cherry);
				break;
			case R.id.flv_chocolate:
				colorToUse = R.color.chocolate;
				name = this.getString(R.string.chocolate);
				break;
			case R.id.flv_chocolate_raspberry:
				colorToUse = R.color.chocolate_raspberry;
				name = this.getString(R.string.chocolate_raspberry);
				break;
			case R.id.flv_coffee_lover:
				colorToUse = R.color.coffee_lover;
				name = this.getString(R.string.coffee_lover);
				break;
			case R.id.flv_cookies_and_cream:
				colorToUse = R.color.cookies_and_cream;
				name = this.getString(R.string.cookies_and_cream);
				break;
			case R.id.flv_dole_pineapple:
				colorToUse = R.color.dole_pineapple;
				name = this.getString(R.string.dole_pineapple);
				break;
			case R.id.flv_eggnog:
				colorToUse = R.color.eggnog;
				name = this.getString(R.string.eggnog);
				this.hehe++;
				break;
			case R.id.flv_gingerbread:
				colorToUse = R.color.gingerbread;
				name = this.getString(R.string.gingerbread);
				break;
			case R.id.flv_green_apple:
				colorToUse = R.color.green_apple;
				name = this.getString(R.string.green_apple);
				break;
			case R.id.flv_green_tea:
				colorToUse = R.color.green_tea;
				name = this.getString(R.string.green_tea);
				break;
			case R.id.flv_honeydew:
				colorToUse = R.color.honeydew;
				name = this.getString(R.string.honeydew);
				break;
			case R.id.flv_kiwi:
				colorToUse = R.color.kiwi;
				name = this.getString(R.string.kiwi);
				break;
			case R.id.flv_lemon:
				colorToUse = R.color.lemon;
				name = this.getString(R.string.lemon);
				this.hehe +=2;
				break;
			case R.id.flv_lychee:
				colorToUse = R.color.lychee;
				name = this.getString(R.string.lychee);
				break;
			case R.id.flv_mango_pomegranate:
				colorToUse = R.color.mango_pomegranate;
				name = this.getString(R.string.mango_pomegranate);
				break;
			case R.id.flv_mint_chocolate:
				colorToUse = R.color.mint_chocolate;
				name = this.getString(R.string.mint_chocolate);
				break;
			case R.id.flv_orange:
				colorToUse = R.color.orange;
				name = this.getString(R.string.orange);
				break;
			case R.id.flv_passion_fruit:
				colorToUse = R.color.passion_fruit;
				name = this.getString(R.string.passion_fruit);
				break;
			case R.id.flv_peach:
				colorToUse = R.color.peach;
				name = this.getString(R.string.peach);
				break;
			case R.id.flv_peanut_butter:
				colorToUse = R.color.peanut_butter;
				name = this.getString(R.string.peanut_butter);
				break;
			case R.id.flv_pistachio:
				colorToUse = R.color.pistachio;
				name = this.getString(R.string.pistachio);
				break;
			case R.id.flv_pumpkin:
				colorToUse = R.color.pumpkin;
				name = this.getString(R.string.pumpkin);
				break;
			case R.id.flv_raspberry:
				colorToUse = R.color.raspberry;
				name = this.getString(R.string.raspberry);
				break;
			case R.id.flv_red_velvet:
				colorToUse = R.color.red_velvet;
				name = this.getString(R.string.red_velvet);
				break;
			case R.id.flv_strawberry:
				colorToUse = R.color.strawberry;
				name = this.getString(R.string.strawberry);
				break;
			case R.id.flv_taro:
				colorToUse = R.color.taro;
				name = this.getString(R.string.taro);
				break;
			case R.id.flv_tart:
				colorToUse = R.color.tart;
				name = this.getString(R.string.tart);
				break;
			case R.id.flv_vanilla:
				colorToUse = R.color.vanilla_solid;
				name = this.getString(R.string.vanilla);
				break;
			case R.id.flv_watermelon:
				colorToUse = R.color.watermelon;
				name = this.getString(R.string.watermelon);
				this.hehe--;
				break;
			case R.id.flv_white_chocolate:
				colorToUse = R.color.white_chocolate;
				name = this.getString(R.string.white_chocolate);
				break;
				
			default:
				//Log.e("error", this.getString(target.getId()));
				break;
		}
			if (this.name.equalsIgnoreCase(""))
			{
				
			}
			else
			{
				/* set filter now */
				this.MessageBoxS(name + " added.");
				
				/* switch for flavor */
				switch (this.flavor_id)
				{
					case 1:
						this.layer_1.setColorFilter(Color.parseColor(this.getString(colorToUse)), Mode.MULTIPLY);
						break;
					case 2:
						this.layer_2.setColorFilter(Color.parseColor(this.getString(colorToUse)), Mode.MULTIPLY);
						break;
					case 3:
						this.layer_3.setColorFilter(Color.parseColor(this.getString(colorToUse)), Mode.MULTIPLY);
						break;
					case 4:
						this.layer_4.setColorFilter(Color.parseColor(this.getString(colorToUse)), Mode.MULTIPLY);
						break;
					case 5:
						this.layer_5.setColorFilter(Color.parseColor(this.getString(colorToUse)), Mode.MULTIPLY);
						break;
					default:
						this.layer_1.setColorFilter(Color.parseColor(this.getString(colorToUse)), Mode.MULTIPLY);
						break;
				}
			}
	}


	public void upLayer(View target)
	{
		/* Check for max */
		if (this.flavor_id == 5)
		{
			/* Pop message */
			this.flavor_id = 5;
			
			/* Show messagebox */
			this.MessageBoxS(this.getString(R.string.error_top_flavor));
		}
		else
		{
			/* add one */
			this.flavor_id++;
		}
		
		/* Highlight if going down */
		this.highlightLayer();
	}
	
	public void downLayer(View target)
	{
		if (this.flavor_id == 1)
		{
			/* reset */
			this.flavor_id = 1;
			
			
			/* message */
			this.MessageBoxS(this.getString(R.string.error_bottom_flavor));
		}
		else
		{
			/* loose 1 */
			this.flavor_id--;
		}
		
		/* run our highlite */
		this.highlightLayer();
		
	}

	public void highlightLayer()
	{
		//--------------------------
		// Highlight our active layer
		//--------------------------
		this.unSelectAll();
		
		switch (this.flavor_id)
		{
		case 1:
			this.layer_1.setImageDrawable(getResources().getDrawable(R.drawable.layer_1_selected));
			break;
		case 2:
			this.layer_2.setImageDrawable(getResources().getDrawable(R.drawable.layer_2_selected));
			break;
		case 3:
			this.layer_3.setImageDrawable(getResources().getDrawable(R.drawable.layer_3_selected));
			break;
		case 4:
			this.layer_4.setImageDrawable(getResources().getDrawable(R.drawable.layer_4_selected));
			break;
		case 5:
			this.layer_5.setImageDrawable(getResources().getDrawable(R.drawable.layer_5_selected));
			break;	
		default:
			break;
		}
	}
	
	private void unSelectAll()
	{
		this.layer_1.setImageDrawable(getResources().getDrawable(R.drawable.icecream1));
		this.layer_2.setImageDrawable(getResources().getDrawable(R.drawable.icecream2));
		this.layer_3.setImageDrawable(getResources().getDrawable(R.drawable.icecream3));
		this.layer_4.setImageDrawable(getResources().getDrawable(R.drawable.icecream4));
		this.layer_5.setImageDrawable(getResources().getDrawable(R.drawable.icecream5));
	}
	
	public void convertICEtoString()
	{
		//String iceCream = "";
		//--------------------------------
		// Turns this into something we can reproduce
		//--------------------------------
		
	}
	
	public void reset()
	{
		this.unSelectAll();
		this.setTints4v2_1();
		this.hehe = 0;
		
		this.layer_1.setColorFilter(Color.parseColor(this.getString(R.color.vanilla_solid)), Mode.MULTIPLY);
		this.layer_2.setColorFilter(Color.parseColor(this.getString(R.color.vanilla_solid)), Mode.MULTIPLY);
		this.layer_3.setColorFilter(Color.parseColor(this.getString(R.color.vanilla_solid)), Mode.MULTIPLY);
		this.layer_4.setColorFilter(Color.parseColor(this.getString(R.color.vanilla_solid)), Mode.MULTIPLY);
		this.layer_5.setColorFilter(Color.parseColor(this.getString(R.color.vanilla_solid)), Mode.MULTIPLY);
		
		this.top_1.setImageDrawable(getResources().getDrawable(R.drawable.blank_img));
		this.top_2.setImageDrawable(getResources().getDrawable(R.drawable.blank_img));
		this.top_3.setImageDrawable(getResources().getDrawable(R.drawable.blank_img));
		
		
	}
	
	public void MessageBoxS(String message){
		
		//--------------------------------
		// Run the Toast, then cancel it
		//
		//--------------------------------
			
		if (this.toast == null)
		{
		    this.toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
		}
		else
		{
			this.toast.setText(message);
		}
		this.toast.show();
	    
	    
	    
	}
	/* Phone call / paused */
	public void onPause(Bundle savedInstanceState)
	{
		super.onPause();
	}
	/* Resume */
	public void onResume(Bundle savedInstanceState)
	{
		super.onResume();
		//Log.e("onResume", "onResume has been hit");
	}
	
	/* Run this everytime */
	public void onStart()
	{
		super.onStart();
		//Log.e("onStart", "onStart has been hit");
	}
	
	/* Lets load all the images */
	public void loadImages()
	{
		//ImageView almonds = (ImageView) findViewById(R.id.almonds);
		
	}
	private void copyright()
	{
		 this.hehe = 0;
		 AlertDialog.Builder adb=new AlertDialog.Builder(createActivity.this);
		 
		 adb.setTitle("Meet the Team");
		 adb.setMessage("connor.tumbleson : developer \n" +
				 		"patrick.hutfless : developer \n" +
				 		"sydney.goldstein : designer  \n" +
				 		"lindsey.swirbul  : designer  \n" +
				 		"ben.hylton       : business  ");
		 adb.setNeutralButton("Thanks", null);
		 adb.show();
	}
}
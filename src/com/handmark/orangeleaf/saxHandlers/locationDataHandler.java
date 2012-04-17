/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf.saxHandlers;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class locationDataHandler extends DefaultHandler {
	
	/* Locations */
	private boolean in_locations 	= false;
	private boolean in_location 	= false;
	private boolean in_city 		= false;
	private boolean in_state		= false;
	private boolean in_address 		= false;
	private boolean in_hours		= false;
	private boolean in_zip			= false;
	private boolean in_tele			= false;
	private boolean end				= false;
	
	String currentValue 			= null;
	boolean buffering 				= false;
	private StringBuilder locationText = new StringBuilder();
	int counter						= 0;
	int typeID						= 0;
	
	/* Location data handler */
	private locationData locationDataSet = new locationData();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public locationData getParsedData() {
		return this.locationDataSet;
	}
	
	/* throw them false */
	public void falseAll()
	{
		this.in_address = false;
		this.in_city 	= false;
		this.in_hours 	= false;
		this.in_state	= false;
		this.in_zip		= false;
		this.in_tele 	= false;
	}
	
	/* Start reading */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		/* log what were on */
		//Log.i(localName.toString(), namespaceURI.toString());
		
		/* Check for locations tag */
		if (localName.equals("locations"))
		{
			this.in_locations = true;
			this.in_location  = false;
			this.buffering 	  = false;
		}
		
		/* Check for locations and one location */
		if (localName.equals("location") && (this.in_locations))
		{
			this.in_location = true;
			this.buffering 	 = false;
		}
		
		//-----------------------------------------------------
		// Check for each type of data, then set accordingly
		//-----------------------------------------------------
		if (localName.equals("city"))
		{
			this.in_city = true;
			this.typeID = 1;
		}
		if (localName.equals("state"))
		{
			this.in_state = true;
			this.typeID = 2;
		}
		if (localName.equals("address"))
		{
			this.in_address = true;
			this.typeID = 0;
		}
		if (localName.equals("hours"))
		{
			this.in_hours = true;
			this.typeID = 3;
		}
		if (localName.equals("zip"))
		{
			this.in_zip = true;
			this.typeID = 4;
		}
		if (localName.equals("phone"))
		{
			this.in_tele = true;
			this.typeID = 5;
		}
		//-------------------------------------------
		// Here comes a massive statement
		//
		// Check for locations & location
		//
		// then check for either
		// city,state,zip,address,hours, telephone
		//-------------------------------------------
		if ((this.in_location) && (this.in_locations) && ((this.in_city) || (this.in_state) || (this.in_address) || (this.in_hours) || (this.in_zip) || (this.in_tele)))
		{
			/* clear it */
			locationText.delete(0, locationText.length());
			locationText.setLength(0);
			
			this.buffering = true;
		}
		
		/* Check if they are all true */
		if ((this.in_location) && (this.in_locations) && (this.in_city) && (this.in_state) && (this.in_address) && (this.in_hours) && (this.in_zip) && (this.in_tele));
		{
			/* They are all true, so lets end this location */
		}
	}
	
	/* Hit a tag, stop reading */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException 
	{
		/* check if end location */
		if (end)
		{
			/* set to a string */
			String newLocation = locationText.toString();
			
				/* Send to the storage thingy */
				locationDataSet.storeData(newLocation.toString(), this.typeID);
				
				/* clear it */
				locationText.delete(0, locationText.length());
				locationText.setLength(0);
		}
		if (localName == "address")
		{
			this.in_address = false;
		}
		else if(localName == "state")
		{
			this.in_state = false;
		}
		else if(localName == "city")
		{
			this.in_city  = false;
		}
		else if(localName == "hours")
		{
			this.in_hours = false;
		}
		else if(localName == "zip")
		{
			this.in_zip = false;	
		}
		else if(localName == "phone")
		{
			this.in_tele = false;
		}
		
		this.buffering = false;
		this.end       = false;
	}
	
	/* Called on the following structure: 
	 * <tag>characters</tag> 
	 * pat was here
	 * could run multiple times, use string builder
	 */
	@Override
    public void characters(char ch[], int start, int length) {
		
		this.locationText.delete(0, this.locationText.length());
		
		
		/* Don't do this if its empty */
		if ((buffering == true) && (in_locations == true) && ((in_location == true)))
		{
			
			/* Better method than twitter. */
			String chars = new String(ch, start, length);
			chars = chars.trim();
			
			/* set to our global var */
			this.locationText.append(chars);
			
			/* We've looped the entire string, end */
			this.buffering = false;
			this.end	   = true;
		}
    }
	
	/* Ran before startDocument */
	@Override
	public void startDocument() throws SAXException {
		this.locationDataSet = new locationData();
		// Do some startup if needed
	}

	/* Ran after endDocument */
	@Override
	public void endDocument() throws SAXException 
	{
		
	}

}

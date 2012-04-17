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

public class tweetsDataHandler extends DefaultHandler {
	
	/* Tweets */
	private boolean in_results 		= false;
	private boolean in_text 		= false;
	@SuppressWarnings("unused")
	private boolean in_pic			= false;
	String currentValue 			= null;
	boolean buffering 				= false;
	private StringBuilder tweetText = new StringBuilder();
	int counter						= 0;
	
	
	/* Dataset */
	private tweetData tweetsDataSet 		 = new tweetData();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public tweetData getParsedData() {
		return this.tweetsDataSet;
	}
	
	/* Start reading */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException 
	{
		/* reset counter */
		this.counter = 0;
		tweetText.setLength(0);
		this.in_pic = false;
		
		//Log.i(qName.toString(), namespaceURI.toString()); #DEBUG
		
		/* Check for result array */
		if (localName.equals("entry"))
		{
			this.in_results = true;
		}
		/* check for tweet */
		else if (localName.equals("title") && (in_results))
		{
			
			this.in_text = true;
			this.in_pic  = false;
			this.in_results = true;
			
			/* pull results */
            this.buffering = true;

		}
		/* check for pic to match with tweet */
		else if(localName.equals("link") && (in_results))
		{
			this.in_pic = true;
			this.in_text = false;
			this.in_results = true;
			
			//Log.i(localName.toString(),atts.getValue("rel"));  #DEBUG
			//Log.i(localName.toString(),atts.getValue("href")); #DEBUG
			
			/* Check if this link has an image :o */
			if (atts.getValue("rel").toString().equals("image"))
			{
				/* grab their pic */
				tweetsDataSet.setExtractedPic(atts.getValue("href"));
			}
		}
	}
	
	/* Hit a tag, stop reading */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException 
	{
		/* Check for entry+text or entry+img */
		if (((in_text) && (in_results)) && (in_results))
		{
			/* return stringBuilder then clear */
			String textBetween = tweetText.toString();
			
			/* Check for images/pics */
			if (in_text == true)
			{
				tweetsDataSet.setExtractedString(textBetween);
			}
			
			/* clear now */
			tweetText.delete(0, tweetText.length());
			
			/* end reader */
			tweetText.setLength(0);
			
		}
		/* Close our tags */
		if (localName == "entry")
		{
			this.in_results = false;
		}
		else if(localName == "title")
		{
			this.in_text = false;
		}
		else if((localName == "link") && (this.buffering == true))
		{
			//this.in_pic = false;
		}
		
		/* close buffer */
		this.buffering = false;
		
	}
	
	/* Called on the following structure: 
	 * <tag>characters</tag> 
	 */
	@Override
    public void characters(char ch[], int start, int length) {
		
		/* Don't do this if its empty */
		if ((buffering == true) && (in_results == true) && ((in_text == true)))
		{
			/* Loop for every letter/char they sent down */
			for (int i = start; i < (start + length); i++)
			{
				/* can we display this? */
				if (Character.isIdentifierIgnorable(ch[i]))
				{
					// can't add this char
				} else 
				{
					/* Check if the length is equal */
					if (tweetText.length() == length)
					{
					}
					else
					{
						tweetText.append(ch[i]);
					}
				}
			}
		}
    }
	
	/* Ran before startDocument */
	@Override
	public void startDocument() throws SAXException {
		this.tweetsDataSet  = new tweetData();
	}

	/* Ran after endDocument */
	@Override
	public void endDocument() throws SAXException {
		/* End of document */
	}

}

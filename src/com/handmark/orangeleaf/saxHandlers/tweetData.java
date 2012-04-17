/*
 * Connor Tumbleson
 * Patrick Hutfless
 * 
 * CAPS
 * rev100
 */
package com.handmark.orangeleaf.saxHandlers;

import java.util.ArrayList;

public class tweetData {
	public String[] tweetPics;
	public String[] tweets;
	public String[] filteredWords = {"cock", "fuck", "cunt", "pussy","pubic", "tits", "motherfucker", "nipples", "shit", "mother fucker", "bitch"};
	
	/* arraylists */
	public ArrayList<String> tweetPicsL;
	public ArrayList<String> tweetsL;
	private int counter  = 0;
	private int counter1 = 0;

	public String[] getExtractedStrings() {
		return this.tweets;
		}
	public String[] getExtractedPics()
	{
		return this.tweetPics;
	}
	public void setExtractedString(String extractedString) {
		
		/* Check if counter is one to init this array */
		if (this.counter == 0)
		{
			this.tweetsL = new ArrayList<String>();
		}
		
		/* Check for filtered text */
		for(int x = 0; x < filteredWords.length; x++)
		{
			/* Make our String into CharSequence */
			CharSequence cs;
			cs = filteredWords[x].toString();
			
			/* Check for the bad word */
			if (extractedString.toString().toLowerCase().contains(cs))
			{
				extractedString = "";
				continue;
			}
		}
		
		
		/* Add to our array */
		this.tweetsL.add(extractedString);
		
		/* increase */
		this.counter++;
	}
	
	/* Tweet Pictures function */
	public void setExtractedPic(String extractedString)
	{
		/* If counter isn't 1 then reset */
		if (counter1 == 0)
		{
			this.tweetPicsL = new ArrayList<String>();
		}
		
		/* Add to the array */
		String temp = extractedString.replace("normal", "bigger");
		this.tweetPicsL.add(temp);
		
		/* up up */
		this.counter1++;
	}
	public Boolean runFirst()
	{
		/* init */
		this.tweets = new String[this.tweetsL.size()];
		this.tweetPics = new String[this.tweetPicsL.size()];
		
		if (this.tweetsL.size() == 0)
		{
			return false;
		}
		
		int count = 0;
		/* Loop through arraylist */
		for (int i = 0; i < this.tweetsL.size(); i++)
		{
			/* check for blank, then skip */
			if (this.tweetsL.get(i).toString().equalsIgnoreCase(""))
			{
				
			}
			else
			{
				/* set up that link */
				this.tweets[count] = this.tweetsL.get(i);
				this.tweetPics[count] = this.tweetPicsL.get(i);
				count++;
			}
		}
		
		/*cleanup */
		this.tweetsL.clear();
		this.tweetPicsL.clear();
		return true;
	}
}
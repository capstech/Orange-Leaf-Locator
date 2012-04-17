package com.handmark.orangeleaf.imageHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
	 public static String doGet(String url)throws ClientProtocolException, IOException{
		 try
		 {
		  HttpGet getRequest = new HttpGet(url);
		  
		  /* Test for stuff */
		  //String hostName = getRequest.getURI().getHost();
		  
		  HttpClient client = new DefaultHttpClient();
		  HttpResponse response = client.execute(getRequest);

			  return responseToString(response);
		 }
		 catch (ClientProtocolException exception)
		 {
			 exception.printStackTrace();
			 return "-1";
		 }
		 catch (IOException exception)
		 {
			 exception.printStackTrace();
			 return "-1";
		 }
	 }
	 

	 private static String responseToString(HttpResponse httpResponse)
	 throws IllegalStateException, IOException{
	  StringBuilder response = new StringBuilder();
	  String aLine = new String();

	  //InputStream to String conversion
	  InputStream is = httpResponse.getEntity().getContent();
	  BufferedReader reader = new BufferedReader(new InputStreamReader(is));

	  while( (aLine = reader.readLine()) != null){
	   response.append(aLine);
	  }
	  reader.close();

	  return response.toString();
	 }

}
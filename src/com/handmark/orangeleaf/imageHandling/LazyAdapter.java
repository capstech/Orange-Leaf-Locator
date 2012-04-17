

package com.handmark.orangeleaf.imageHandling;

import android.app.Activity;
import android.content.Context;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.orangeleaf.R;
import com.handmark.orangeleaf.imageHandling.ImageLoader;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] tweetsImg;
    private String[] tweetsTxt;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, String[] p, String[] d) {
        activity  = a;
        tweetsImg = d; 
        tweetsTxt = p; 
        
        /* Inflate some now */
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return tweetsImg.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        public TextView text;
        public ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder;
        
        /* Dont inflate if we exist :o */
        if(convertView==null){
            vi = inflater.inflate(R.layout.tweet_row, null);
            holder=new ViewHolder();
            holder.text=(TextView)vi.findViewById(R.id.label);
            holder.image=(ImageView)vi.findViewById(R.id.icon);
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();
        
        /* Set txt/image */
        holder.text.setText(tweetsTxt[position]);
        holder.image.setTag(tweetsImg[position]);
        imageLoader.DisplayImage(tweetsImg[position], activity, holder.image);
        
		//Log.i("Current Position: ", Integer.toString(position));
        return vi;
    }
}
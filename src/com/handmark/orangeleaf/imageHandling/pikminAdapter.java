package com.handmark.orangeleaf.imageHandling;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.handmark.orangeleaf.R;

	//----------------------------------------------
	// This adapter is the same as the LazyAdapter
	// Just configured for use with locations
	// connor.tumbleson yeeeeeeeeeeeeee
	//----------------------------------------------
	public class pikminAdapter extends BaseAdapter {
	    
	    private Activity activity;
	    private String[][] locations;
	    
	    private static LayoutInflater inflater=null; 
	    
	    public pikminAdapter(Activity a, String[][] p) {
	        activity  = a;
	        this.locations = p;
	        
	        /* Inflate some now */
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    
	    public static class ViewHolder{
	        public TextView state;
	        public TextView address;
	        public TextView city_zip;
	        public TextView zip;
	        public TextView tele;
	        public TextView hours;
	        
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        ViewHolder holder;
	        if(convertView==null)
	        {
	            vi = inflater.inflate(R.layout.store_row, null);
	            holder = new ViewHolder();
	            holder.state   =     (TextView) vi.findViewById(R.id.state);
	            holder.address =     (TextView) vi.findViewById(R.id.address);
	            holder.city_zip =     (TextView) vi.findViewById(R.id.city_zip);
	            //holder.zip     =     (TextView) vi.findViewById(R.id.zip);
	            holder.tele    =     (TextView) vi.findViewById(R.id.telephone);
	            holder.hours   =     (TextView) vi.findViewById(R.id.hours);
	            vi.setTag(holder);
	        }
	        else
	        {
	            holder=(ViewHolder)vi.getTag();
	        }
	        holder.address.setText(this.locations[position][0] + "");
	        holder.city_zip.setText(this.locations[position][1] + ", " + this.locations[position][4]);
	        holder.state.setText(this.locations[position][2] + " ");
	        //holder.zip.setText(this.locations[position][4]);
	        holder.hours.setText("" + this.locations[position][3] + "    ");
	        holder.tele.setText(this.locations[position][5]);
	        
			//Log.i("Current Position: ", Integer.toString(position));
	        return vi;
	    }

		public int getCount() {
			// TODO Auto-generated method stub
			return this.locations.length;
		}


	}
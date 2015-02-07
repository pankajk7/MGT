package com.mygrouptracker.mygrouptracker.adapter;

import java.util.HashMap;
import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.Home_Navigation;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListBaseAdapter extends BaseAdapter{

	// XML node keys
    static final String KEY_TAG = "home"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_ICON = "icon";
    static final String KEY_NAME = "name";
    LayoutInflater inflater;
	ImageView imageview;
	List<HashMap<String,String>> homeDataCollection;
	ViewHolder viewholder;
	
	
	Context context;
    
	public ListBaseAdapter(Context context, List<HashMap<String,String>> hashmap)
	{
	  this.context=context;
	  this.homeDataCollection=hashmap;	
	  inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(homeDataCollection==null){
			;
			return 0;
		}
		return homeDataCollection.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		
	    if(convertView==null){	     
	      view = inflater.inflate(R.layout.navigation_listview_items, null);
	      viewholder = new ViewHolder();
	     
	      viewholder.iconImage = (ImageView) view.findViewById(R.id.list_image);
	      viewholder.name=(TextView)view.findViewById(R.id.name);
	      viewholder.arrowImage =(ImageView)view.findViewById(R.id.arrow); 
	 
	      view.setTag(viewholder);
	    }
	    else{	    	
	    	viewholder = (ViewHolder)view.getTag();
	    }

	      // Setting all values in listview
	      
	     //Setting an image
	      String uri = "drawable/"+ homeDataCollection.get(position).get(KEY_ICON);
	      int imageResource = view.getContext().getApplicationContext().getResources().getIdentifier(uri, null, view.getContext().getApplicationContext().getPackageName());
	      Drawable image = view.getContext().getResources().getDrawable(imageResource);
	      viewholder.iconImage.setImageDrawable(image);
	      
	      //Setting name
	      if(homeDataCollection.get(position).get(KEY_NAME).toString().equals("My Messages")){
//	    	  new Home_Navigation().getMessagesCount();
	    	  if(Home_Navigation.countMessage==0)
	    		  viewholder.name.setText(homeDataCollection.get(position).get(KEY_NAME));
	    	  else
	    		  viewholder.name.setText(homeDataCollection.get(position).get(KEY_NAME)+" ("+Home_Navigation.countMessage+")");
	      }
	      else
	    	  viewholder.name.setText(homeDataCollection.get(position).get(KEY_NAME));
	      
	      
	      if(position==0)
	      {
	    	  view.setBackgroundResource(R.drawable.top_round_corners);
	      }
	      else if(position==getCount()-1)
	      {
	    	  view.setBackgroundResource(R.drawable.bottom_round_corners);
	      }
	      
	      return view;
	}
	
static class ViewHolder{
		ImageView iconImage;
		TextView name;		
		ImageView arrowImage;
	}



}

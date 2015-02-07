package com.mygrouptracker.mygrouptracker.adapter;

import java.util.HashMap;
import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter.ViewHolder;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.entity.Station;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class StationLocationBaseAdapter extends BaseAdapter {

	// XML node keys
    static final String KEY_TAG = "stations"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    
    LayoutInflater inflater;
	ImageView imageview;
//	SingleNameListEntity objSingleNameList=new SingleNameListEntity();
	List<Station> stationDataCollection;
	Station stationObj=new Station();
	ViewHolder viewholder;
	Context context;
    
	public StationLocationBaseAdapter(Context context, List<Station> list)
	{
		  this.stationDataCollection = list;	
		  this.context=context;
		  inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(stationDataCollection==null){
			;
			return 0;
		}
		return stationDataCollection.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
	    if(convertView==null){	     
	      convertView = inflater.inflate(R.layout.station_location_items, null);	      
	    }
	    
	    TextView name = (TextView)convertView.findViewById(R.id.textview_stationlocation_stationname);
	    TextView direction= (TextView)convertView.findViewById(R.id.textview_stationlocation_getdirection);
	 	
		stationObj=stationDataCollection.get(position);
		name.setText(stationObj.stationName);
		direction.setText("Get Direction");
		
		if(position==0)
	      {
	    	  convertView.setBackgroundResource(R.drawable.top_round_corners);
	      }
	      else if(position==getCount()-1)
	      {
	    	  convertView.setBackgroundResource(R.drawable.bottom_round_corners);
	      }
	      
	      return convertView;
	}

}

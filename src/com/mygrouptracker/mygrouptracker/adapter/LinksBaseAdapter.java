package com.mygrouptracker.mygrouptracker.adapter;

import java.util.ArrayList;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.entity.Links;
import com.mygrouptracker.mygrouptracker.utility.listview_populate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LinksBaseAdapter extends BaseAdapter{

	Links linksObj=new Links();
	ArrayList<Links> linksArrayList=new ArrayList<Links>();
	Context context;
	LayoutInflater inflater;
	
	public LinksBaseAdapter(Context context, ArrayList<Links> list)
	{
		linksArrayList=list;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(linksArrayList==null){
			;
			return 0;
		}
		return linksArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return linksArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.single_name_list_items, parent,false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.textview_singlenamelist_name);
		TextView linkAddress = (TextView)convertView.findViewById(R.id.textview_singlenamelist_linkaddress);
				
		linksObj=linksArrayList.get(position);		
		name.setText(linksObj.linkName);
		linkAddress.setText(linksObj.linkAddress);
		
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

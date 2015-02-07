package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SingleNameListBaseAdapter extends BaseAdapter{

	SingleNameListEntity objSingleNameList=new SingleNameListEntity();
	List<SingleNameListEntity> listSingleList;
	LayoutInflater inflater;
	
	public SingleNameListBaseAdapter(Activity activity, List<SingleNameListEntity> list) 
	{
		listSingleList=list;
		inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(listSingleList==null){
			;
			return 0;
		}
		return listSingleList.size();
	}

	@Override
	public Object getItem(int position) {
		return listSingleList.get(position);
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
				
		objSingleNameList=listSingleList.get(position);		
		name.setText(objSingleNameList.linkName);
		linkAddress.setText(objSingleNameList.linkAddress);
		
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

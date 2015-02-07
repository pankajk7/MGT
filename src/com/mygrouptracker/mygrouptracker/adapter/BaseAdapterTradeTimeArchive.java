package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.TradeTimeSummary;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeArchiveEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BaseAdapterTradeTimeArchive extends BaseAdapter{

//	TradeTimeArchiveEntity objTradeTimeArchive=new TradeTimeArchiveEntity();
	TradeTimeEntity objTradeTimeArchive = new TradeTimeEntity();
	List<TradeTimeEntity> listTradeTimeArchive;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	
	public BaseAdapterTradeTimeArchive(Context context, List<TradeTimeEntity> list)
	{
	  listTradeTimeArchive=list;
	  inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  this.context=context;
	}
	
	@Override
	public int getCount() {
		if(listTradeTimeArchive==null){
			;
			return 0;
		}
		return listTradeTimeArchive.size();
	}

	@Override
	public Object getItem(int position) {
		return listTradeTimeArchive.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.trade_time_archive_items, parent,false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.textview_tradetimearchive_name);
		TextView date = (TextView)convertView.findViewById(R.id.textview_tradetimearchive_date);
		TextView hours = (TextView)convertView.findViewById(R.id.textview_tradetimearchive_hour);
		
		
		objTradeTimeArchive=listTradeTimeArchive.get(position);
		name.setText(objTradeTimeArchive.name);		
//		date.setText(objTradeTimeArchive.date);
//		date.setText(MgtDateFormat.dateFormatYYMMDD(objTradeTimeArchive.date));
		date.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objTradeTimeArchive.date));
		hours.setText(objTradeTimeArchive.hours);
		
		if (position % 2 == 0) 
			convertView.setBackgroundResource(R.color.green_listview);		    
		else
			convertView.setBackgroundResource(R.color.red_listview);
		
		return convertView;		
	}

}

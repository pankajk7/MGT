package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.activity.EditOvertime;
import com.mygrouptracker.mygrouptracker.activity.EditTradeTime;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.JetPlayer.OnJetEventListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


public class BaseAdapterTradeTimeSummary extends BaseAdapter {

	TradeTimeEntity objTradeTimeSummary=new TradeTimeEntity();
	List<TradeTimeEntity> listTradeTime;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	
	public BaseAdapterTradeTimeSummary(Context context, List<TradeTimeEntity> list)
	{
	  listTradeTime=list;
	  inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  this.context=context;
	}
	
	@Override
	public int getCount() {
		if(listTradeTime==null){
			;
			return 0;
		}
		return listTradeTime.size();
	}

	@Override
	public Object getItem(int position) {
		return listTradeTime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.trade_time_summary_items, parent,false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.textview_tradetimesummary_name);
		TextView hours = (TextView)convertView.findViewById(R.id.textview_tradetimesummary_hours);
		TextView date = (TextView)convertView.findViewById(R.id.textview_tradetimesummary_date);
		editButton=(Button)convertView.findViewById(R.id.btn_tradetimesummary_edit);
		editButton.setTag(position);
				
		objTradeTimeSummary=listTradeTime.get(position);		
		name.setText(objTradeTimeSummary.name);		
		hours.setText(objTradeTimeSummary.hours);
		date.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(objTradeTimeSummary.date));
	     if(objTradeTimeSummary.isOweMe.toLowerCase().equals("true"))
	     {
	    	 convertView.setBackgroundResource(R.color.green_listview);		 
	     }
	     else
	     {
	    	 convertView.setBackgroundResource(R.color.red_listview);		    
	     }
		
	/*	if (position % 2 == 0) 
			convertView.setBackgroundResource(R.color.green_listview);		    
		else
			convertView.setBackgroundResource(R.color.red_listview);
		*/
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objTradeTimeSummary=new TradeTimeEntity();
				objTradeTimeSummary=listTradeTime.get(position); //getting object from list by position
				new EditTradeTime((CalendarEvents) context).createEditTradeTimeLayout(objTradeTimeSummary);
//				Intent intent=new Intent(context,EditTradeTime.class);
//				intent.putExtra("TradeTimeEntity", objTradeTimeSummary);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
		
		return convertView;		
	}

}

package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import org.w3c.dom.ls.LSInput;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditAppointment;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseAdapterCompTime extends BaseAdapter{

	CompTimeEntity objCompTime;
	List<CompTimeEntity> listCompTime;
	LayoutInflater inflater;
	Context context;
	TextView dateTextView;
	TextView typeTextView;
	TextView hoursTextView;
	Button editButton;
	int comptimeEarned=0;
	int comptimeTaken=0;
	public BaseAdapterCompTime(Context context, List<CompTimeEntity> list)
	{		
		this.context=context;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listCompTime=list;
		 comptimeEarned=0;
		 comptimeTaken=0;
	}
	
	@Override
	public int getCount() {
		if(listCompTime==null){
			;
			return 0;
		}
	 return	listCompTime.size();
	}

	@Override
	public Object getItem(int position) {
		return listCompTime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;		
		if(row==null)
		{			
			row = inflater.inflate(R.layout.comp_time_summary_items, parent,false);			
			
		} 
		 
		try{		
			dateTextView = (TextView)row.findViewById(R.id.textview_comptimesummary_date);		
			typeTextView = (TextView)row.findViewById(R.id.textview_comptimesummary_type);
			hoursTextView = (TextView)row.findViewById(R.id.textview_comptimesummary_hours);
			editButton=(Button)row.findViewById(R.id.btn_comptimesummary_edit);
			editButton.setTag(position); //To get position onClick of button in listview
		
			objCompTime=new CompTimeEntity();
			objCompTime=listCompTime.get(position);		
//		dateTextView.setText(objCompTime.eventDate);
			dateTextView.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTime.eventDate));
			if(objCompTime.eventType.equals("0")){
				typeTextView.setText("Earned");
				row.setBackgroundResource(R.color.green_listview);	
				comptimeEarned+=Integer
				
				.valueOf(objCompTime.hours);
			}
				
			else{
				typeTextView.setText("Taken");
				row.setBackgroundResource(R.color.red_listview);
				comptimeTaken+=Integer
				
				.valueOf(objCompTime.hours);
			}
				
			hoursTextView.setText(objCompTime.hours);	 
		}
		catch(Exception e){
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
		
	    editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objCompTime=new CompTimeEntity();
				objCompTime=listCompTime.get(position); //getting object from list by position
				new EditCompTime((CalendarEvents) context,listCompTime).createEditCompTimeLayout(objCompTime);
//				Intent intent=new Intent(context,EditCompTime.class);
//				intent.putExtra("CompTimeEntity", objCompTime);
				/*intent.putExtra("earned",comptimeEarned);
				intent.putExtra("taken",comptimeTaken);*/
				//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
						
//		View view = super.getView(position, convertView, parent);  
		
	/*	if (position % 2 == 0) 
			row.setBackgroundResource(R.color.green_listview);		    
		else
			row.setBackgroundResource(R.color.red_listview);*/
		
		return row;		
	}	
	
}

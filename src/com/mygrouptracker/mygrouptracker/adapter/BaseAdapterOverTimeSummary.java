package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.activity.EditOvertime;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseAdapterOverTimeSummary extends BaseAdapter{

	OverTimeEntity objOverTime=new OverTimeEntity();
	List<OverTimeEntity> listOverTime;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	
	public BaseAdapterOverTimeSummary(Context context, List<OverTimeEntity> list)
	{
		listOverTime=list;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
	}
	
	@Override
	public int getCount() {
		if(listOverTime==null){
			;
			return 0;
		}
		return listOverTime.size();
	}

	@Override
	public Object getItem(int position) {
		return listOverTime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.overtime_summary_items, parent,false);
		}
		
		TextView date = (TextView)convertView.findViewById(R.id.textview_overtimesummary_date);
		TextView assignment = (TextView)convertView.findViewById(R.id.textview_overtimesummary_assignment);
		TextView hours = (TextView)convertView.findViewById(R.id.textview_overtimesummary_hours);
		editButton=(Button)convertView.findViewById(R.id.btn_overtimesummary_edit);
		editButton.setTag(position); //To get position onClick of button in listview
				
		objOverTime=listOverTime.get(position);		
//		date.setText(objOverTime.date);
		date.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objOverTime.date));
		assignment.setText(objOverTime.assignment);
		hours.setText(objOverTime.hours);
		
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objOverTime=new OverTimeEntity();
				objOverTime=listOverTime.get(position); //getting object from list by position
				new EditOvertime((CalendarEvents) context).createEditOvertimeLayout(objOverTime);
//				Intent intent=new Intent(context,EditOvertime.class);
//				intent.putExtra("OverTimeEntity", objOverTime);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
		
		return convertView;		
	}

}

package com.mygrouptracker.mygrouptracker.adapter;

import java.util.ArrayList;
import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.activity.EditOvertime;
import com.mygrouptracker.mygrouptracker.activity.EditSickTime;
import com.mygrouptracker.mygrouptracker.activity.KellyDaySummary;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BaseAdapterSickTime extends BaseAdapter{

	SickTimeEntity objSickTime=new SickTimeEntity();
	List<SickTimeEntity> listSickTime;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	
	public BaseAdapterSickTime(Context context, List<SickTimeEntity> listSickTime)
	{
		this.listSickTime=listSickTime;
		inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
	}
	
	@Override
	public int getCount() {
		if(listSickTime==null){
			;
			return 0;
		}
		return listSickTime.size();
	}

	@Override
	public Object getItem(int position) {
		return listSickTime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.sick_time_summary_items, parent,false);
		}
		
		TextView date = (TextView)convertView.findViewById(R.id.textview_sicktimesummary_date);
		TextView notes = (TextView)convertView.findViewById(R.id.textview_sicktimesummary_notes);
		TextView hours = (TextView)convertView.findViewById(R.id.textview_sicktimesummary_hours);
		editButton=(Button)convertView.findViewById(R.id.btn_sicktimesummary_edit);
		editButton.setTag(position); //To get position onClick of button in listview
				
		objSickTime=listSickTime.get(position);		
//		date.setText(objSickTime.date);
		date.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objSickTime.date));
		notes.setText(objSickTime.notes);
		hours.setText(objSickTime.hours);
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objSickTime=new SickTimeEntity();
				objSickTime=listSickTime.get(position); //getting object from list by position
				new EditSickTime((CalendarEvents) context).createEditSickTimeLayout(objSickTime);
//				Intent intent=new Intent(context,EditSickTime.class);
//				intent.putExtra("SickTimeEntity", objSickTime);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
		
		return convertView;		
		
	}
}

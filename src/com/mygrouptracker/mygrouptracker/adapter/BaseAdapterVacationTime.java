package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.activity.EditOvertime;
import com.mygrouptracker.mygrouptracker.activity.EditVacationTime;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
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

public class BaseAdapterVacationTime extends BaseAdapter {

	VacationTimeEntity objVacationTime;
	List<VacationTimeEntity> listVacationTime;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	
	public BaseAdapterVacationTime(Context context, List<VacationTimeEntity> list)
	{
		listVacationTime=list;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
	}
	
	@Override
	public int getCount() {
		if(listVacationTime==null){
			;
			return 0;
		}
		return listVacationTime.size();
	}

	@Override
	public Object getItem(int position) {
		return listVacationTime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.vacation_time_summary_items, parent,false);
		}
		
		TextView startDate = (TextView)convertView.findViewById(R.id.textview_vacationtime_startdate);
		TextView endDate = (TextView)convertView.findViewById(R.id.textview_vacationtime_enddate);
		TextView note = (TextView)convertView.findViewById(R.id.textview_vacationtime_note);
		TextView hour = (TextView)convertView.findViewById(R.id.textview_vacationtime_hour);
		editButton=(Button)convertView.findViewById(R.id.btn_vacationtimesummary_edit);
		editButton.setTag(position);
				
		objVacationTime=listVacationTime.get(position);		
//		startDate.setText(objVacationTime.startDate);
//		endDate.setText(objVacationTime.endDate);
		startDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objVacationTime.startDate));
		endDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objVacationTime.endDate));
		note.setText(objVacationTime.note);
		hour.setText(objVacationTime.hour);
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objVacationTime=new VacationTimeEntity();
				objVacationTime=listVacationTime.get(position); //getting object from list by position
				new EditVacationTime((CalendarEvents) context).createEditVacationTimeLayout(objVacationTime);
//				Intent intent=new Intent(context,EditVacationTime.class);
//				intent.putExtra("VacationTimeEntity", objVacationTime);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
		
		return convertView;		
	}

}

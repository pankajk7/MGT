package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.activity.EditKellyDay;
import com.mygrouptracker.mygrouptracker.activity.KellyDaySummary;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

public class KellyDayBaseAdapter extends BaseAdapter {

	KellyDayEntity objKellyDayEntity=new KellyDayEntity();
	List<KellyDayEntity> listKellyDaySummary;
	Context context;
	LayoutInflater inflater;
	Button editButton;
	
	public KellyDayBaseAdapter(Context context, List<KellyDayEntity> list)
	{
		this.context=context;
		listKellyDaySummary=list;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(listKellyDaySummary==null){
			;
			return 0;
		}
		return listKellyDaySummary.size();
	}

	@Override
	public Object getItem(int position) {
		return listKellyDaySummary.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.kelly_day_summary_items, parent,false);
		}
		
		TextView date = (TextView)convertView.findViewById(R.id.textview_kellydaysummary_date);
		editButton=(Button)convertView.findViewById(R.id.btn_kellydaysummary_edit);
		editButton.setTag(position);
		
		objKellyDayEntity = listKellyDaySummary.get(position);			
//		date.setText(objKellyDayEntity.date);
		date.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objKellyDayEntity.date));
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objKellyDayEntity=new KellyDayEntity();
				objKellyDayEntity=listKellyDaySummary.get(position); //getting object from list by position
				new EditKellyDay((CalendarEvents) context).createEditKellyDayLayout(objKellyDayEntity);
//				Intent intent=new Intent(context,EditKellyDay.class);
//				intent.putExtra("KellyDayEntity", objKellyDayEntity);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
}

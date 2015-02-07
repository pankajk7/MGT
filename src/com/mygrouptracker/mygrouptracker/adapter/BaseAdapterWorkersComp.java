package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditCompTime;
import com.mygrouptracker.mygrouptracker.activity.EditOvertime;
import com.mygrouptracker.mygrouptracker.activity.EditWorkersComp;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
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

public class BaseAdapterWorkersComp extends BaseAdapter{

	WorkersCompEntity objWorkersComp;
	List<WorkersCompEntity> listWorkersComp;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	
	public BaseAdapterWorkersComp(Context context, List<WorkersCompEntity> list)
	{
		listWorkersComp=list;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
	}
	
	
	@Override
	public int getCount() {
		if(listWorkersComp==null){
			;
			return 0;
		}
		return listWorkersComp.size();
	}

	@Override
	public Object getItem(int position) {
		return listWorkersComp.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.workers_comp_summary_items, parent,false);
		}
		
		TextView injuryDate = (TextView)convertView.findViewById(R.id.textview_workerscomp_injurydate);
		TextView returnDate = (TextView)convertView.findViewById(R.id.textview_workerscomp_returndate);
		TextView notes = (TextView)convertView.findViewById(R.id.textview_workerscomp_notes);
		editButton=(Button)convertView.findViewById(R.id.btn_workerscompsummary_edit);
		editButton.setTag(position);
				
		objWorkersComp=listWorkersComp.get(position);		
//		injuryDate.setText(objWorkersComp.injuryDate);
//		returnDate.setText(objWorkersComp.returnDate);
		injuryDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objWorkersComp.injuryDate));
		returnDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objWorkersComp.returnDate));
		notes.setText(objWorkersComp.notes);
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objWorkersComp=new WorkersCompEntity();
				objWorkersComp=listWorkersComp.get(position); //getting object from list by position
				new EditWorkersComp((CalendarEvents) context).createEditWorkersCompLayout(objWorkersComp);
//				Intent intent=new Intent(context,EditWorkersComp.class);
//				intent.putExtra("WorkersCompEntity", objWorkersComp);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
		});
		
		return convertView;		
	}

}

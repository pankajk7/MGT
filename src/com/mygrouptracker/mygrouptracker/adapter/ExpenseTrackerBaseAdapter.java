package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.EditExpenseActivity;
import com.mygrouptracker.mygrouptracker.activity.EditWorkersComp;
import com.mygrouptracker.mygrouptracker.entity.ExpenseTrackerEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ExpenseTrackerBaseAdapter extends BaseAdapter{

	ExpenseTrackerEntity objExpenseTrackerEntity;
	List<ExpenseTrackerEntity> listExpenseTracker;
	Context context;
	LayoutInflater inflater;
	Button editButton;
	
	public ExpenseTrackerBaseAdapter(Context context, List<ExpenseTrackerEntity> list)
	{
	 this.context=context;
	 inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 listExpenseTracker=list;
	}
	
	@Override
	public int getCount() {
		if(listExpenseTracker==null){
			;
			return 0;
		}
		return listExpenseTracker.size();
	}

	@Override
	public Object getItem(int position) {
		return listExpenseTracker.get(position); 
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.expense_tracker_activity_items, parent,false);
		}
		
		TextView dateTextView = (TextView)convertView.findViewById(R.id.textview_expensetracker_date);
		TextView descTextView = (TextView)convertView.findViewById(R.id.textview_expensetracker_description);
		TextView amountTextView = (TextView)convertView.findViewById(R.id.textview_expensetracker_amount);
		editButton=(Button)convertView.findViewById(R.id.btn_expensetracker_edit);
		editButton.setTag(position);
				
		objExpenseTrackerEntity=listExpenseTracker.get(position);		
//		dateTextView.setText(objExpenseTrackerEntity.eventDate);
		dateTextView.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objExpenseTrackerEntity.eventDate));
		descTextView.setText(objExpenseTrackerEntity.desc1);
		amountTextView.setText("$"+objExpenseTrackerEntity.amount);
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objExpenseTrackerEntity=new ExpenseTrackerEntity();
				objExpenseTrackerEntity=listExpenseTracker.get(position); //getting object from list by position
				Intent intent=new Intent(context,EditExpenseActivity.class);
				intent.putExtra("ExpenseTrackerEntity", objExpenseTrackerEntity);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		
		return convertView;		
	}

}

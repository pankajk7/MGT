package com.mygrouptracker.mygrouptracker.activity;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterOverTimeSummary;
import com.mygrouptracker.mygrouptracker.adapter.ExpenseTrackerBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.MessageBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.ExpenseTrackerEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class ExpenseTracker extends BaseClass {

	ExpenseTrackerEntity objExpenseTrackerEntity=new ExpenseTrackerEntity();
	List<ExpenseTrackerEntity> listExpenseTracker;
	ListView expenseListView;
	Button addButton;
	Button emailButton;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	public void loadSummary()
	{
		 try {
				new BackgroundNetwork(ExpenseTracker.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(ExpenseTracker.this);
						listExpenseTracker=dataEngine.getExpenseTracker();
						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);	
						ExpenseTrackerBaseAdapter expenseAdapter=new ExpenseTrackerBaseAdapter(getApplicationContext(),listExpenseTracker); 	
						
						expenseListView.setAdapter(expenseAdapter);	
						expenseAdapter.notifyDataSetChanged();
					};
				}.execute();
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}		
	}
	
	@Override
	protected void onResume() {
	
		loadSummary();
		super.onResume();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.expense_tracker_activity);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.expense_tracker_activity, null));
		addButton=(Button)findViewById(R.id.btn_expensetracker_add);
		backButton=(Button)findViewById(R.id.btn_baselayout_backbutton);
		expenseListView=(ListView) findViewById(R.id.list_expensetracker);
		emailButton=(Button)findViewById(R.id.btn_expensetracker_email);

		 		loadSummary();		
		
		
		
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ExpenseTracker.this, AddExpenseTracker.class);
				startActivityForResult(intent, 100);
			}
		});  
		
		emailButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String listString="";
				int i=1;
				for (ExpenseTrackerEntity obj : listExpenseTracker)
				{
					listString+="\n"+"Event "+(i++)+"\n";
					listString+="Date :- "+obj.eventDate+"\n";
					listString+="Description :-"+obj.desc1+"\n";
					listString+="Amount :-"+obj.amount+"\n";
				    listString +="-----------------"+ "\n";
				}
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] {""+LoginActivity.userNameString+""});
				intent.putExtra(Intent.EXTRA_SUBJECT, "Expense Tracker Summary");
				intent.putExtra(Intent.EXTRA_TEXT, listString);
				startActivity(Intent.createChooser(intent, ""));
				
			}
		});
	}	
	
	
}



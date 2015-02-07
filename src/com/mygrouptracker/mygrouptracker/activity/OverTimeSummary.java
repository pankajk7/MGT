package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterOverTimeSummary;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterSickTime;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class OverTimeSummary extends BaseClass {

	OverTimeEntity objOverTime=new OverTimeEntity();
	ListView listOverTime;
	Button addOverTimeButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.over_time_summary);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.over_time_summary, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.over_time_summary, null));
		
		BaseAdapterOverTimeSummary overtimeAdapter=new BaseAdapterOverTimeSummary(getApplicationContext(), objOverTime.getOverTimeSummary()); 	
		listOverTime=(ListView) findViewById(R.id.list_overtimesummary);
		listOverTime.setAdapter(overtimeAdapter);
		
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addOverTimeButton=(Button)findViewById(R.id.btn_overtimesummary_addovertime);
		addOverTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(OverTimeSummary.this, AddOvertime.class);
				startActivity(intent);
			}
		});   
	}

}

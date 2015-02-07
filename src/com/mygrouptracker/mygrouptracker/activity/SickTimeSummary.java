package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterSickTime;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SickTimeSummary extends BaseClass {

	SickTimeEntity objSickTimeEntity=new SickTimeEntity();
	ListView listSickTime;
	Button addSickTimeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.sick_time_summary);
	
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.sick_time_summary, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.sick_time_summary, null));
		
		BaseAdapterSickTime sicktimeAdapter=new BaseAdapterSickTime(this, objSickTimeEntity.getSickTimeSummary()); 	
		listSickTime=(ListView) findViewById(R.id.list_sicktimesummaryview);
		listSickTime.setAdapter(sicktimeAdapter);
		
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addSickTimeButton=(Button)findViewById(R.id.btn_sicktimesummary_addsicktime);
		addSickTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SickTimeSummary.this, AddSickTime.class);
				startActivity(intent);
			}
		});  
	}


}

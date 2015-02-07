package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterCompTime;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;

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

public class CompTimeSummary extends BaseClass {

	CompTimeEntity objCompTime=new CompTimeEntity();
	ListView listCompTime;
	Button addCompTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.comp_time_summary);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.comp_time_summary, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.comp_time_summary, null));
			
		BaseAdapterCompTime comptimeAdapter = new BaseAdapterCompTime(this,objCompTime.getCompTimeSummary());
        
		listCompTime=(ListView)findViewById(R.id.list_comptimesummary);
		listCompTime.setAdapter(comptimeAdapter);
        
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addCompTime=(Button)findViewById(R.id.btn_comptimesummary_addcomptime);
		addCompTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(CompTimeSummary.this, AddCompTime.class);
				startActivity(intent);
			}
		});    
	}

}

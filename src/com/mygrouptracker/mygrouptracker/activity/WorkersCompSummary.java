package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterOverTimeSummary;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterWorkersComp;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;

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

public class WorkersCompSummary extends BaseClass {

	WorkersCompEntity objWorkersComp=new WorkersCompEntity();
	ListView listWorkersComp;
	Button addWorkersCompButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.workers_comp_summary);		

		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.workers_comp_summary, null));
		
		BaseAdapterWorkersComp workerscompAdapter=new BaseAdapterWorkersComp(this, objWorkersComp.getOverTimeSummary()); 	
		listWorkersComp=(ListView) findViewById(R.id.list_workerscompsummary);
		listWorkersComp.setAdapter(workerscompAdapter);
		
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addWorkersCompButton=(Button)findViewById(R.id.btn_workerscompsummary_addworkercomp);
		addWorkersCompButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(WorkersCompSummary.this, AddWorkersComp.class);
				startActivity(intent);
			}
		});  
	}

	
}

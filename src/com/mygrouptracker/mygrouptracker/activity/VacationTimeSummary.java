package com.mygrouptracker.mygrouptracker.activity;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterVacationTime;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterWorkersComp;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class VacationTimeSummary extends BaseClass {

	VacationTimeEntity objVacationTime=new VacationTimeEntity();
	ListView listVacationTime;
	Button addVacationTimeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.vacation_time_summary);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.vacation_time_summary, null));
		
		BaseAdapterVacationTime vacationtimeAdapter=new BaseAdapterVacationTime(this, objVacationTime.getVacationTimeSummary()); 	
		listVacationTime=(ListView) findViewById(R.id.list_vacationtimesummary);
		listVacationTime.setAdapter(vacationtimeAdapter);
		
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addVacationTimeButton=(Button)findViewById(R.id.btn_vacationtimesummary_addvacationtime);
		addVacationTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(VacationTimeSummary.this, AddVacationTime.class);
				startActivity(intent);
				
			}
		});
		
	}

	
}

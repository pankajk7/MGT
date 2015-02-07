package com.mygrouptracker.mygrouptracker.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.KellyDayBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class KellyDaySummary extends BaseClass {

	
	KellyDayEntity objKellyDayEntity;	
	List<KellyDayEntity> kellyDayList;
	Button addKellyDayButton;
	Button editButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.appointment_summary);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.kelly_day_summary, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.kelly_day_summary, null));
        
		//TODO:
        DataEngine dataEngine = new DataEngine(this);
        kellyDayList =dataEngine.getKellyDay();
        
        KellyDayBaseAdapter kellydayAdapter = new KellyDayBaseAdapter(getApplicationContext(),kellyDayList);
        
        ListView listkellydaysummary=(ListView)findViewById(R.id.list_kellyday_summary);
        
        listkellydaysummary.setAdapter(kellydayAdapter);
        
        kellydayAdapter.notifyDataSetChanged();
        
        
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addKellyDayButton=(Button)findViewById(R.id.btn_kellydaysummary_addkellyday);
		addKellyDayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(KellyDaySummary.this, AddKellyDay.class);
				startActivity(intent);
			}
		});    
		
		
	}
	
}

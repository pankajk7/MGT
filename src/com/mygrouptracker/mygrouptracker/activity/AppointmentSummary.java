package com.mygrouptracker.mygrouptracker.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.AppointmentSummaryBaseAdapter;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;

import android.media.JetPlayer.OnJetEventListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AppointmentSummary extends BaseTabTitleActivity {

	
	
	AppointmentSummaryEntity objAppointmentSummaryEntity;
	
//	Button backButton;
	Button addAppointmentButton;
	List<AppointmentSummaryEntity> apptSummaryList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.appointment_summary);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.layout_basecontainer);
		framelayout.addView(getLayoutInflater().inflate(R.layout.appointment_summary, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.appointment_summary, null));
		
		objAppointmentSummaryEntity=new AppointmentSummaryEntity();
		apptSummaryList=objAppointmentSummaryEntity.getapptSummary();
        AppointmentSummaryBaseAdapter appointmentAdapter = new AppointmentSummaryBaseAdapter(getApplicationContext(),apptSummaryList);
        
        ListView listapptsummary=(ListView)findViewById(R.id.list_appointment_summary);
        listapptsummary.setAdapter(appointmentAdapter);
        
//		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addAppointmentButton=(Button)findViewById(R.id.btn_appointmentsummary_addappt);
		addAppointmentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AppointmentSummary.this, AddAppointment.class);
				startActivity(intent);
			}
		});    
		
		
		
	}	
	
	
}





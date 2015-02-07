package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.ListAllEventsBaseAdapter;
import com.mygrouptracker.mygrouptracker.entity.AllEvents;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class ListAllevents extends ListActivity {

	AllEvents objAllEvents;
	List<VacationTimeEntity> listVacationTime;
	
	ListAllEventsBaseAdapter listAdapter;
	
	@SuppressWarnings("unchecked")
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.list_all_events);
		
//		ListView sickTimeList = (ListView) findViewById(R.id.list_listallevents_vacationlist);
//		
//		Intent intent=new Intent();
//		listVacationTime=(List<VacationTimeEntity>) getIntent().getSerializableExtra("VacationTime");
		try{
//		Bundle bundleObject = getIntent().getExtras();

//		objAllEvents = (AllEvents) bundleObject.getParcelable("DateEvents");
////	    String s=classObject.get(0).Classa;
	    
		objAllEvents=(AllEvents)getIntent().getSerializableExtra("DateEvents");
		
		listAdapter=new ListAllEventsBaseAdapter(ListAllevents.this);
		listAdapter.addAppointment(objAllEvents.appointmentArrayList);
		listAdapter.addCompTime(objAllEvents.compTimeArrayList);
		listAdapter.addKellyDay(objAllEvents.kellyDayArrayList);
		listAdapter.addOverTime(objAllEvents.overTimeArrayList);
		listAdapter.addSickTime(objAllEvents.sickTimeArrayList);
		listAdapter.addTradeTime(objAllEvents.tradeTimeArrayList);
		listAdapter.addVacationTime(objAllEvents.vacationTimeArrayList);
		listAdapter.addWorkersCompTime(objAllEvents.workersCompArrayList);
		listAdapter.addPayday(objAllEvents.paydayArrayList);
		listAdapter.addHoliday(objAllEvents.holidayArrayList);
		
		setListAdapter(listAdapter);
		}catch(Exception e){
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
			e.printStackTrace();
		}
		
//		listAdapter=new ListAllEventsBaseAdapter(ListAllevents.this, objAllEvents.vacationTimeArrayList);
//		
//		
//
//		sickTimeList.setAdapter(adapter);
//
//		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		
	}

}

package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.AllEvents;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class SyncingActivity extends BaseTabTitleActivity {

	FrameLayout framelayout;
	String stateBackButtonString;
	Button syncButton;
	EditText startDateEditText;
	EditText endDateEditText;
	public AllEvents objAllEvents;
	ArrayList<AppointmentSummaryEntity> apptSummaryArrayList;
	ArrayList<CompTimeEntity> compTimeArrayList;
	ArrayList<KellyDayEntity> kellyDayList;
	ArrayList<OverTimeEntity> overTimeEntityArrayList;
	ArrayList<SickTimeEntity> sickTimeEntityArrayList;
	ArrayList<TradeTimeEntity> tradeTimeEntityArrayList;
	ArrayList<VacationTimeEntity> vacationEntityArrayList;
	ArrayList<WorkersCompEntity> workersCompArrayList;
	static int numOfDays;
	static String startDate;
	String dateString;
	long calendarID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.syncing_activity);
		framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.syncing_activity, null));
		stateBackButtonString = "finish";
		
		dateString=startDate;
		for(int i=1;i<numOfDays;i++){
			dateString=MgtDateFormat.incrementDate(dateString);
		}
		
		startDateEditText=(EditText)findViewById(R.id.edittext_syncing_startdate);
		endDateEditText=(EditText)findViewById(R.id.edittext_syncing_enddate);
		syncButton=(Button)findViewById(R.id.btn_syncingactivity_sync);
		
		startDateEditText.setText(startDate);
		endDateEditText.setText(dateString);
		
		syncButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getCalendarId();
				getAllEvents();
				
			}
		});
		
	}

	public void getAllEvents()
	{
		try {
			new BackgroundNetwork(SyncingActivity.this) {
				protected Void doInBackground(Void[] params) {
					
//					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//					nameValuePairs.add(new BasicNameValuePair("StartDate", "2014-04-01"));
//					nameValuePairs.add(new BasicNameValuePair("Days", "15"));
					
					DataEngine dataEngine = new DataEngine(SyncingActivity.this);
					
					
					objAllEvents=dataEngine.getAllEvents("DateOfEvents?StartDate="+startDate+"&Days="+String.valueOf(numOfDays));
//					adapter.setAllEvents(objAllEvents);
					apptSummaryArrayList=objAllEvents.appointmentArrayList;
					compTimeArrayList=objAllEvents.compTimeArrayList;
					kellyDayList=objAllEvents.kellyDayArrayList;
					overTimeEntityArrayList=objAllEvents.overTimeArrayList;
					sickTimeEntityArrayList=objAllEvents.sickTimeArrayList;
					tradeTimeEntityArrayList=objAllEvents.tradeTimeArrayList;
					vacationEntityArrayList=objAllEvents.vacationTimeArrayList;
					workersCompArrayList=objAllEvents.workersCompArrayList;
					
										
					System.out.println(objAllEvents.toString());
					Log.e(">>AlleventObj>>", objAllEvents.toString());
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					if(apptSummaryArrayList!=null){
						for(AppointmentSummaryEntity obj:apptSummaryArrayList){							
							syncEventsToCalendar("Appointment",obj.description,obj.eventDate);
						}
					}
					if(compTimeArrayList!=null){
						for(CompTimeEntity obj:compTimeArrayList){							
							syncEventsToCalendar("Comp Time",obj.desc,obj.eventDate);
						}
					}
					if(kellyDayList!=null){
						for(KellyDayEntity obj:kellyDayList){							
							syncEventsToCalendar("Kelly Day",obj.isShare,obj.date);
						}
					}
					if(overTimeEntityArrayList!=null){
						for(OverTimeEntity obj:overTimeEntityArrayList){							
							syncEventsToCalendar("Over Time",obj.desc1,obj.date);
						}
					}
					if(sickTimeEntityArrayList!=null){
						for(SickTimeEntity obj:sickTimeEntityArrayList){							
							syncEventsToCalendar("Sick Time",obj.notes,obj.date);
						}
					}
					if(tradeTimeEntityArrayList!=null){
						for(TradeTimeEntity obj:tradeTimeEntityArrayList){							
							syncEventsToCalendar("Trade Time",obj.note,obj.date);
						}
					}
					if(vacationEntityArrayList!=null){
						for(VacationTimeEntity obj:vacationEntityArrayList){							
							syncEventsToCalendar("Vacation Time",obj.note,obj.startDate);
						}
					}
					if(workersCompArrayList!=null){
						for(WorkersCompEntity obj:workersCompArrayList){							
							syncEventsToCalendar("Workers Comp",obj.notes,obj.injuryDate);
						}
					}
				};
			}.execute();
		}
		 catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
	}

	@SuppressLint("NewApi")
	void getCalendarId()
	{
		try
		{
			
			String[] projection = 
			      new String[]{
			            Calendars._ID, 
			            Calendars.NAME, 
			            Calendars.ACCOUNT_NAME, 
			            Calendars.ACCOUNT_TYPE};
				Cursor calCursor = 
			      getContentResolver().
			            query(Calendars.CONTENT_URI, 
			                  projection, 
			                  Calendars.VISIBLE + " = 1", 
			                  null, 
			                  Calendars._ID + " ASC");
				String accountType="";
				if (calCursor.moveToFirst()) {
					do {
						long id = calCursor.getLong(0);
						System.out.println("ID: "+String.valueOf(id)+"\n Name: "+accountType+"\n cursor: "+calCursor.getString(2)+"\n "+calCursor.getString(3));						
						accountType = calCursor.getString(3);
						if(accountType.equals("com.google"))
						{
							calendarID=calCursor.getLong(0);
						}
					} while (calCursor.moveToNext());
				}
				System.out.println("Names: "+accountType);
			}
		catch(Exception e){
			System.out.println(">>>Exception>>>"+e.toString()+">>>"+e.getMessage());
		}
 	}
	
	@SuppressLint("NewApi")
	void syncEventsToCalendar(String title, String description, String date){
		try{
//			Uri l_calendars;
//			if (Build.VERSION.SDK_INT >= 8) {
//				l_calendars = Uri.parse("content://com.android.calendar/calendars");
//			} else {
//				l_calendars = Uri.parse("content://calendar/calendars");
//			}
			String dateString;
			dateString=MgtDateFormat.dateFormatYYMMDDCalendar(date);
			int year=Integer.valueOf(MgtDateFormat.getYear(dateString));
			int month=Integer.valueOf(MgtDateFormat.getMonth(dateString));
			int day=Integer.valueOf(MgtDateFormat.getDate(dateString));
			int hourOfDay=Integer.valueOf(MgtDateFormat.getHour(date));
			int minute=Integer.valueOf(MgtDateFormat.getMinutes(date));
			long calID = calendarID;
			long startMillis = 0; 
			long endMillis = 0;     
			Calendar beginTime= Calendar.getInstance();
//			beginTime.set(2014, 4, 1, 7, 30); //4 means May month not april
			beginTime.set(year, month-1, day, hourOfDay, minute);
			startMillis = beginTime.getTimeInMillis();
			Calendar endTime = Calendar.getInstance();
			endTime.set(year, month-1, day, hourOfDay+1, minute);
			endMillis = endTime.getTimeInMillis();
		//
			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			values.put(Events.DTSTART, startMillis);
			values.put(Events.DTEND, endMillis);
			values.put(Events.TITLE, title);
			values.put(Events.DESCRIPTION, description);
			values.put(Events.CALENDAR_ID, calendarID);
//			values.put(Events.EVENT_TIMEZONE, "America/Los_Angeles");
			values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
			Uri uricall=cr.insert(Events.CONTENT_URI, values);
//			AsyncQueryHandler handler = new MyHandler(getContentResolver());
//			handler.startInsert(0, null, Events.CONTENT_URI, values);
		}
		catch(Exception e){
			System.out.println(">>>Exception>>>"+e.toString()+">>>"+e.getMessage());
		}
	}
	
	public class MyHandler extends AsyncQueryHandler {
	    public MyHandler(ContentResolver cr) {
			        super(cr);
		 }
	}
}

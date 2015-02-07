package com.mygrouptracker.mygrouptracker.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.MgtDatePickerDialog;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AddSickTime {

	Context context;
	EditText dateEditText;
	EditText hourEditText;
	EditText notesEditText;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean shareBoolean=true;
	String eventNameString="sicktime";
	String currentTimeString;
	String response;
	List<NameValuePair> nameValuePairs;
	
	public AddSickTime(Context context)
	{
		this.context=context;
	}
	
	public void createAddSickTimeLayout()
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
				.findViewById(R.id.layout_basecontainer);
				LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
				.getLayoutInflater().inflate(R.layout.add_sick_time,null);
				baseLayout.removeAllViews();
				baseLayout.addView(inflateLayout);
				
				dateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addsicktime_selectdate);
				hourEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addsicktime_hours);
				notesEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addsicktime_notes);
				shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addsicktime_share_yes);
				shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addsicktime_share_no);
				
				currentTimeString=MgtDateFormat.getCurrentDateTime();
				dateEditText.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString)));
				
				try{
					SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
					Date minDate=dt.parse("01/01/1970");
					Date maxDate=dt.parse("01/01/2025");
					new MgtDatePickerDialog(context,dateEditText,minDate,maxDate);
					}catch(Exception er){}
				
				if(shareBoolean==true){
					shareYesButton.setBackgroundResource(R.drawable.btn_gray);
					shareNoButton.setBackgroundResource(R.drawable.btn_no);					
				}
				else{
					shareYesButton.setBackgroundResource(R.drawable.btn_yes);
					shareNoButton.setBackgroundResource(R.drawable.btn_gray);
				}
				
				shareYesButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
							shareBoolean=true;
//							shareNoButton.setEnabled(true);
//							shareYesButton.setEnabled(false);
							shareYesButton.setBackgroundResource(R.drawable.btn_gray);
							shareNoButton.setBackgroundResource(R.drawable.btn_no);
					}
				});
				
				shareNoButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
							shareBoolean=false;				
//							shareNoButton.setEnabled(false);
//							shareYesButton.setEnabled(true);
							shareYesButton.setBackgroundResource(R.drawable.btn_yes);
							shareNoButton.setBackgroundResource(R.drawable.btn_gray);
					}
				});
				
				
				  
				  
				addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_addsicktime_calendar);
				addCalendarButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(new MgtValidation(context).CheckSickTime(hourEditText, notesEditText)==false)
					       {					        
					        return;
					       }
						
						try {
							new BackgroundNetwork(context) {
								protected Void doInBackground(Void[] params) {
									nameValuePairs = new ArrayList<NameValuePair>();
									String dateTime=dateEditText.getText().toString();
									  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
									  nameValuePairs.add(new BasicNameValuePair("Date", dateTime));
									  nameValuePairs.add(new BasicNameValuePair("Hour", hourEditText.getText().toString()));
									  nameValuePairs.add(new BasicNameValuePair("Note", notesEditText.getText().toString()));
									  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
									  
									DataEngine dataEngine = new DataEngine(context);
									response=dataEngine.addRecord(eventNameString,nameValuePairs);
									return null;
								};

								protected void onPostExecute(Void result) {
									super.onPostExecute(result);
									AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
									dlgAlert.setTitle("Message");
									if(new StatusInfo().getStatusInfo(response).equals("Success"))
									{
										dlgAlert.setMessage("Sick Time added successfully");
									}
									else
									{
									dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
									}
									dlgAlert.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
													((CalendarEvents)context).createLayouts("SickTimeSummary");
												}
											});
									dlgAlert.setCancelable(true);
									dlgAlert.create().show();
								};
							}.execute();
					}
						 catch (Exception e) {
								System.out.println(">>>Exception " + e + "  >>> Message : "
										+ e.getMessage());
							}
					}
				});
	}

}

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

import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AddCompTime {

	Context context;
	EditText dateEditText;
	EditText hourEditText;
	EditText noteEditText;
	EditText descriptionEditText;
	Button typeEarnedButton;
	Button typeTakenButton;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean shareBoolean=true;
	String typeString="0";
	String eventNameString="comptime";
	String currentTimeString;
	String response;
	List<NameValuePair> nameValuePairs;
	int comptimeEarned=0;
	int comptimeTaken=0;
	public AddCompTime(Context context)
	{
		this.context=context;
	}

	public AddCompTime(Context context,int earned,int taken)
	{
		this.context=context;
		this.comptimeEarned=earned;
		this.comptimeTaken=taken;
	}
	
	public void createAddCompTimeLayout()
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
				.findViewById(R.id.layout_basecontainer);
		baseLayout.removeAllViews();
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
				.getLayoutInflater().inflate(R.layout.add_comp_time,
						null);
				
				baseLayout.addView(inflateLayout);
				
				dateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addcomptime_date);
				hourEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addcomptime_hours);
				noteEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addcomptime_note);
				descriptionEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addcomptime_desc);
				typeEarnedButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addcomptime_type_earned);
				typeTakenButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addcomptime_type_taken);
				shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addcomptime_share_yes);
				shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addcomptime_share_no);
				
				currentTimeString=MgtDateFormat.getCurrentDateTime();
				dateEditText.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString)));
				
				try{
					SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
					Date minDate=dt.parse("01/01/1970");
					Date maxDate=dt.parse("01/01/2099");
					//Date existingDate=dt.parse(string)
					new MgtDatePickerDialog(context,dateEditText,minDate,maxDate);
					}catch(Exception er){}

				if(typeString.equals("0")){
					typeEarnedButton.setBackgroundResource(R.drawable.btn_gray);
					typeTakenButton.setBackgroundResource(R.drawable.btn_no);
				}
				else{
					typeEarnedButton.setBackgroundResource(R.drawable.btn_yes);
					typeTakenButton.setBackgroundResource(R.drawable.btn_gray);
				}
				
					
				typeEarnedButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
							typeString="0";	
//							typeEarnedButton.setEnabled(false);							
//							typeTakenButton.setEnabled(true);
							typeEarnedButton.setBackgroundResource(R.drawable.btn_gray);
							typeTakenButton.setBackgroundResource(R.drawable.btn_no);
					}
				});
				
				typeTakenButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
							typeString="1";		
//							typeTakenButton.setEnabled(false);
//							typeEarnedButton.setEnabled(true);
							typeEarnedButton.setBackgroundResource(R.drawable.btn_yes);
							typeTakenButton.setBackgroundResource(R.drawable.btn_gray);
					}
				});
				
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
							
//							shareYesButton.setEnabled(false);
//							shareNoButton.setEnabled(true);
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
							shareNoButton.setBackgroundResource(R.drawable.btn_gray);
							shareYesButton.setBackgroundResource(R.drawable.btn_yes);
					}
				});				
				  
				  
				addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_addcomptime_calendar);
				addCalendarButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						
						
						if(new MgtValidation(context).CheckCompTime(hourEditText, noteEditText, descriptionEditText)==false)
					       {					        
					        return;
					       }
						
						if(typeString.equals("1"))
						{
							comptimeTaken=Integer.valueOf(hourEditText.getText().toString());
							int balance=comptimeEarned-comptimeTaken;
							if(balance<0)
							{
								AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
								dlgAlert.setTitle("Negetive Balance");
								dlgAlert.setMessage("Comp Time must be earned before it can be taken");
								dlgAlert.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
											}
										});
								dlgAlert.setCancelable(true);
								dlgAlert.create().show();
								return;
								
							}
						}
						
						try {
							new BackgroundNetwork(context) {
								protected Void doInBackground(Void[] params) {
									  nameValuePairs = new ArrayList<NameValuePair>();
									  nameValuePairs.add(new BasicNameValuePair("Hour", hourEditText.getText().toString()));
									  nameValuePairs.add(new BasicNameValuePair("EventType", typeString));				  
									  nameValuePairs.add(new BasicNameValuePair("Description", descriptionEditText.getText().toString()));
									  String dateTime=dateEditText.getText().toString();
									  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
									  nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
									  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
//									  nameValuePairs.add(new BasicNameValuePair("EventType", typeString));
//									  nameValuePairs.add(new BasicNameValuePair("EntryDate", dateEditText.getText().toString()));
									  
									DataEngine dataEngine = new DataEngine(context);
//									dataEngine.postKellyDayTest();	
									response=dataEngine.addRecord(eventNameString,nameValuePairs);
									return null;
								};

								protected void onPostExecute(Void result) {
									super.onPostExecute(result);
									AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
									dlgAlert.setTitle("Message");
									if(new StatusInfo().getStatusInfo(response).equals("Success"))
									{
										dlgAlert.setMessage("Comp Time added successfully");
									}
									else
									{
									dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
									}
									dlgAlert.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
													((CalendarEvents)context).createLayouts("CompTimeSummary");
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

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
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.CommonDeleteFunction;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.MgtDatePickerDialog;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class EditCompTime{

	CompTimeEntity objCompTime;
	EditText eventDateEditText;
	EditText noteEditText;
	Button deleteButton;
	EditText hoursEditText;
	EditText descEditText;
	Button typeEarnedButton;
	Button typeTakenButton;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean shareBoolean;
	String typeString="0";
	String idString;
	String eventNameString="comptime";
	String currentTimeString;
	String response;
	Context context;
	List<NameValuePair> nameValuePairs;
	int comptimeEarned=0;
	int comptimeTaken=0;
	CommonDeleteFunction commonDeleteFunction;
	List<CompTimeEntity> listCompTime;
	public EditCompTime(Context context)
	{
		this.context=context;
	}
	public EditCompTime(Context context,List<CompTimeEntity> listCompTime)
	{
		this.context=context;
		this.listCompTime=listCompTime;
	}
	
	public void createEditCompTimeLayout(CompTimeEntity objCompTimeEntity) {
	try
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
		.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
		.getLayoutInflater().inflate(R.layout.edit_comp_time,
				null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		CalendarEvents.stateBackButtonString="CompTimeSummary";
		
		eventDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editcomptime_date);
//		noteEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editcomptime_note);
		descEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editcomptime_note);
		hoursEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editcomptime_hours);
		typeEarnedButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editcomptime_type_earned);
		typeTakenButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editcomptime_type_taken);
		shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editcomptime_share_yes);
		deleteButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editcomptime_delete);
		shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editcomptime_share_no);
		comptimeEarned=comptimeTaken=0;
		for (CompTimeEntity objCompTimeEntity1 : listCompTime) {
			
			if(objCompTimeEntity1.eventType.equals("0"))
			{
				comptimeEarned+=Integer
				
				.valueOf(objCompTimeEntity1.hours);
			}
			else{
					comptimeTaken+=Integer
				
				.valueOf(objCompTimeEntity1.hours);
			}
		}
		
		commonDeleteFunction=new CommonDeleteFunction();
			idString=objCompTimeEntity.id;
			eventDateEditText.setText(objCompTimeEntity.eventDate);
//			noteEditText.setText(objCompTimeEntity.note);
			hoursEditText.setText(objCompTimeEntity.hours);
			descEditText.setText(objCompTimeEntity.desc);	
			if(objCompTimeEntity.eventType.equals("0")){
				typeEarnedButton.setBackgroundResource(R.drawable.btn_gray);
				typeTakenButton.setBackgroundResource(R.drawable.btn_no);
				typeString="0";
				comptimeEarned-=Integer.valueOf(objCompTimeEntity.hours);
			}
			else{
				typeEarnedButton.setBackgroundResource(R.drawable.btn_yes);
				typeTakenButton.setBackgroundResource(R.drawable.btn_gray);
				typeString="1";
				comptimeTaken-=Integer.valueOf(objCompTimeEntity.hours); 
			}
		    
		/*	if(listCompTime.size()==1)
			{
				typeEarnedButton.setBackgroundResource(R.drawable.btn_gray);
				typeTakenButton.setBackgroundResource(R.drawable.btn_no);
				typeString="0";
				comptimeEarned-=Integer.valueOf(objCompTimeEntity.hours);
				
			}*/
		
			if(objCompTimeEntity.isShare.equals("true")){
				shareYesButton.setBackgroundResource(R.drawable.btn_gray);
				shareNoButton.setBackgroundResource(R.drawable.btn_no);
			}
			else{
				shareNoButton.setBackgroundResource(R.drawable.btn_gray);
				shareYesButton.setBackgroundResource(R.drawable.btn_yes);
			}
				
		
			currentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTimeEntity.eventDate);
			eventDateEditText.setText(currentTimeString);
		
		
			try{
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				Date minDate=dt.parse("01/01/1970");
				Date maxDate=dt.parse("01/01/2099");
			//	String date=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTimeEntity.eventDate);
				//Date existingDate=dt.parse(date);
				new MgtDatePickerDialog(context,eventDateEditText,minDate,maxDate);
				}catch(Exception er){}
			
	}catch(Exception e){
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
	}
		
		
	
	
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				commonDeleteFunction.delete(context, eventNameString, idString, "Comp Time Deleted successfully", "CompTimeSummary");
				
				
			}
		});
		typeEarnedButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					typeString="0";	
//					typeEarnedButton.setEnabled(false);							
//					typeTakenButton.setEnabled(true);
					typeEarnedButton.setBackgroundResource(R.drawable.btn_gray);
					typeTakenButton.setBackgroundResource(R.drawable.btn_no);
			}
		});
		
		typeTakenButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					typeString="1";		
//					typeTakenButton.setEnabled(false);
//					typeEarnedButton.setEnabled(true);
					typeEarnedButton.setBackgroundResource(R.drawable.btn_yes);
					typeTakenButton.setBackgroundResource(R.drawable.btn_gray);
			}
		});
		
		shareYesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					shareBoolean=true;
					
//					shareYesButton.setEnabled(false);
//					shareNoButton.setEnabled(true);
					shareYesButton.setBackgroundResource(R.drawable.btn_gray);
					shareNoButton.setBackgroundResource(R.drawable.btn_no);
			}
		});
		
		shareNoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					shareBoolean=false;	
//					shareNoButton.setEnabled(false);
//					shareYesButton.setEnabled(true);
					shareNoButton.setBackgroundResource(R.drawable.btn_gray);
					shareYesButton.setBackgroundResource(R.drawable.btn_yes);
			}
		});		
		
		
		addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editcomptime_calendar);
		addCalendarButton.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				
				
				if(listCompTime.size()==1 && typeString.equals("1"))
				{
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
					dlgAlert.setTitle("Negetive Balance");
					dlgAlert.setMessage("You must have earned if there is only 1 record");
					dlgAlert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
								}
							});
					dlgAlert.setCancelable(true);
					dlgAlert.create().show();
					return;
				}
				
				if(new MgtValidation(context).CheckCompTime(hoursEditText, noteEditText, descEditText)==false)
			       {					        
			        return;
			       }
				
				if(typeString.equals("1"))
				{
					int xcomptimeTaken=Integer.valueOf(hoursEditText.getText().toString());
					int balance=comptimeEarned-(comptimeTaken+xcomptimeTaken);
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
							  nameValuePairs.add(new BasicNameValuePair("Id", idString));
							  String dateTime=eventDateEditText.getText().toString();
							  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
							  nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
							  nameValuePairs.add(new BasicNameValuePair("Hour", hoursEditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("EventType", typeString));				  
							  nameValuePairs.add(new BasicNameValuePair("Description", descEditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
//							  nameValuePairs.add(new BasicNameValuePair("EventType", typeString));
							  
							DataEngine dataEngine = new DataEngine(context);
//							dataEngine.postKellyDayTest();	
							response=dataEngine.updateRecord(eventNameString,nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
							dlgAlert.setTitle("Message");
							if(new StatusInfo().getStatusInfo(response).equals("Success"))
							{
								dlgAlert.setMessage("CompTime updated successfully");
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

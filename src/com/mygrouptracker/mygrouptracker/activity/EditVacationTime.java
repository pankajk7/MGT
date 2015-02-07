package com.mygrouptracker.mygrouptracker.activity;

import java.text.ParseException;
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
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.CommonDeleteFunction;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.MgtDatePickerDialog;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

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

public class EditVacationTime {

	VacationTimeEntity objVacationTimeEntity;
	EditText startDateEditText;
	EditText endDateEditText;
	EditText noteEditText;
	EditText hourEditText;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean isShareBoolean;
	String eventNameString="vacationtime";
	String currentTimeString;
	String response;
	String idString;
	Button deleteButton;
	List<NameValuePair> nameValuePairs;
	
Context context;
	
	public EditVacationTime(Context context)
	{
		this.context=context;
	}
	
	public void createEditVacationTimeLayout(VacationTimeEntity objVacationTimeEntity) {
	try
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
		.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
		.getLayoutInflater().inflate(R.layout.edit_vacation_time,
				null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		CalendarEvents.stateBackButtonString="VacationTimeSummary";
		idString=objVacationTimeEntity.id;
		
		startDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editvacationtime_start);
		noteEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editvacationtime_note);
		endDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editvacationtime_end);
		hourEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editvacationtime_hours);
		shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editvacationtime_share_yes);
		shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editvacationtime_share_no);
		deleteButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editvactiontime_delete);
		startDateEditText.setText(objVacationTimeEntity.startDate);
		noteEditText.setText(objVacationTimeEntity.note);
		hourEditText.setText(objVacationTimeEntity.hour);
		endDateEditText.setText(objVacationTimeEntity.endDate);
		
		currentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objVacationTimeEntity.endDate);
		String StartcurrentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objVacationTimeEntity.startDate);
		startDateEditText.setText(StartcurrentTimeString);
		endDateEditText.setText(currentTimeString);
		//dateEditText.setText(currentTimeString);
	//	currentTimeString=MgtDateFormat.getCurrentDateTime();
//		startDateEditText.setText(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString));
//		endDateEditText.setText(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString));
		
		if(objVacationTimeEntity.isShare.equals("true")){
			shareYesButton.setBackgroundResource(R.drawable.btn_gray);
			shareNoButton.setBackgroundResource(R.drawable.btn_no);
		}
		else{
			shareYesButton.setBackgroundResource(R.drawable.btn_yes);
			shareNoButton.setBackgroundResource(R.drawable.btn_gray);
		}
		
		try{
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			Date minDate=dt.parse("01/01/1970");
			Date maxDate=dt.parse("01/01/2099");
		//	String date=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTimeEntity.eventDate);
			//Date existingDate=dt.parse(date);
			new MgtDatePickerDialog(context,startDateEditText,minDate,maxDate);
			}catch(Exception er){}
		
			try{
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				Date minDate=dt.parse("01/01/1970");
				Date maxDate=dt.parse("01/01/2099");
			//	String date=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTimeEntity.eventDate);
				//Date existingDate=dt.parse(date);
				new MgtDatePickerDialog(context,endDateEditText,minDate,maxDate);
				}catch(Exception er){}
	}catch(Exception e){
		System.out.println(">>>Exception " + e + "  >>> Message : "
				+ e.getMessage());
	}
		
deleteButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			new CommonDeleteFunction().delete(context, eventNameString, idString, "Vacation Time Deleted successfully", "VacationTimeSummary");
			
			
		}
	});
		shareYesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					isShareBoolean=true;	
//					shareNoButton.setEnabled(true);
//					shareYesButton.setEnabled(false);
					shareYesButton.setBackgroundResource(R.drawable.btn_gray);
					shareNoButton.setBackgroundResource(R.drawable.btn_no);
			}
		});
		
		shareNoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isShareBoolean=false;			
//				shareYesButton.setEnabled(true);
//				shareNoButton.setEnabled(false);
				shareYesButton.setBackgroundResource(R.drawable.btn_yes);
				shareNoButton.setBackgroundResource(R.drawable.btn_gray);
			}
		});				
		
		
		 
		  
		  
		addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editvacationtime_calendar);
		addCalendarButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(new MgtValidation(context).CheckVacationTime(noteEditText, hourEditText)==false)
			       {					        
			        return;
			       }
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String endDateString = endDateEditText.getText().toString();
				String startDateString = startDateEditText.getText().toString();
				Date startDate = null;
				Date endDate = null;
				
				try {
					startDate = sdf.parse(startDateString);
					endDate = sdf.parse(endDateString);
				
				
				if(endDate.compareTo(startDate) < 0){
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
					dlgAlert.setTitle("Message");
					dlgAlert.setMessage("End date cant be greater than start date.");							
					dlgAlert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {											
									
								}
							});
					dlgAlert.setCancelable(true);
					dlgAlert.create().show();
					
				}				
				else{
				try {
					new BackgroundNetwork(context) {
						protected Void doInBackground(Void[] params) {
							
							 nameValuePairs = new ArrayList<NameValuePair>();
							  nameValuePairs.add(new BasicNameValuePair("Id", idString));
							  String dateTime=startDateEditText.getText().toString();
							  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
							  nameValuePairs.add(new BasicNameValuePair("Start", dateTime));
							  String dateTime2=endDateEditText.getText().toString();
							  dateTime2=MgtDateFormat.dateFormatSendingToServer(dateTime2);
							  nameValuePairs.add(new BasicNameValuePair("End", dateTime2));
							  nameValuePairs.add(new BasicNameValuePair("Hour", hourEditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(isShareBoolean)));
							  nameValuePairs.add(new BasicNameValuePair("Note", noteEditText.getText().toString()));
							 
							  
							DataEngine dataEngine = new DataEngine(context);
							response=dataEngine.updateRecord(eventNameString,nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
							dlgAlert.setTitle("Message");
							if(new StatusInfo().getStatusInfo(response).equals("Success"))
							{
								dlgAlert.setMessage("VacationTime updated successfully");
							}
							else
							{
							dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
							}
							dlgAlert.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											((CalendarEvents)context).createLayouts("VacationTimeSummary");
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
				} catch (ParseException e) {
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>>" + e.getMessage());
				}
			}
		});
		
	}
	
}

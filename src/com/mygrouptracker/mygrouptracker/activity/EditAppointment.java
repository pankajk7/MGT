package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.MgtDatePickerDialog;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent.OnFinished;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class EditAppointment {
	
	AppointmentSummaryEntity objAppointmentSummaryEntity;
	EditText eventDateEditText;
	EditText descriptionEditText;
	EditText recurringEditText;
	Button shareYesButton;
	Button shareNoButton;
	Button deleteButton;
	Button addCalendarButton;
	Spinner recurringSpinner;
	Context context;
	String idString;
	String currentTimeString;
	Boolean shareBoolean;
	String eventNameString="appointment";
	String response;
	List<NameValuePair> nameValuePairs;
	
	public EditAppointment(Context context)
	{
		this.context=context;
	}
	
	public void createEditAppointmentLayout(AppointmentSummaryEntity objAppointmentSummaryEntity) {
		
	 try
	 {
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
		.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
		.getLayoutInflater().inflate(R.layout.edit_appointment,
				null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		CalendarEvents.stateBackButtonString="AppointmentSummary";
		idString=objAppointmentSummaryEntity.id;
		
		eventDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editappointment_selectdate);
		descriptionEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editappointment_description);		
		shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editappointment_share_yes);
		shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editappointment_share_no);
		recurringSpinner=(Spinner)((CalendarEvents)context).findViewById(R.id.spinner_editappointment_recurring);
		deleteButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editappointment_delete);
		eventDateEditText.setText(objAppointmentSummaryEntity.eventDate.toString());
		descriptionEditText.setText(objAppointmentSummaryEntity.description.toString());
		
//		currentTimeString=MgtDateFormat.getCurrentDateTime();
		currentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objAppointmentSummaryEntity.eventDate);
		eventDateEditText.setText(currentTimeString);
		
		if(objAppointmentSummaryEntity.isShare.equals("true")){
			shareYesButton.setBackgroundResource(R.drawable.btn_gray);
			shareNoButton.setBackgroundResource(R.drawable.btn_no);
		}
		else{
			shareYesButton.setBackgroundResource(R.drawable.btn_yes);
			shareNoButton.setBackgroundResource(R.drawable.btn_gray);
		}
			
		try{
			new MgtDatePickerDialog(context,eventDateEditText);
			}catch(Exception er){}
		
		List<String> list = new ArrayList<String>();
		list.add("None");
		list.add("Weekly");
		list.add("Biweekly");
		list.add("Monthly");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		recurringSpinner.setAdapter(dataAdapter);
//		int position = dataAdapter.getPosition(objAppointmentSummaryEntity.recurring);
//		int position = dataAdapter.getPosition(item)
		recurringSpinner.setSelection(Integer.valueOf(objAppointmentSummaryEntity.recurring));
	 }
	 catch(Exception e){
		 System.out.println("<<<Exception>>>" + e.toString()
					+ "<<<>>>" + e.getMessage());
	 }
		shareYesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					shareBoolean=true;	
//					shareNoButton.setEnabled(true);
//					shareYesButton.setEnabled(false);
					shareYesButton.setBackgroundResource(R.drawable.btn_gray);
					shareNoButton.setBackgroundResource(R.drawable.btn_no);
			}
		});
		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try{
				new BackgroundNetwork(context) {
					protected Void doInBackground(Void[] params) {
						
						 /* nameValuePairs = new ArrayList<NameValuePair>();
						  nameValuePairs.add(new BasicNameValuePair("Id", idString));
						  nameValuePairs.add(new BasicNameValuePair("Description", descriptionEditText.getText().toString()));
						  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
						  nameValuePairs.add(new BasicNameValuePair("Name", descriptionEditText.getText().toString()));	
						  String dateTime=eventDateEditText.getText().toString();
						  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
						  nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
						  nameValuePairs.add(new BasicNameValuePair("Recurring", String.valueOf(recurringSpinner.getSelectedItemPosition())));*/
						  
						DataEngine dataEngine = new DataEngine(context);
//						dataEngine.postKellyDayTest();	
						response=dataEngine.deleteRecord(eventNameString+"/"+idString);
						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
						dlgAlert.setTitle("Message");	
						if(new StatusInfo().getStatusInfo(response).equals("Success"))
						{
							dlgAlert.setMessage("Appointment Deleted successfully");
						}
						else
						{
						dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
						}
						dlgAlert.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {	
									    ((CalendarEvents)context).createLayouts("AppointmentSummary");
									}
									
								});
//						dlgAlert.setCancelable(false);
						dlgAlert.create().show();
						
					};
				}.execute();
			  }
			 catch (Exception e) 
			    {
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				}
				
			}
	
		});
		shareNoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					shareBoolean=false;
//					shareNoButton.setEnabled(false);
//					shareYesButton.setEnabled(true);
					shareYesButton.setBackgroundResource(R.drawable.btn_yes);
					shareNoButton.setBackgroundResource(R.drawable.btn_gray);
			}
		});
		
		addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editappointment_calendar);
		addCalendarButton.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				
				 if(new MgtValidation(context).CheckApointment(eventDateEditText, descriptionEditText)==false)
			       {					        
			        return;
			       }
				 
				try {
					new BackgroundNetwork(context) {
						protected Void doInBackground(Void[] params) {
							
							  nameValuePairs = new ArrayList<NameValuePair>();
							  nameValuePairs.add(new BasicNameValuePair("Id", idString));
							  nameValuePairs.add(new BasicNameValuePair("Description", descriptionEditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
							  nameValuePairs.add(new BasicNameValuePair("Name", descriptionEditText.getText().toString()));	
							  String dateTime=eventDateEditText.getText().toString();
							  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
							  nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
							  nameValuePairs.add(new BasicNameValuePair("Recurring", String.valueOf(recurringSpinner.getSelectedItemPosition())));
							  
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
								dlgAlert.setMessage("Appointment Updated successfully");
							}
							else
							{
							dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
							}
							dlgAlert.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {	
										    ((CalendarEvents)context).createLayouts("AppointmentSummary");
										}
										
									});
//							dlgAlert.setCancelable(false);
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

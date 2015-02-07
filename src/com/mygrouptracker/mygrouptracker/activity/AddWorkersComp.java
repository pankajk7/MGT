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
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
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
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AddWorkersComp {

	Context context;
	EditText injuryDateEditText;
	EditText caseNumberEditText;
	EditText noteEditText;
	EditText returnDateEditText;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean isShareBoolean=false;
	String eventNameString="workerscomp";
	String currentTimeString;
	String response;
	List<NameValuePair> nameValuePairs;
	
	public AddWorkersComp(Context context)
	{
		this.context=context;
	}
	
	public void createAddWorkersCompLayout()
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
				.findViewById(R.id.layout_basecontainer);
				LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
				.getLayoutInflater().inflate(R.layout.add_workers_group,
						null);
				baseLayout.removeAllViews();
				baseLayout.addView(inflateLayout);
				
				injuryDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addworkerscomp_injurydate);
				caseNumberEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addworkerscomp_casenumber);
				noteEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addworkerscomp_notes);
				returnDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addworkerscomp_returndate);
				shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addworkerscomp_share_yes);
				shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addworkerscomp_share_no);
				
				currentTimeString=MgtDateFormat.getCurrentDateTime();
				
				injuryDateEditText.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString)));
				returnDateEditText.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString)));
				try{
					SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
					Date minDate=dt.parse("01/01/1970");
					Date maxDate=dt.parse("01/01/2025");
					new MgtDatePickerDialog(context,injuryDateEditText,minDate,maxDate);
					}catch(Exception er){}
				
					try{
						SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
						Date minDate=dt.parse("01/01/1970");
						Date maxDate=dt.parse("01/01/2025");
						new MgtDatePickerDialog(context,returnDateEditText,minDate,maxDate);
						}catch(Exception er){}
				
				if(isShareBoolean==true){
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
							isShareBoolean=true;	
//							shareNoButton.setEnabled(true);
//							shareYesButton.setEnabled(false);
							shareYesButton.setBackgroundResource(R.drawable.btn_gray);
							shareNoButton.setBackgroundResource(R.drawable.btn_no);
					}
				});
				
				shareNoButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						isShareBoolean=false;				
//						shareNoButton.setEnabled(false);
//						shareYesButton.setEnabled(true);
						shareYesButton.setBackgroundResource(R.drawable.btn_yes);
						shareNoButton.setBackgroundResource(R.drawable.btn_gray);
					}
				});				
				
				
				 
				  
				  
				addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_addworkerscomp_calendar);
				addCalendarButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(new MgtValidation(context).CheckWorkersComp(caseNumberEditText, noteEditText)==false)
					       {					        
					        return;
					       }
						
						
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						String injuryDateString = injuryDateEditText.getText().toString();
						String returnDateString = returnDateEditText.getText().toString();
						Date injuryDate = null;
						Date returnDate = null;
						
						try {
							injuryDate = sdf.parse(injuryDateString);
							returnDate = sdf.parse(returnDateString);
						
						if(returnDate.compareTo(injuryDate) < 0){
							AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
							dlgAlert.setTitle("Message");
							dlgAlert.setMessage("Return date cant be greater than Injury date.");							
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
									 String dateTime=injuryDateEditText.getText().toString();
									  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
									  nameValuePairs.add(new BasicNameValuePair("InjuryDate", dateTime));				  
									  nameValuePairs.add(new BasicNameValuePair("CaseNumber", caseNumberEditText.getText().toString()));
									  nameValuePairs.add(new BasicNameValuePair("Note", noteEditText.getText().toString()));
									  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(isShareBoolean)));				  
									  nameValuePairs.add(new BasicNameValuePair("ReturnDate", returnDateEditText.getText().toString()));
									
									  
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
										dlgAlert.setMessage("WorkersComp added successfully");
									}
									else
									{
									dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
									}
									dlgAlert.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
													((CalendarEvents)context).createLayouts("WorkersCompSummary");
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

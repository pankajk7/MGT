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
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
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

public class EditWorkersComp{

	WorkersCompEntity objWorkersCompEntity;
	EditText injuryDateEditText;
	EditText returnDateEditText;
	EditText notesEditText;
	EditText caseNumberEditText;
	Context context;
	Button deleteButton;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean isShareBoolean;
	String eventNameString="workerscomp";
	String currentTimeString;
	String response;
	
	String idString;
	List<NameValuePair> nameValuePairs;
	
	public EditWorkersComp(Context context)
	{
		this.context=context;
	}
	
	public void createEditWorkersCompLayout(WorkersCompEntity objWorkersCompEntity) {
	try
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
		.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
		.getLayoutInflater().inflate(R.layout.edit_workers_comp,
				null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		CalendarEvents.stateBackButtonString="WorkersCompSummary";
		idString=objWorkersCompEntity.id;
		
		injuryDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editworkerscomp_injurydate);
		returnDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editworkerscomp_returndate);
		caseNumberEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editworkerscomp_casenumber);
		notesEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_editworkerscomp_notes);
		shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editworkerscomp_share_yes);
		shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_editworkerscomp_share_no);
		deleteButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editworkerscomp_delete);
		injuryDateEditText.setText(objWorkersCompEntity.injuryDate);
		returnDateEditText.setText(objWorkersCompEntity.returnDate);
		caseNumberEditText.setText(objWorkersCompEntity.caseNumber);
		notesEditText.setText(objWorkersCompEntity.notes);
		
		currentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objWorkersCompEntity.injuryDate);
		String retcurrentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objWorkersCompEntity.returnDate);
		injuryDateEditText.setText(currentTimeString);
		returnDateEditText.setText(retcurrentTimeString);
		
		if(objWorkersCompEntity.isShare.equals("true")){
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
			new MgtDatePickerDialog(context,injuryDateEditText,minDate,maxDate);
			}catch(Exception er){}
		
	
			try{
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				Date minDate=dt.parse("01/01/1970");
				Date maxDate=dt.parse("01/01/2099");
			//	String date=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTimeEntity.eventDate);
				//Date existingDate=dt.parse(date);
				new MgtDatePickerDialog(context,returnDateEditText,minDate,maxDate);
				}catch(Exception er){}
	}catch(Exception e){
		System.out.println(">>>Exception " + e + "  >>> Message : "
				+ e.getMessage());
	}
deleteButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			new CommonDeleteFunction().delete(context, eventNameString, idString, "Workers Comp Deleted successfully", "WorkersCompSummary");
			
			
		}
	});
		shareYesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					isShareBoolean=true;	
//					shareYesButton.setEnabled(false);
//					shareNoButton.setEnabled(true);
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
		
		addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_editworkerscomp_calendar);
		addCalendarButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(new MgtValidation(context).CheckWorkersComp(caseNumberEditText, notesEditText)==false)
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
							  nameValuePairs.add(new BasicNameValuePair("Id", idString));
							  String dateTime=injuryDateEditText.getText().toString();
							  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
							  nameValuePairs.add(new BasicNameValuePair("InjuryDate", dateTime));				  
							  nameValuePairs.add(new BasicNameValuePair("CaseNumber", caseNumberEditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("Note", notesEditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(isShareBoolean)));
							  String dateTime2=returnDateEditText.getText().toString();
							  dateTime2=MgtDateFormat.dateFormatSendingToServer(dateTime2);
							  nameValuePairs.add(new BasicNameValuePair("ReturnDate", dateTime2));
							  
							  
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
								dlgAlert.setMessage("WorkersComp updated successfully");
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

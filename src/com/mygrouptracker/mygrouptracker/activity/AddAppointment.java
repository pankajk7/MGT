package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.CustomOnItemSelectedListener;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.MgtDatePickerDialog;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAppointment {

	Context context;
	EditText eventDateEditText;
	EditText descriptionEditText;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Spinner recurringSpinner;
	Boolean shareBoolean=true;
	String eventNameString="appointment";
	String currentTimeString;
	String response;
	List<NameValuePair> nameValuePairs;
	
	public AddAppointment(Context context)
	{
		this.context=context;
	}
	
	public void createAddAppointmentLayout()
	{
		   FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
				.findViewById(R.id.layout_basecontainer);
				LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
				.getLayoutInflater().inflate(R.layout.add_appointment,
						null);
				baseLayout.removeAllViews();
				baseLayout.addView(inflateLayout);
				((CalendarEvents) context).getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
				
				eventDateEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addappointment_selectdate);
				descriptionEditText=(EditText)((CalendarEvents) context).findViewById(R.id.edittext_addappointment_description);				
				shareYesButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addappointment_share_yes);
				shareNoButton=(Button)((CalendarEvents) context).findViewById(R.id.btn_addappointment_share_no);
				recurringSpinner=(Spinner)((CalendarEvents)context).findViewById(R.id.spinner_addapppointment);
				
				currentTimeString=MgtDateFormat.getCurrentDateTime();
				eventDateEditText.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString)));
			    
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
						
//				recurringSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
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
							shareYesButton.setBackgroundResource(R.drawable.btn_yes);
							shareNoButton.setBackgroundResource(R.drawable.btn_gray);
					}
				});
							
				  
				addCalendarButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_addappointment_calendar);
				addCalendarButton.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
//						Toast.makeText(context, recurringSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
						  if(new MgtValidation(context).CheckApointment(eventDateEditText, descriptionEditText)==false)
					       {					        
					        return;
					       }
						  
						  
						try {
							new BackgroundNetwork(context) {
								protected Void doInBackground(Void[] params) {
//									String date=MgtDateFormat.dateFormatYYMMDDTime(eventDateEditText.getText().toString());
									nameValuePairs = new ArrayList<NameValuePair>();
									nameValuePairs.add(new BasicNameValuePair("Description", descriptionEditText.getText().toString()));
									nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
									nameValuePairs.add(new BasicNameValuePair("Name", descriptionEditText.getText().toString()));
									String dateTime=eventDateEditText.getText().toString();
									dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
									nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
									nameValuePairs.add(new BasicNameValuePair("Recurring", String.valueOf(recurringSpinner.getSelectedItemPosition())));
									  
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
										dlgAlert.setMessage("Appointment added successfully");
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
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.add_appointment);
//		
//		FrameLayout framelayout=(FrameLayout)findViewById(R.id.layout_basecontainer);
//		framelayout.addView(getLayoutInflater().inflate(R.layout.add_appointment, null));
//		
////		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
////		linearLayout.addView(getLayoutInflater().inflate(R.layout.add_appointment, null));
//		
//		
//		recurringSpinner=(Spinner)findViewById(R.id.spinner_addapppointment);
//		List<String> list = new ArrayList<String>();
//		list.add("None");
//		list.add("Weekly");
//		list.add("Biweekly");
//		list.add("Monthly");
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		recurringSpinner.setAdapter(dataAdapter);
//		
//		
////		backButton=(Button)findViewById(R.id.btn_back);
//		backButton.setVisibility(View.VISIBLE);
//		
//		backButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//	}
	

}

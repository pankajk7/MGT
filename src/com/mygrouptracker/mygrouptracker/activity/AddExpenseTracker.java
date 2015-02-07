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
import com.mygrouptracker.mygrouptracker.entity.ExpenseTrackerEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.MgtDatePickerDialog;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AddExpenseTracker extends BaseClass {
	
	TextView textView;
	EditText dateEditText;
	EditText desc1EditText;
	EditText desc2EditText;
	EditText desc3EditText;
	EditText amountEditText;
	Button saveButton;
	Button deleteButton;
	List<NameValuePair> nameValuePairs;
	String idString;
	String eventNameString="expense";
	String response;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.add_expense_tracker);
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.add_expense_tracker, null));
		
		dateEditText=(EditText)findViewById(R.id.edittext_addexpense_eventdate);
		desc1EditText=(EditText)findViewById(R.id.edittext_addexpense_desc1);
		desc2EditText=(EditText)findViewById(R.id.edittext_addexpense_desc2);
		desc3EditText=(EditText)findViewById(R.id.edittext_addexpense_desc3);
		amountEditText=(EditText)findViewById(R.id.edittext_addexpense_amount);
		saveButton=(Button)findViewById(R.id.btn_addexpense_save);
		backButton=(Button)findViewById(R.id.btn_baselayout_backbutton);
		backButton.setVisibility(View.VISIBLE);		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		//dateEditText.setText(MgtDateFormat.getCurrentDateTime());
		
		String  currentTimeString=MgtDateFormat.getCurrentDateTime();
		dateEditText.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString)));
		amountEditText.setText("$", TextView.BufferType.EDITABLE);
		
		amountEditText.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if(amountEditText.getText().toString().contains("$"))
					 amountEditText.setText(amountEditText.getText().toString());
					else
					 amountEditText.setText("$"+amountEditText.getText().toString());
				}
				return false;
			}
		});
		
		try{
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			Date minDate=dt.parse("01/01/1970");
			Date maxDate=dt.parse("01/01/2025");
			new MgtDatePickerDialog(AddExpenseTracker.this,dateEditText,minDate,maxDate);
			}catch(Exception er){}
	
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if(new MgtValidation(AddExpenseTracker.this).CheckExpenseTracker(desc1EditText, desc2EditText, desc3EditText, amountEditText)==false)
			       {					        
			        return;
			       }
				
				try {
					new BackgroundNetwork(AddExpenseTracker.this) {
						protected Void doInBackground(Void[] params) {
							
							  nameValuePairs = new ArrayList<NameValuePair>();
							  nameValuePairs.add(new BasicNameValuePair("Id", idString));
							  String dateTime=dateEditText.getText().toString();
							  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
							  nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
							  nameValuePairs.add(new BasicNameValuePair("Description1", desc1EditText.getText().toString()));
							  nameValuePairs.add(new BasicNameValuePair("Description2", desc2EditText.getText().toString()));				  
							  nameValuePairs.add(new BasicNameValuePair("Description3", desc3EditText.getText().toString()));
							
								String x=amountEditText.getText().toString().replace("$","");
							  nameValuePairs.add(new BasicNameValuePair("Amount", x));
							  
							DataEngine dataEngine = new DataEngine(AddExpenseTracker.this);
//							dataEngine.postKellyDayTest();	
							response=dataEngine.addRecord(eventNameString,nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							AlertDialog.Builder dlgAlert = new AlertDialog.Builder(AddExpenseTracker.this);
							dlgAlert.setTitle("Message");
							if(new StatusInfo().getStatusInfo(response).equals("Success"))
							{
								dlgAlert.setMessage("ExpenseTracker added successfully");
							}
							else
							{
							dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
							}
							dlgAlert.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											finish();
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

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
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
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

public class EditTradeTime extends BaseClass {

	TradeTimeEntity objTradeTimeEntity;
	EditText nameEditText;
	EditText dateEditText;
	EditText hoursEditText;
	EditText noteEditText;
	Context context;
	Button iOweButton;
	Button oweMEButton;
	Button addCalendarButton;
	Boolean isOweMeBoolean;
	String eventNameString = "tradetime";
	String currentTimeString;
	String response;
	String idString;
	Button deleteButton;
	List<NameValuePair> nameValuePairs;

	public EditTradeTime(Context context) {
		this.context = context;
	}

	public void createEditTradeTimeLayout(TradeTimeEntity objTradeTimeEntity) {
	try
	{
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
				.getLayoutInflater().inflate(R.layout.edit_trade_time, null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);

		CalendarEvents.stateBackButtonString = "TradeTimeSummary";
		idString = objTradeTimeEntity.id;

		dateEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_edittradetimesummary_date);
		noteEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_edittradetimesummary_notes);
		nameEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_edittradetimesummary_name);
		hoursEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_edittradetimesummary_hours);
	    iOweButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_edittradetime_iowe);
	    oweMEButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_edittradetime_oweme);
	    deleteButton=(Button)((CalendarEvents)context).findViewById(R.id.btn_edittradetime_delete);
		dateEditText.setText(objTradeTimeEntity.date);
		noteEditText.setText(objTradeTimeEntity.note);
		hoursEditText.setText(objTradeTimeEntity.hours);
		nameEditText.setText(objTradeTimeEntity.name);

		currentTimeString=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objTradeTimeEntity.date);
		dateEditText.setText(currentTimeString);
//		currentTimeString=MgtDateFormat.getCurrentDateTime();
//		dateEditText.setText(MgtDateFormat.dateFormatYYMMDDTime(currentTimeString));
		
		if(objTradeTimeEntity.isOweMe.equals("true")){
			oweMEButton.setBackgroundResource(R.drawable.btn_gray);
			iOweButton.setBackgroundResource(R.drawable.btn_no);
		}
		else{
			iOweButton.setBackgroundResource(R.drawable.btn_gray);
			oweMEButton.setBackgroundResource(R.drawable.btn_yes);
		}
		
		
		try{
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			Date minDate=dt.parse("01/01/1970");
			Date maxDate=dt.parse("01/01/2099");
		//	String date=MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTimeEntity.eventDate);
			//Date existingDate=dt.parse(date);
			new MgtDatePickerDialog(context,dateEditText,minDate,maxDate);
			}catch(Exception er){}
	}catch(Exception e){
		System.out.println(">>>Exception " + e + "  >>> Message : "
				+ e.getMessage());
	}
deleteButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			new CommonDeleteFunction().delete(context, eventNameString, idString, "Trade Time Deleted successfully", "TradeTimeSummary");
			
			
		}
	});
		iOweButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isOweMeBoolean = false;
//				oweMEButton.setEnabled(true);
//				iOweButton.setEnabled(false);
				iOweButton.setBackgroundResource(R.drawable.btn_gray);
				oweMEButton.setBackgroundResource(R.drawable.btn_yes);
			}
		});

		oweMEButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isOweMeBoolean = true;
//				iOweButton.setEnabled(true);
//				oweMEButton.setEnabled(false);
				iOweButton.setBackgroundResource(R.drawable.btn_no);
				oweMEButton.setBackgroundResource(R.drawable.btn_gray);
			}
		});

		addCalendarButton = (Button) ((CalendarEvents) context)
				.findViewById(R.id.btn_edittradetime_edit);
		addCalendarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(new MgtValidation(context).CheckTradeTime(nameEditText, hoursEditText, noteEditText)==false)
			       {					        
			        return;
			       }
				try {
					new BackgroundNetwork(context) {
						protected Void doInBackground(Void[] params) {
							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Id",
									idString));
							nameValuePairs.add(new BasicNameValuePair("Name",
									nameEditText.getText().toString()));
							String dateTime=dateEditText.getText().toString();
							  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
							nameValuePairs.add(new BasicNameValuePair("Date",
									dateTime));
							nameValuePairs.add(new BasicNameValuePair("Hour",
									hoursEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("Note",
									noteEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"IsOweMe", String.valueOf(isOweMeBoolean)));

							DataEngine dataEngine = new DataEngine(context);
							response=dataEngine.updateRecord(eventNameString,
									nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
							dlgAlert.setTitle("Message");
							if(new StatusInfo().getStatusInfo(response).equals("Success"))
							{
								dlgAlert.setMessage("TradeTime updated successfully");
							}
							else
							{
							dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
							}
							dlgAlert.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											((CalendarEvents)context).createLayouts("TradeTimeSummary");
										}
									});
							dlgAlert.setCancelable(true);
							dlgAlert.create().show();
						};
					}.execute();
				} catch (Exception e) {
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				}
			}
		});
	}
}

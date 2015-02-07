package com.mygrouptracker.mygrouptracker.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
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

public class AddVacationTime {

	Context context;
	EditText startEditText;
	EditText hourEditText;
	EditText noteEditText;
	EditText endEditText;
	Button shareYesButton;
	Button shareNoButton;
	Button addCalendarButton;
	Boolean isShareBoolean = true;
	String eventNameString = "vacationtime";
	String currentTimeString;
	String response;
	List<NameValuePair> nameValuePairs;

	public AddVacationTime(Context context) {
		this.context = context;
	}

	public void createAddVacationTimeLayout() {
		FrameLayout baseLayout = (FrameLayout) ((CalendarEvents) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarEvents) context)
				.getLayoutInflater().inflate(R.layout.add_vacation_time, null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);

		startEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_addvactiontime_start);
		hourEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_addvacationtime_hours);
		noteEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_addvacationtime_note);
		endEditText = (EditText) ((CalendarEvents) context)
				.findViewById(R.id.edittext_addvactiontime_end);
		shareYesButton = (Button) ((CalendarEvents) context)
				.findViewById(R.id.btn_addvacationtime_share_yes);
		shareNoButton = (Button) ((CalendarEvents) context)
				.findViewById(R.id.btn_addvacationtime_share_no);

		currentTimeString = MgtDateFormat.getCurrentDateTime();

		startEditText.setText(MgtDateFormat
				.dateFormatMMddYYYYCalendar(MgtDateFormat
						.dateFormatYYMMDDTime(currentTimeString)));
		endEditText.setText(MgtDateFormat
				.dateFormatMMddYYYYCalendar(MgtDateFormat
						.dateFormatYYMMDDTime(currentTimeString)));

		try {
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			Date minDate = dt.parse("01/01/1970");
			Date maxDate = dt.parse("01/01/2025");
			new MgtDatePickerDialog(context, startEditText, minDate, maxDate);
		} catch (Exception er) {
		}

		try {
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			Date minDate = dt.parse("01/01/1970");
			Date maxDate = dt.parse("01/01/2025");
			new MgtDatePickerDialog(context, endEditText, minDate, maxDate);
		} catch (Exception er) {
		}

		if (isShareBoolean == true) {
			shareYesButton.setBackgroundResource(R.drawable.btn_gray);
			shareNoButton.setBackgroundResource(R.drawable.btn_no);
		} else {
			shareYesButton.setBackgroundResource(R.drawable.btn_yes);
			shareNoButton.setBackgroundResource(R.drawable.btn_gray);
		}

		shareYesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// isShareBoolean=true;
				// shareNoButton.setEnabled(true);
				shareYesButton.setBackgroundResource(R.drawable.btn_gray);
				shareNoButton.setBackgroundResource(R.drawable.btn_no);
			}
		});

		shareNoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isShareBoolean = false;
				// shareYesButton.setEnabled(true);
				// shareNoButton.setEnabled(false);
				shareYesButton.setBackgroundResource(R.drawable.btn_yes);
				shareNoButton.setBackgroundResource(R.drawable.btn_gray);
			}
		});

		addCalendarButton = (Button) ((CalendarEvents) context)
				.findViewById(R.id.btn_addvacationtime_calendar);
		addCalendarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (new MgtValidation(context).CheckVacationTime(noteEditText,
						hourEditText) == false) {
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String endDateString = endEditText.getText().toString();
				String startDateString = startEditText.getText().toString();
				Date startDate = null;
				Date endDate = null;
				
				try {
					startDate = sdf.parse(startDateString);
					endDate = sdf.parse(endDateString);
				

				if (endDate.compareTo(startDate) < 0) {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
							context);
					dlgAlert.setTitle("Message");
					dlgAlert.setMessage("End date cant be greater than start date.");
					dlgAlert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					dlgAlert.setCancelable(true);
					dlgAlert.create().show();

				} else {
					try {
						new BackgroundNetwork(context) {
							protected Void doInBackground(Void[] params) {

								nameValuePairs = new ArrayList<NameValuePair>();
								String dateTime = startEditText.getText()
										.toString();
								dateTime = MgtDateFormat
										.dateFormatSendingToServer(dateTime);
								nameValuePairs.add(new BasicNameValuePair(
										"Start", dateTime));
								nameValuePairs
										.add(new BasicNameValuePair("End",
												endEditText.getText()
														.toString()));
								nameValuePairs.add(new BasicNameValuePair(
										"Hour", hourEditText.getText()
												.toString()));
								nameValuePairs.add(new BasicNameValuePair(
										"IsShare", String
												.valueOf(isShareBoolean)));
								nameValuePairs.add(new BasicNameValuePair(
										"Note", noteEditText.getText()
												.toString()));

								DataEngine dataEngine = new DataEngine(context);
								// dataEngine.postKellyDayTest();
								response = dataEngine.addRecord(
										eventNameString, nameValuePairs);
								return null;
							};

							protected void onPostExecute(Void result) {
								super.onPostExecute(result);
								AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
										context);
								dlgAlert.setTitle("Message");
								if (new StatusInfo().getStatusInfo(response)
										.equals("Success")) {
									dlgAlert.setMessage("VacationTime added successfully");
								} else {
									dlgAlert.setMessage(new StatusInfo()
											.getStatusInfo(response));
								}
								dlgAlert.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												((CalendarEvents) context)
														.createLayouts("VacationTimeSummary");
											}
										});
								dlgAlert.setCancelable(true);
								dlgAlert.create().show();
							};
						}.execute();
					} catch (Exception e) {
						System.out.println(">>>Exception " + e
								+ "  >>> Message : " + e.getMessage());
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

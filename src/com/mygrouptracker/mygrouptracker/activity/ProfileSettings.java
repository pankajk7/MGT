package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ProfileSettings {
	
	Context context;
	EditText firstNameEditText;
	EditText lastNameEditText;
	EditText badgeEditText;
	EditText emailEditText;
	//EditText passwordEditText;
	Button updateButton;
	
	SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	String eventNameString="userprofile";
	String idString;
	List<NameValuePair> nameValuePairs;
	String response;
	
	public ProfileSettings(Context context)
	{
		this.context=context;
	}
	
	public void  createLayoutProfileSettings()
	{
		
		FrameLayout baseLayout = (FrameLayout) ((CalendarSettingsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarSettingsActivity) context)
				.getLayoutInflater().inflate(R.layout.edit_profile_settings,null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		firstNameEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_editprofilesettings_firstname);
		lastNameEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_editprofilesettings_lastname);
		badgeEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_editprofilesettings_badge);
		emailEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_editprofilesettings_email);
		//passwordEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_editprofilesettings_password);
		updateButton=(Button)((CalendarSettingsActivity)context).findViewById(R.id.btn_editprofilesettings_updateprofile);
		
		/*loginPreferences = context.getSharedPreferences("loginPrefs", 1);
		loginPrefsEditor = loginPreferences.edit();
		firstNameEditText.setText(loginPreferences.getString("firstName", ""));
		lastNameEditText.setText(loginPreferences.getString("lastName", ""));
		badgeEditText.setText(loginPreferences.getString("badge", ""));
		emailEditText.setText(loginPreferences.getString("userEmail", ""));
		passwordEditText.setText(loginPreferences.getString("password", ""));
		idString=loginPreferences.getString("id", "");*/
		
		firstNameEditText.setText(LoginActivity.loginUserObj.firstName);
		lastNameEditText.setText(LoginActivity.loginUserObj.lastName);
		badgeEditText.setText(LoginActivity.loginUserObj.badge);
		emailEditText.setText(LoginActivity.loginUserObj.email);
		//passwordEditText.setText(LoginActivity.loginUserObj.password);
		idString=LoginActivity.loginUserObj.userRefNo;
		
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					new BackgroundNetwork(context) {
						protected Void doInBackground(Void[] params) {
							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Id", idString));
							nameValuePairs.add(new BasicNameValuePair("FirstName", firstNameEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("LastName", lastNameEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("Badge", badgeEditText.getText().toString()));
						//	nameValuePairs.add(new BasicNameValuePair("Password", passwordEditText.getText().toString()));
							  
							DataEngine dataEngine = new DataEngine(context);
//							dataEngine.postKellyDayTest();	
							response=dataEngine.updateRecord(eventNameString,nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							try
							{
								AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
								dlgAlert.setTitle("Message");
								dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
								
								dlgAlert.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
											}
										});
								dlgAlert.setCancelable(true);
								dlgAlert.create().show();
							
							}
							catch(Exception e){
								
								Log.e(">>>Error>>",e.getMessage());
							}
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

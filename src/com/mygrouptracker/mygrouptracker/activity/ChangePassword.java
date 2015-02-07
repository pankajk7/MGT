package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChangePassword {

	Context context;
	EditText oldPasswordEditText;
	EditText newPasswordEditText;
	EditText confirmPasswordEditText;
	String oldPassword;
	String newPassword;
	String confirmPassword;
	Button changePasswordButton;
	String eventNameString="ChangePassword";
	ArrayList<NameValuePair> nameValuePairs;
	String responseString;
	
	public ChangePassword(Context context){
		this.context=context;
	}
	
	public void  createLayoutChangePassword()
	{
		
		FrameLayout baseLayout = (FrameLayout) ((CalendarSettingsActivity) context).findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarSettingsActivity) context).getLayoutInflater().inflate(R.layout.change_password_activity,null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
	
		oldPasswordEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_changepassword_oldpassword);
		newPasswordEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_changepassword_newpassword);
		confirmPasswordEditText=(EditText)((CalendarSettingsActivity) context).findViewById(R.id.edittext_changepassword_confirmpassword);
		changePasswordButton=(Button)((CalendarSettingsActivity) context).findViewById(R.id.btn_changepassword_changepassowrd);
		
		changePasswordButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Boolean isEmpty=new MgtValidation((CalendarSettingsActivity) context).CheckChangePassword(newPasswordEditText, oldPasswordEditText, confirmPasswordEditText);
			   if(isEmpty==false)
			   {	
				   return;
			   }
			        
				oldPassword=oldPasswordEditText.getText().toString();
				newPassword=newPasswordEditText.getText().toString();
				confirmPassword=confirmPasswordEditText.getText().toString();
				if(!new MgtValidation((CalendarSettingsActivity) context).checkPasswordLength(newPassword, 6))
				{
					return;
				}
				
				if(oldPassword.equals(LoginActivity.loginUserObj.password))
				{
					if(newPassword.equals(confirmPassword))
					{
						try {
							new BackgroundNetwork(context) {
								protected Void doInBackground(Void[] params) {
									nameValuePairs = new ArrayList<NameValuePair>();
									nameValuePairs.add(new BasicNameValuePair("Id", LoginActivity.loginUserObj.userRefNo));
									nameValuePairs.add(new BasicNameValuePair("NewPassword", newPasswordEditText.getText().toString()));
									
									  
									DataEngine dataEngine = new DataEngine(context);
//									dataEngine.postKellyDayTest();	
									responseString=dataEngine.updateRecord(eventNameString,nameValuePairs);
									return null;
								};

								protected void onPostExecute(Void result) {
									
									super.onPostExecute(result);
									AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
									dlgAlert.setTitle("Message");
									if(new StatusInfo().getStatusInfo(responseString).equals("Success"))
									{
										dlgAlert.setMessage("Password changed successfully");
									}
									else
									{
									dlgAlert.setMessage(new StatusInfo().getStatusInfo(responseString));
									}
									dlgAlert.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
												
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
					else{
						Toast.makeText(context, "New and Confirm Password does not matched", Toast.LENGTH_LONG).show();
					}
				}
				else{
					Toast.makeText(context, "Old Password is not correct", Toast.LENGTH_LONG).show();
				}
			 }
		  
		});
		
	}
}

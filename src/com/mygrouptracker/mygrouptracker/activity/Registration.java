package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Registration {
	
	EditText userEmailEditText;
	EditText passwordEditText;
	EditText firstNameEditText;
	EditText lastNameEditText;
	EditText phoneNumberEditText;
	Button registerButton;
	Button backFormButton;	
	String eventNameString="userregistration";
	List<NameValuePair> nameValuePairs;
	Context context;
	
//	FirstName=Tom&LastName=Cruise&Email=tom%40name.com&Phone=7777777777&Password=3333&Badge=16
	
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		createRegistrationLayout();
//	}
	
	public Registration(Context context)
	{
	  this.context=context;
	}
	
	public void createRegistrationLayout()
	{
		 FrameLayout baseLayout = (FrameLayout)((LoginActivity) context).findViewById(R.id.container_layout);
		 baseLayout.removeAllViews();
		 
		 LinearLayout inflateLayout = (LinearLayout) ((LoginActivity) context)
					.getLayoutInflater().inflate(R.layout.register,null);		 
		 baseLayout.addView(inflateLayout);
		 
		 ((LoginActivity) context).getWindow().setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
					
		userEmailEditText=(EditText)((LoginActivity) context).findViewById(R.id.edittext_registration_useremail);
		passwordEditText=(EditText)((LoginActivity) context).findViewById(R.id.edittext_registration_password);			
		firstNameEditText=(EditText)((LoginActivity) context).findViewById(R.id.edittext_registration_firstname);
		lastNameEditText=(EditText)((LoginActivity) context).findViewById(R.id.edittext_registration_lastname);
		phoneNumberEditText=(EditText)((LoginActivity) context).findViewById(R.id.edittext_registration_phonenumber);
		registerButton=(Button)((LoginActivity) context).findViewById(R.id.btn_registration_register);
		
		userEmailEditText.setText("jhonny7777@name.com");		
		passwordEditText.setText("7777");
		firstNameEditText.setText("Jhonny");
		lastNameEditText.setText("Depp");
		phoneNumberEditText.setText("7777777777");
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				try {
					new BackgroundNetwork(context) {
						protected Void doInBackground(Void[] params) {
				
							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Email", userEmailEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("Password", passwordEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("FirstName", firstNameEditText.getText().toString()));	
							nameValuePairs.add(new BasicNameValuePair("LastName", lastNameEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("Phone", phoneNumberEditText.getText().toString()));
				  
							DataEngine dataEngine = new DataEngine(context);
							dataEngine.addRecord(eventNameString,nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
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

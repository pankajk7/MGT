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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AddMemberActivity {
	Context context;
	Button addButton;
	EditText emailEditText;
	String response;
	String eventNameString="network";
	List<NameValuePair> nameValuePairs;
	
	public AddMemberActivity(Context context){
		this.context=context;
	}
	
	public void createAddMemberLayout(){
		FrameLayout baseLayout = (FrameLayout) ((CalendarSettingsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarSettingsActivity) context)
				.getLayoutInflater().inflate(R.layout.add_member,null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		emailEditText=(EditText)((CalendarSettingsActivity)context).findViewById(R.id.edittext_addmember_email);
		addButton=(Button)((CalendarSettingsActivity)context).findViewById(R.id.btn_addmember_add);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					new BackgroundNetwork(context) {
						
						@Override
						protected Void doInBackground(Void[] params) {
											
							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Email", emailEditText.getText().toString()));
							DataEngine dataEngine = new DataEngine(context);
							response=dataEngine.addRecord(eventNameString, nameValuePairs);
							
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
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
						};
					}.execute();
				}
				catch(Exception e){
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				 }
			}
		});
		
	}
}

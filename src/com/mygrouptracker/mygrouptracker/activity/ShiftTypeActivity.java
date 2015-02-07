package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.entity.ShiftType;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.NoCopySpan.Concrete;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ShiftTypeActivity{

	Spinner shiftTypeSpinner;
	Context context;
	Button updateButton;
	List<NameValuePair> nameValuePairs;
	ArrayList<ShiftType> shiftTypeList;
	ShiftType objShiftType;
	String eventNameString="usershifttype";
	String response;
	
	public ShiftTypeActivity(Context context){
		this.context=context;
	}
	
	
   public void createLayoutShiftType()
   {
		
	   FrameLayout baseLayout = (FrameLayout) ((CalendarSettingsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarSettingsActivity) context)
				.getLayoutInflater().inflate(R.layout.shift_type_activity,null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		shiftTypeSpinner=(Spinner)((CalendarSettingsActivity)context).findViewById(R.id.spinner_selectshifttype_shifttype);
		updateButton=(Button)((CalendarSettingsActivity)context).findViewById(R.id.btn_selectshifttype_updateprofile);
		
		try {
			new BackgroundNetwork(context) {
				protected Void doInBackground(Void[] params) {
					
					shiftTypeList=new ArrayList<ShiftType>();
					DataEngine dataEngine = new DataEngine(context);
					shiftTypeList=dataEngine.getShiftTypeList();
					
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					
					try
					{							  
						ArrayAdapter<ShiftType> dataAdapter = new ArrayAdapter<ShiftType>(context, android.R.layout.simple_spinner_item,shiftTypeList);
						dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						shiftTypeSpinner.setAdapter(dataAdapter);
						dataAdapter.notifyDataSetChanged();
						
//						shiftTypeSpinner.setOnItemClickListener(new OnItemClickListener() {
//
//							@Override
//							public void onItemClick(AdapterView<?> arg0,
//									View arg1, int arg2, long arg3) {
//								ShiftType objShiftType=(ShiftType)shiftTypeSpinner.getItemAtPosition(arg2);
////								Toast.makeText(context, "MemberName "+arg2+" : "+objShiftType.memberName.toString(), Toast.LENGTH_LONG).show();
//							}
//						});
					}
					catch (Exception e) {
						System.out.print(">>>ShiftTypeActivity Error>>>"+e.toString()+">>>"+e.getMessage());
						Log.e("ShiftTypeerror", e.toString()+">>>"+e.getMessage());
					}
				};
			}.execute();
		}
		 catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
try {
					
					new BackgroundNetwork(context) {
						
						@Override
						protected Void doInBackground(Void[] params) {
											
							objShiftType=new ShiftType();
							objShiftType=(ShiftType) shiftTypeSpinner.getSelectedItem();
							
							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Id",LoginActivity.idString));
							nameValuePairs.add(new BasicNameValuePair("MemberShiftId",objShiftType.idString));
							DataEngine dataEngine = new DataEngine(context);
							response=dataEngine.updateRecord(eventNameString, nameValuePairs);
							
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

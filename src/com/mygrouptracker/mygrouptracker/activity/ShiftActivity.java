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
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ShiftActivity implements OnItemSelectedListener {
  
	Spinner selectShiftSpinner;
	Context context;
	Button updateButton;
	String response;
	String eventNameString="UserShiftType";
	ArrayList<Shift> shiftArrayList;
	List<NameValuePair> nameValuePairs;
	Shift shiftObj;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.shift_activity);
	
	public ShiftActivity(Context context){
		this.context=context;
	}
	
	public void createLayoutShiftActivity(){
		
		FrameLayout baseLayout = (FrameLayout) ((CalendarSettingsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarSettingsActivity) context)
				.getLayoutInflater().inflate(R.layout.shift_activity,null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		
		selectShiftSpinner=(Spinner)((CalendarSettingsActivity)context).findViewById(R.id.spinner_selectshift_shift);
		updateButton=(Button)((CalendarSettingsActivity)context).findViewById(R.id.btn_selectshift_updateprofile);
		
		shiftArrayList=new ArrayList<Shift>();
		try {
			
			new BackgroundNetwork(context) {
				
				@Override
				protected Void doInBackground(Void[] params) {
					try{				
						DataEngine dataEngine = new DataEngine(context);
						shiftArrayList=dataEngine.getShiftList();
					}catch(Exception er)
					{
						System.out.print("Error Shift Activity:->>"+er.toString());
						
					}
					
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
										
					try
					{
						SharedPreferences sharedPreferences;
						SharedPreferences.Editor sharedPrefsEditor;
						sharedPreferences = context.getSharedPreferences("loginPrefs", context.MODE_PRIVATE);		
						String memberShiftId = sharedPreferences.getString("memberShiftId", "");
						
						if(shiftArrayList != null){
							ArrayAdapter<Shift> dataAdapter = new ArrayAdapter<Shift>(context, android.R.layout.simple_spinner_item,shiftArrayList);
					
							dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							selectShiftSpinner.setAdapter(dataAdapter);
							for (int position = 0; position < dataAdapter.getCount(); position++)
						    {
								String Id = dataAdapter.getItem(position).id;
								System.out.println(">>>ID>>>" + Id);
						        if(memberShiftId.equals(Id))
						        {
						            selectShiftSpinner.setSelection(position);
						            break;
						        }
						    }
//							selectShiftSpinner.setSelection(dataAdapter.getPosition("Category 2"));
							selectShiftSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
								
								@Override
								public void onItemSelected(AdapterView<?> parent, View view,
										int position, long id) {
									// 	TODO Auto-generated method stub
									try{
										Shift shift=(Shift)parent.getItemAtPosition(position);
									}catch(Exception er){}
//								Toast.makeText(context, shift.shiftName+" "+position+" Hourspday "+shift.hoursPerDay, Toast.LENGTH_LONG).show();
							
								}

								@Override
								public void onNothingSelected(AdapterView<?> parent) {
									// TODO Auto-generated method stub
							
								}
							});
						}//End of if
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
		
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					shiftObj=new Shift();
					new BackgroundNetwork(context) {
						
						@Override
						protected Void doInBackground(Void[] params) {
											
							
							shiftObj=(Shift) selectShiftSpinner.getSelectedItem();
							
							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Id",LoginActivity.idString));
							nameValuePairs.add(new BasicNameValuePair("MemberShiftId",shiftObj.id));
							DataEngine dataEngine = new DataEngine(context);
							response=dataEngine.updateRecord(eventNameString, nameValuePairs);
							
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
												
							try
							{
								
								
								if("Success".equals(new StatusInfo().getStatusInfo(response))){
//									SharedPreferences sharedPreferences;
//									SharedPreferences.Editor sharedPrefsEditor;
//									
//									sharedPreferences = context.getSharedPreferences("shift", context.MODE_PRIVATE);
//									sharedPrefsEditor = sharedPreferences.edit();
//									sharedPrefsEditor.putString("shiftName", shiftObj.shiftName);
//									sharedPrefsEditor.putString("startDate", shiftObj.startDate);
//									sharedPrefsEditor.putString("day01", shiftObj.day01);
//									sharedPrefsEditor.putString("day01ColorName", shiftObj.day01ColorName);
//									sharedPrefsEditor.putString("day01ColorCode", shiftObj.day01ColorCode);
//									sharedPrefsEditor.putString("day02", shiftObj.day02);
//									sharedPrefsEditor.putString("day02ColorName", shiftObj.day02ColorName);
//									sharedPrefsEditor.putString("day02ColorCode", shiftObj.day02ColorCode);
//									sharedPrefsEditor.putString("day03", shiftObj.day03);
//									sharedPrefsEditor.putString("day03ColorName", shiftObj.day03ColorName);
//									sharedPrefsEditor.putString("day03ColorCode", shiftObj.day03ColorCode);
//									sharedPrefsEditor.putString("day04", shiftObj.day04);
//									sharedPrefsEditor.putString("day04ColorName", shiftObj.day04ColorName);
//									sharedPrefsEditor.putString("day04ColorCode", shiftObj.day04ColorCode);
//									sharedPrefsEditor.putString("day05", shiftObj.day05);
//									sharedPrefsEditor.putString("day05ColorName", shiftObj.day05ColorName);
//									sharedPrefsEditor.putString("day05ColorCode", shiftObj.day05ColorCode);
//									sharedPrefsEditor.putString("day06", shiftObj.day06);
//									sharedPrefsEditor.putString("day06ColorName", shiftObj.day06ColorName);
//									sharedPrefsEditor.putString("day06ColorCode", shiftObj.day06ColorCode);
//									sharedPrefsEditor.commit();
//									
//									sharedPreferences = context.getSharedPreferences("loginPrefs", context.MODE_PRIVATE);
//									sharedPrefsEditor = sharedPreferences.edit();
//									sharedPrefsEditor.putString("memberShiftId", shiftObj.id);
//									sharedPrefsEditor.commit();
									SharedPreferences sharedPreferences;
									final SharedPreferences.Editor sharedPrefsEditor;
									
									
//									sharedPreferences = context.getSharedPreferences("loginPrefs", context.MODE_PRIVATE);		
//									final String memberShiftId = sharedPreferences.getString("memberShiftId", "");
									sharedPreferences = context.getSharedPreferences("shift", context.MODE_PRIVATE);
									sharedPrefsEditor = sharedPreferences.edit();
									
										
										new BackgroundNetwork(context) {
											Shift shiftNewObj = new Shift();
											protected Void doInBackground(Void[] params) {
												DataEngine dataEngine = new DataEngine(context);
												shiftNewObj = dataEngine.getShift(shiftObj.id);
												return null;
											};

											protected void onPostExecute(Void result) {	
												
												if(shiftNewObj != null){
													//storing color name and code in shift Preference
													sharedPrefsEditor.putString("shiftName", shiftNewObj.shiftName);
													sharedPrefsEditor.putString("startDate", shiftNewObj.startDate);
//													shiftObj.day = new ArrayList<String>();
//													shiftObj.ColorCode = new ArrayList<String>();
//													shiftObj.ColorName = new ArrayList<String>();
													
													for (int i = 1; i <= 18; i++) {
														try{
														if (i < 10) {
															sharedPrefsEditor.putString("day0"+i,
																	shiftObj.day.get(i));
															sharedPrefsEditor.putString("day0"+i+"ColorName",
																	shiftObj.ColorName.get(i));
															sharedPrefsEditor.putString("day0"+i+"ColorCode",
																	shiftObj.ColorCode.get(i));
														} else {
															sharedPrefsEditor.putString("day"+i,
																	shiftObj.day.get(i));
															sharedPrefsEditor.putString("day"+i+"ColorName",
																	shiftObj.ColorName.get(i));
															sharedPrefsEditor.putString("day"+i+"ColorCode",
																	shiftObj.ColorCode.get(i));
														}
														} catch (Exception er) {
															er.printStackTrace();
														}
													}
//													sharedPrefsEditor.putString("day01", shiftNewObj.day01);
//													sharedPrefsEditor.putString("day01ColorName", shiftNewObj.day01ColorName);
//													sharedPrefsEditor.putString("day01ColorCode", shiftNewObj.day01ColorCode);
//													sharedPrefsEditor.putString("day02", shiftNewObj.day02);
//													sharedPrefsEditor.putString("day02ColorName", shiftNewObj.day02ColorName);
//													sharedPrefsEditor.putString("day02ColorCode", shiftNewObj.day02ColorCode);
//													sharedPrefsEditor.putString("day03", shiftNewObj.day03);
//													sharedPrefsEditor.putString("day03ColorName", shiftNewObj.day03ColorName);
//													sharedPrefsEditor.putString("day03ColorCode", shiftNewObj.day03ColorCode);
//													sharedPrefsEditor.putString("day04", shiftNewObj.day04);
//													sharedPrefsEditor.putString("day04ColorName", shiftNewObj.day04ColorName);
//													sharedPrefsEditor.putString("day04ColorCode", shiftNewObj.day04ColorCode);
//													sharedPrefsEditor.putString("day05", shiftNewObj.day05);
//													sharedPrefsEditor.putString("day05ColorName", shiftNewObj.day05ColorName);
//													sharedPrefsEditor.putString("day05ColorCode", shiftNewObj.day05ColorCode);
//													sharedPrefsEditor.putString("day06", shiftNewObj.day06);
//													sharedPrefsEditor.putString("day06ColorName", shiftNewObj.day06ColorName);
//													sharedPrefsEditor.putString("day06ColorCode", shiftNewObj.day06ColorCode);
													sharedPrefsEditor.commit();
													
													//storing membershiftId in loginPref 
													SharedPreferences sp = context.getSharedPreferences("loginPrefs", context.MODE_PRIVATE);
													SharedPreferences.Editor spEditor = sp.edit();
													spEditor.putString("memberShiftId", shiftObj.id);
													spEditor.commit();
													
//													sp = context.getSharedPreferences("calendar", context.MODE_PRIVATE);
//													spEditor = sp.edit();
//													spEditor.remove("calendar");
//													spEditor.commit();
												}
													
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
											}
										}.execute();
								}
							
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
	
	public void setValues(ArrayList<Shift> list)
	{
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.shift, menu);
//		return true;
//	}

}

package com.mygrouptracker.mygrouptracker.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.AppointmentSummaryBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterCompTime;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterOverTimeSummary;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterSickTime;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterTradeTimeArchive;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterTradeTimeSummary;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterVacationTime;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterWorkersComp;
import com.mygrouptracker.mygrouptracker.adapter.CalendarAdapter;
import com.mygrouptracker.mygrouptracker.adapter.KellyDayBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.data.RestfulService;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.listview_populate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarEvents extends BaseTabTitleActivity {

	// XML node keys
	static final String KEY_TAG = "calendar"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_ICON = "icon";
	static final String KEY_NAME = "name";
	int comptimeEarned=0;
	int comptimeTaken=0;
	String rawxmlString = "calendarevents.xml";
	public static String stateBackButtonString;
	ListView listview;
	ListBaseAdapter adapter = null;
	List<HashMap<String, String>> homeDataCollection;
	View view;
	FrameLayout framelayout;
	// Button backButton;
	List<AppointmentSummaryEntity> apptSummaryList;
	ArrayList<CompTimeEntity> compTimeArrayList;
	ArrayList<KellyDayEntity> kellyDayList;
	ArrayList<OverTimeEntity> overTimeEntityArrayList;
	ArrayList<SickTimeEntity> sickTimeEntityArrayList;
	ArrayList<TradeTimeEntity> tradeTimeEntityArrayList;
	ArrayList<VacationTimeEntity> vacationEntityArrayList;
	ArrayList<WorkersCompEntity> workersCompArrayList;
	int balance;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		// framelayout=(FrameLayout)findViewById(R.id.layout_basecontainer);
		// framelayout.addView(getLayoutInflater().inflate(R.layout.calendar_events,
		// null));
		//
		// try {
		// DocumentBuilderFactory docBuilderFactory =
		// DocumentBuilderFactory.newInstance();
		// DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		// Document doc = docBuilder.parse (getAssets().open(rawxmlString));
		//
		// homeDataCollection = new ArrayList<HashMap<String,String>>();
		//
		//
		//
		// listview_populate list=new listview_populate();
		// homeDataCollection=list.populateListView(doc,rawxmlString,KEY_TAG);
		//
		// ListBaseAdapter adapter = new
		// ListBaseAdapter(this,homeDataCollection);
		// listview = (ListView) findViewById(R.id.listcalendarevents);
		// listview.setAdapter(adapter);
		// listview.setSelector(R.drawable.tranparent_selection);
		//
		// }
		//
		// catch (IOException ex) {
		// Log.e("Error", ex.getMessage());
		// }
		// catch (Exception ex) {
		// Log.e("Error", "Loading exception");
		// }

		createCalendarEventsLayout();

	}

	@Override
	public void onBackPressed() {
		// CalendarEvents.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		if (stateBackButtonString.equals("CalendarEvents"))
			createCalendarEventsLayout();
		else if (stateBackButtonString.equals("AppointmentSummary"))
			createLayouts("AppointmentSummary");
		else if (stateBackButtonString.equals("CompTimeSummary"))
			createLayouts("CompTimeSummary");
		else if (stateBackButtonString.equals("KellyDaySummary"))
			createLayouts("KellyDaySummary");
		else if (stateBackButtonString.equals("OverTimeSummary"))
			createLayouts("OverTimeSummary");
		else if (stateBackButtonString.equals("SickTimeSummary"))
			createLayouts("SickTimeSummary");
		else if (stateBackButtonString.equals("TradeTimeSummary"))
			createLayouts("TradeTimeSummary");
		else if (stateBackButtonString.equals("VacationTimeSummary"))
			createLayouts("VacationTimeSummary");
		else if (stateBackButtonString.equals("WorkersCompSummary"))
			createLayouts("WorkersCompSummary");
		else if (stateBackButtonString.equals("finish"))
			finish();
	}

	protected void createCalendarEventsLayout() {

		framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.calendar_events, null));
		stateBackButtonString = "finish";
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(getAssets().open(rawxmlString));

			homeDataCollection = new ArrayList<HashMap<String, String>>();

			listview_populate list = new listview_populate();
			homeDataCollection = list.populateListView(doc, rawxmlString,
					KEY_TAG);

			ListBaseAdapter adapter = new ListBaseAdapter(this,
					homeDataCollection);
			listview = (ListView) findViewById(R.id.listcalendarevents);
			listview.setAdapter(adapter);
			listview.setSelector(R.drawable.tranparent_selection);

		} catch (IOException ex) {
			Log.e("Error", ex.getMessage());
		} catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent;
				switch (position) {
				case 0:
					createLayouts("AppointmentSummary");
					break;

				case 1:
					createLayouts("CompTimeSummary");
					break;

				case 2:
					createLayouts("KellyDaySummary");
					break;

				case 3:
					createLayouts("OverTimeSummary");
					break;

				case 4:
					createLayouts("SickTimeSummary");
					break;

				case 5:
					createLayouts("TradeTimeSummary");
					break;

				case 6:
					createLayouts("VacationTimeSummary");
					break;

				case 7:
					createLayouts("WorkersCompSummary");
					break;

				default:
					break;
				}

			}
		});

		// backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// onBackPressed();
				InputMethodManager inputMethodManager = (InputMethodManager) CalendarEvents.this
						.getSystemService(Activity.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(CalendarEvents.this
						.getCurrentFocus().getWindowToken(), 0);

				if (stateBackButtonString.equals("CalendarEvents"))
					createCalendarEventsLayout();
				else if (stateBackButtonString.equals("AppointmentSummary"))
					createLayouts("AppointmentSummary");
				else if (stateBackButtonString.equals("CompTimeSummary"))
					createLayouts("CompTimeSummary");
				else if (stateBackButtonString.equals("KellyDaySummary"))
					createLayouts("KellyDaySummary");
				else if (stateBackButtonString.equals("OverTimeSummary"))
					createLayouts("OverTimeSummary");
				else if (stateBackButtonString.equals("SickTimeSummary"))
					createLayouts("SickTimeSummary");
				else if (stateBackButtonString.equals("TradeTimeSummary"))
					createLayouts("TradeTimeSummary");
				else if (stateBackButtonString.equals("VacationTimeSummary"))
					createLayouts("VacationTimeSummary");
				else if (stateBackButtonString.equals("WorkersCompSummary"))
					createLayouts("WorkersCompSummary");
				else if (stateBackButtonString.equals("finish"))
					finish();
			}
		});
	}
	
	public void emailButtonSetting(Button b,int size)
	{
		if(size==0)
		{
			b.setEnabled(false);
			b.setBackgroundResource(R.drawable.btn_gray);
		}
		else
		{
			b.setEnabled(true);
			
		}
		
	}

	public void createLayouts(String layoutNameString) {

		if (layoutNameString.equals("AppointmentSummary")) {
			try {
				framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
				framelayout.removeAllViews();
				framelayout.addView(getLayoutInflater().inflate(
						R.layout.appointment_summary, null));
				stateBackButtonString = "CalendarEvents";
				// AppointmentSummaryEntity objAppointmentSummaryEntity;
				// objAppointmentSummaryEntity = new AppointmentSummaryEntity();
				// List<AppointmentSummaryEntity> apptSummaryList;
				// apptSummaryList =
				// objAppointmentSummaryEntity.getapptSummary();
				 Button addAppointmentButton = (Button) findViewById(R.id.btn_appointmentsummary_addappt);
				 final	Button emailButton = (Button) findViewById(R.id.btn_appointmentsummary_email);
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						apptSummaryList = dataEngine.getAppointmentSummary();

						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (apptSummaryList != null) {
							AppointmentSummaryBaseAdapter appointmentAdapter = new AppointmentSummaryBaseAdapter(
									CalendarEvents.this, apptSummaryList);

							ListView listapptsummary = (ListView) findViewById(R.id.list_appointment_summary);
							listapptsummary.setAdapter(appointmentAdapter);
							appointmentAdapter.notifyDataSetChanged();
                           
						}
						else
						{
							  emailButtonSetting(emailButton,0);
						}// End of if

					};
				}.execute();
				
			
				addAppointmentButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_appointment, null));
						// stateBackButtonString = "AppointmentSummary";
						new AddAppointment(CalendarEvents.this)
								.createAddAppointmentLayout();
						stateBackButtonString = "AppointmentSummary";
					}
				});

				
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (apptSummaryList != null) {
							for (AppointmentSummaryEntity obj : apptSummaryList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.eventDate) + "\n";
								listString += "Notes :-" + obj.description
										+ "\n";
								listString += "Recurring :-" + obj.recurring
										+ "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"Appointment Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else if (layoutNameString.equals("CompTimeSummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.comp_time_summary, null));
			stateBackButtonString = "CalendarEvents";

			// CompTimeEntity objCompTime=new CompTimeEntity();
			// BaseAdapterCompTime comptimeAdapter = new
			// BaseAdapterCompTime(getApplicationContext(),objCompTime.getCompTimeSummary());
			// ListView listCompTime;
			// listCompTime=(ListView)findViewById(R.id.list_comptimesummary);
			// listCompTime.setAdapter(comptimeAdapter);
			
			
			
			final Button emailButton = (Button) findViewById(R.id.btn_comptimesummary_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						compTimeArrayList = dataEngine.getCompTime();

						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);

						try {
							
							if(compTimeArrayList==null)
							{
								emailButtonSetting(emailButton, 0);
							}
							BaseAdapterCompTime compTimeAdapter = new BaseAdapterCompTime(
									CalendarEvents.this, compTimeArrayList);

							ListView compTimeList = (ListView) findViewById(R.id.list_comptimesummary);

							compTimeList.setAdapter(compTimeAdapter);

							compTimeAdapter.notifyDataSetChanged();
							balance = 0;
						
							comptimeEarned=comptimeTaken=0;
							for (CompTimeEntity objCompTimeEntity : compTimeArrayList) {
								
								if(objCompTimeEntity.eventType.equals("0"))
								{
									comptimeEarned+=Integer
									
									.valueOf(objCompTimeEntity.hours);
								}
								else{
										comptimeTaken+=Integer
									
									.valueOf(objCompTimeEntity.hours);
								}
									
								
							
							}
							balance=comptimeEarned-comptimeTaken;
							TextView balanceTextView = (TextView) findViewById(R.id.textview_comptimesummary_balancevalue);
							balanceTextView.setText(String.valueOf(balance));
						} catch (Exception e) {
							System.out.println(">>>Exception " + e
									+ "  >>> Message : " + e.getMessage());
						}

					};
				}.execute();

				Button addCompTime = (Button) findViewById(R.id.btn_comptimesummary_addcomptime);
				addCompTime.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_comp_time, null));
						// stateBackButtonString = "CompTimeSummary";
						new AddCompTime(CalendarEvents.this,comptimeEarned,comptimeTaken)
								.createAddCompTimeLayout();
						stateBackButtonString = "CompTimeSummary";
					}
				});

				
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (compTimeArrayList != null) {
							for (CompTimeEntity obj : compTimeArrayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.eventDate) + "\n";
								listString += "Type :-" + obj.eventType + "\n";
								listString += "Hour :-" + obj.hours + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"CompTime Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});

			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}

		} else if (layoutNameString.equals("KellyDaySummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.kelly_day_summary, null));
			stateBackButtonString = "CalendarEvents";
			// KellyDayEntity objKellyDayEntity;
			// objKellyDayEntity=new KellyDayEntity();
			final Button emailButton = (Button) findViewById(R.id.btn_kellydaysummary_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						kellyDayList = dataEngine.getKellyDay();
						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);

						try {
							if(kellyDayList==null)
							{
								emailButtonSetting(emailButton, 0);
							}
							KellyDayBaseAdapter kellydayAdapter = new KellyDayBaseAdapter(
									CalendarEvents.this, kellyDayList);
							ListView listkellydaysummary = (ListView) findViewById(R.id.list_kellyday_summary);
							listkellydaysummary.setAdapter(kellydayAdapter);
							kellydayAdapter.notifyDataSetChanged();

						} catch (Exception e) {
							System.out.println(">>>Exception " + e
									+ "  >>> Message : " + e.getMessage());
						}
					};
				}.execute();

				Button addKellyDayButton = (Button) findViewById(R.id.btn_kellydaysummary_addkellyday);
				addKellyDayButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						new AddKellyDay(CalendarEvents.this)
								.createAddKellyDayLayout();
						stateBackButtonString = "KellyDaySummary";
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_kelly_day, null));
						// stateBackButtonString = "KellyDaySummary";
					}
				});

				
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (kellyDayList != null) {
							for (KellyDayEntity obj : kellyDayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.date) + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"KellyDay Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else if (layoutNameString.equals("OverTimeSummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.over_time_summary, null));
			stateBackButtonString = "CalendarEvents";
			// OverTimeEntity objOverTime=new OverTimeEntity();
			// BaseAdapterOverTimeSummary overtimeAdapter=new
			// BaseAdapterOverTimeSummary(getApplicationContext(),
			// objOverTime.getOverTimeSummary());
			// ListView listOverTime=(ListView)
			// findViewById(R.id.list_overtimesummary);
			// listOverTime.setAdapter(overtimeAdapter);
			final Button emailButton = (Button) findViewById(R.id.btn_overtiemsummary_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						overTimeEntityArrayList = dataEngine.getOverTime();
						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);

						if (overTimeEntityArrayList != null) {
							BaseAdapterOverTimeSummary overTimeAdapter = new BaseAdapterOverTimeSummary(
									CalendarEvents.this,
									overTimeEntityArrayList);

							ListView overTimeList = (ListView) findViewById(R.id.list_overtimesummary);

							overTimeList.setAdapter(overTimeAdapter);

							overTimeAdapter.notifyDataSetChanged();

						}// End of if
						else
						{
							emailButtonSetting(emailButton, 0);
						}
					};
				}.execute();

				Button addOverTimeButton = (Button) findViewById(R.id.btn_overtimesummary_addovertime);
				addOverTimeButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_overtime, null));
						// stateBackButtonString = "OverTimeSummary";
						new AddOvertime(CalendarEvents.this)
								.createAddOvertimeLayout();
						stateBackButtonString = "OverTimeSummary";
					}
				});

			//	Button emailButton = (Button) findViewById(R.id.btn_overtiemsummary_email);
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (overTimeEntityArrayList != null) {
							for (OverTimeEntity obj : overTimeEntityArrayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.date) + "\n";
								listString += "Assignment :-" + obj.assignment
										+ "\n";
								listString += "Hour :-" + obj.hours + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"OverTime Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});

			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else if (layoutNameString.equals("SickTimeSummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.sick_time_summary, null));
			stateBackButtonString = "CalendarEvents";
			final Button emailButton = (Button) findViewById(R.id.btn_sicktimesummary_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						sickTimeEntityArrayList = dataEngine.getSickTime();

						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (sickTimeEntityArrayList != null) {
							BaseAdapterSickTime sickTimeAdapter = new BaseAdapterSickTime(
									CalendarEvents.this,
									sickTimeEntityArrayList);

							ListView sickTimeList = (ListView) findViewById(R.id.list_sicktimesummaryview);

							sickTimeList.setAdapter(sickTimeAdapter);

							sickTimeAdapter.notifyDataSetChanged();

						}
						else
						{
							emailButtonSetting(emailButton, 0);
						}
						// End of if
					};
				}.execute();

				Button addSickTimeButton = (Button) findViewById(R.id.btn_sicktimesummary_addsicktime);
				addSickTimeButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_sick_time, null));
						// stateBackButtonString = "SickTimeSummary";
						new AddSickTime(CalendarEvents.this)
								.createAddSickTimeLayout();
						stateBackButtonString = "SickTimeSummary";
					}
				});

			//	Button emailButton = (Button) findViewById(R.id.btn_sicktimesummary_email);
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (sickTimeEntityArrayList != null) {
							for (SickTimeEntity obj : sickTimeEntityArrayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.date) + "\n";
								listString += "Notes :-" + obj.notes + "\n";
								listString += "Hour :-" + obj.hours + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"SickTime Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else if (layoutNameString.equals("TradeTimeSummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.trade_time_summary, null));
			stateBackButtonString = "CalendarEvents";
			//final Button emailButton = (Button) findViewById(R.id.btn_tradetimearchive_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						tradeTimeEntityArrayList = dataEngine.getTradeTime();

						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (tradeTimeEntityArrayList != null) {
							BaseAdapterTradeTimeSummary tradeTimeEntityAdapter = new BaseAdapterTradeTimeSummary(
									CalendarEvents.this,
									tradeTimeEntityArrayList);

							ListView tradeTimeList = (ListView) findViewById(R.id.list_tradetimesummary);

							tradeTimeList.setAdapter(tradeTimeEntityAdapter);

							tradeTimeEntityAdapter.notifyDataSetChanged();

						}
//						else
//						{
//							emailButtonSetting(emailButton, 0);
//						}
					};
				}.execute();

				Button addTradeTime = (Button) findViewById(R.id.btn_tradetimesummary_addtradetime);
				addTradeTime.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_trade_time, null));
						// stateBackButtonString = "TradeTimeSummary";
						new AddTradeTime(CalendarEvents.this)
								.createAddTradeTimeLayout();
						stateBackButtonString = "TradeTimeSummary";
					}
				});

				Button viewArchiveButton = (Button) findViewById(R.id.btn_tradetimesummary_viewarchive);
				viewArchiveButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						framelayout.removeAllViews();
						framelayout.addView(getLayoutInflater().inflate(
								R.layout.trade_time_archive, null));
						stateBackButtonString = "TradeTimeSummary";
						final Button emailButton;
						emailButton = (Button) findViewById(R.id.btn_tradetimearchive_email);
						
						try {
							new BackgroundNetwork(CalendarEvents.this) {
								protected Void doInBackground(Void[] params) {
									DataEngine dataEngine = new DataEngine(
											CalendarEvents.this);
									tradeTimeEntityArrayList = dataEngine
											.getTradeTimeArchive();

									return null;
								};

								protected void onPostExecute(Void result) {
									super.onPostExecute(result);
									try
									{
										if (tradeTimeEntityArrayList != null) {
										// BaseAdapterTradeTimeArchive
										// tradeTimeEntityAdapter = new
										// BaseAdapterTradeTimeArchive(
										// CalendarEvents.this,
										// tradeTimeEntityArrayList);
											BaseAdapterTradeTimeArchive adapter = new BaseAdapterTradeTimeArchive(
													CalendarEvents.this,
													tradeTimeEntityArrayList);
											ListView tradeTimeList = (ListView) findViewById(R.id.list_tradetimearchive);

											tradeTimeList.setAdapter(adapter);

											adapter.notifyDataSetChanged();

										}
										else{
											emailButtonSetting(emailButton, 0);
										}
									
									}
									catch(Exception e){
										System.out.println("<<<Exception>>>"+e.toString()+"<<<>>>"+e.getMessage());
									}
								};
							}.execute();
							
							emailButton.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									String listString = "";
									for (TradeTimeEntity obj : tradeTimeEntityArrayList) {
										listString += "Name :- " + obj.name + "\n";	
									listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.date) + "\n";
									listString += "-----------------" + "\n";
								}
								Intent intent = new Intent(Intent.ACTION_SEND);
								intent.setType("plain/text");
								intent.putExtra(
										Intent.EXTRA_EMAIL,
										new String[] { ""
												+ LoginActivity.userNameString + "" });
								intent.putExtra(Intent.EXTRA_SUBJECT,
										"KellyDay Summary");
								intent.putExtra(Intent.EXTRA_TEXT, listString);
								startActivity(Intent.createChooser(intent, ""));
								}
							});
						} catch (Exception e) {
							System.out.println("<<<Exception>>>" + e.toString()
									+ "<<>>" + e.getMessage());
						}
						
						
					}
				});

				Button emailButton = (Button) findViewById(R.id.btn_tradetimearchive_email);
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (tradeTimeEntityArrayList != null) {
							for (TradeTimeEntity obj : tradeTimeEntityArrayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Name :- " + obj.name + "\n";
								listString += "Hour :-" + obj.hours + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"TradeTime Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else if (layoutNameString.equals("VacationTimeSummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.vacation_time_summary, null));
			stateBackButtonString = "CalendarEvents";
			final Button emailButton = (Button) findViewById(R.id.btn_vacationtimesummary_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						vacationEntityArrayList = dataEngine.getVacationTime();

						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (vacationEntityArrayList != null) {
							BaseAdapterVacationTime vacationTimeEntityAdapter = new BaseAdapterVacationTime(
									CalendarEvents.this,
									vacationEntityArrayList);

							ListView VacationTimeList = (ListView) findViewById(R.id.list_vacationtimesummary);

							VacationTimeList
									.setAdapter(vacationTimeEntityAdapter);

							vacationTimeEntityAdapter.notifyDataSetChanged();

						}
						else
						{
								emailButtonSetting(emailButton, 0);
						}
					};
				}.execute();

				Button addVacationTimeButton = (Button) findViewById(R.id.btn_vacationtimesummary_addvacationtime);
				addVacationTimeButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_vacation_time, null));
						// stateBackButtonString = "VacationTimeSummary";
						new AddVacationTime(CalendarEvents.this)
								.createAddVacationTimeLayout();
						stateBackButtonString = "VacationTimeSummary";
					}
				});

				//Button emailButton = (Button) findViewById(R.id.btn_vacationtimesummary_email);
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (vacationEntityArrayList != null) {
							for (VacationTimeEntity obj : vacationEntityArrayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "Start :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.startDate)
										+ "\n";
								listString += "End :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.endDate) + "\n";
								listString += "Notes :-" + obj.note + "\n";
								listString += "Hour :-" + obj.hour + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"VacationTime Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});

			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else if (layoutNameString.equals("WorkersCompSummary")) {
			framelayout.removeAllViews();
			framelayout.addView(getLayoutInflater().inflate(
					R.layout.workers_comp_summary, null));
			stateBackButtonString = "CalendarEvents";
			final Button emailButton = (Button) findViewById(R.id.btn_workerscompsummary_email);
			try {
				new BackgroundNetwork(CalendarEvents.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(
								CalendarEvents.this);
						workersCompArrayList = dataEngine.getWorkersComp();

						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (workersCompArrayList != null) {
							BaseAdapterWorkersComp workersCompAdapter = new BaseAdapterWorkersComp(
									CalendarEvents.this, workersCompArrayList);

							ListView WorkersCompList = (ListView) findViewById(R.id.list_workerscompsummary);

							WorkersCompList.setAdapter(workersCompAdapter);

							workersCompAdapter.notifyDataSetChanged();

						}
						else
						{
							emailButtonSetting(emailButton, 0);
						}

					};
				}.execute();

				Button addWorkersCompButton = (Button) findViewById(R.id.btn_workerscompsummary_addworkercomp);
				addWorkersCompButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// framelayout.removeAllViews();
						// framelayout.addView(getLayoutInflater().inflate(
						// R.layout.add_workers_group, null));
						// stateBackButtonString = "WorkersCompSummary";
						new AddWorkersComp(CalendarEvents.this)
								.createAddWorkersCompLayout();
						stateBackButtonString = "WorkersCompSummary";
					}
				});

			//	Button emailButton = (Button) findViewById(R.id.btn_workerscompsummary_email);
				emailButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String listString = "";
						int i = 1;
						if (workersCompArrayList != null) {
							for (WorkersCompEntity obj : workersCompArrayList) {
//								listString += "\n" + "Event " + (i++) + "\n";
								listString += "InjuryDate :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.injuryDate)
										+ "\n";
								listString += "ReturnDate :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.returnDate)
										+ "\n";
								listString += "Notes :-" + obj.notes + "\n";
								listString += "-----------------" + "\n";
							}
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("plain/text");
							intent.putExtra(
									Intent.EXTRA_EMAIL,
									new String[] { ""
											+ LoginActivity.userNameString + "" });
							intent.putExtra(Intent.EXTRA_SUBJECT,
									"WorkersComp Summary");
							intent.putExtra(Intent.EXTRA_TEXT, listString);
							startActivity(Intent.createChooser(intent, ""));
						}
					}
				});

			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		} else {
			createCalendarEventsLayout();
		}
	}

	// public void createEditLayouts(String layoutString) {
	// try {
	// this.framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
	// this.framelayout.removeAllViews();
	// this.framelayout.addView(getLayoutInflater().inflate(
	// R.layout.edit_appointment, null));
	// stateBackButtonString = "AppointmentSummary";
	// } catch (Exception ex) {
	// System.out.print(">>>>>FrameLayout Error>>>" + ex.toString()
	// + "<<<Message>>" + ex.getMessage());
	// ex.getMessage();
	// }
	// }
}

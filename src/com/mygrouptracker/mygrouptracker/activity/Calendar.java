package com.mygrouptracker.mygrouptracker.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.adapter.AppointmentSummaryBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.CalendarAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.data.RestfulService;
import com.mygrouptracker.mygrouptracker.entity.AllEvents;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.HolidayEntity;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.PaydayEntity;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Calendar extends BaseTabTitleActivity {

	// Shift shiftObj;
	// Button backButton;
	public GregorianCalendar month, itemmonth, tempDate;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
									// public ArrayList<VacationTimeEntity>
									// objArrayList=new
									// ArrayList<VacationTimeEntity>();

	public HashMap<String, AllEvents> hashMap = new HashMap();

	public ArrayList<String> allEventsKeysArratList = new ArrayList<String>();
	public ArrayList<AllEvents> allEventsArratList = new ArrayList<AllEvents>();

	public AllEvents objAllEvents;
	ArrayList<AppointmentSummaryEntity> apptSummaryArrayList;
	ArrayList<CompTimeEntity> compTimeArrayList;
	ArrayList<KellyDayEntity> kellyDayList;
	ArrayList<OverTimeEntity> overTimeEntityArrayList;
	ArrayList<SickTimeEntity> sickTimeEntityArrayList;
	ArrayList<TradeTimeEntity> tradeTimeEntityArrayList;
	ArrayList<VacationTimeEntity> vacationEntityArrayList;
	ArrayList<WorkersCompEntity> workersCompArrayList;
	ArrayList<PaydayEntity> paydayArrayList;
	ArrayList<HolidayEntity> holidayArrayList;

	int numOfDays;
	String startDate;
	int currentYear;
	int currentMonth;
	int color[];
	int totalCount;
	Shift shiftObj;
	boolean isCalendarOnCreate;

	SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.calendar_activity);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		// setContentView(R.layout.base_layout);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.window_title);

		FrameLayout framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.calendar_activity, null));

		// getAllEvents();
		// LinearLayout
		// linearLayout=(LinearLayout)findViewById(R.id.container_layout);
		// linearLayout.addView(getLayoutInflater().inflate(R.layout.calendar_activity,
		// null));
		// onResume();
		isCalendarOnCreate = true;
		shiftObj = new Shift();
		shiftObj = getShiftColor(shiftObj);
		getArrayOfColor(shiftObj);

		// int temp = 6 % i; // to check how many days not having color code
		//
		// // making the empty days to fill color with start day color
		// for (int j = 6 - temp, k = 0; j < color.length; j++, k++) {
		// color[j] = color[k];
		// if(k >= color.length){
		// k = 0;
		// }
		// }

		Locale.setDefault(Locale.US);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		currentYear = month.get(GregorianCalendar.YEAR);
		System.out.println("year: " + currentYear);

		String tempYear = String.valueOf(currentYear);

		currentMonth = month.get(GregorianCalendar.MONTH);

		String tempMonth = String.valueOf(currentMonth + 1);

		if (tempMonth.length() == 1)
			startDate = tempYear + "-0" + tempMonth + "-01";
		else
			startDate = tempYear + "-" + tempMonth + "-01";

		numOfDays = month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		System.out.println("days: " + numOfDays);

		SyncingActivity.startDate = startDate;
		SyncingActivity.numOfDays = numOfDays;

		// getAllEvents();
		loginPreferences = getSharedPreferences("calendar", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();

		String tmpMonth = String.valueOf(month.get(GregorianCalendar.MONTH));
		String tmpYear = String.valueOf(month.get(GregorianCalendar.YEAR));
		int currentMonthCountStart = 0;
		if (loginPreferences.getBoolean(tmpMonth + tmpYear + "boolean", false)) {
			currentMonthCountStart = loginPreferences.getInt(
					tmpMonth + tmpYear, 0);
		}
		items = new ArrayList<String>();
		adapter = new CalendarAdapter(this, month, shiftObj, color, totalCount,
				currentMonthCountStart);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) findViewById(R.id.calendartitle);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);
		// getHoliday();
		// backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CalendarAdapter.dayCount = 1;
				setPreviousMonth();
				getAllEvents();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CalendarAdapter.dayCount = 1;
				setNextMonth();
				getAllEvents();
				refreshCalendar();
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				try {
					((CalendarAdapter) parent.getAdapter()).setSelected(v);
					String selectedGridDate = CalendarAdapter.dayString
							.get(position);
					String[] separatedTime = selectedGridDate.split("-");
					String gridvalueString = separatedTime[2].replaceFirst(
							"^0*", "");// taking last part of date. ie; 2 from
										// 2012-12-02.
					int gridvalue = Integer.parseInt(gridvalueString);
					// navigate to next or previous month on clicking offdays.
					if ((gridvalue > 10) && (position < 8)) {
						setPreviousMonth();
						refreshCalendar();
					} else if ((gridvalue < 7) && (position > 28)) {
						setNextMonth();
						refreshCalendar();
					}
					((CalendarAdapter) parent.getAdapter()).setSelected(v);

					// showToast(selectedGridDate);
					showList(selectedGridDate);
				} catch (Exception e) {
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				}
			}
		});
	}

	public Shift getShiftColor(Shift shiftObj) {

		SharedPreferences sharedPreferences;
		// SharedPreferences.Editor sharedPrefsEditor;

		sharedPreferences = getSharedPreferences("shift", MODE_PRIVATE);
		// sharedPrefsEditor = sharedPreferences.edit();
		try {
			String tempDateString = sharedPreferences
					.getString("startDate", "");

			shiftObj.startDate = MgtDateFormat
					.dateFormatYYMMDDCalendar(tempDateString);
			shiftObj.shiftName = sharedPreferences.getString("shiftName", "");
			shiftObj.day = new ArrayList<String>();
			shiftObj.ColorCode = new ArrayList<String>();
			shiftObj.ColorName = new ArrayList<String>();

			for (int i = 1; i <= 18; i++) {
				if (i < 10) {
					String day = "day0" + i;
					shiftObj.day.add(sharedPreferences.getString(day, ""));
					String color = "day0" + i + "ColorName";
					shiftObj.ColorName.add(sharedPreferences.getString(color,
							""));
					String code = "day0" + i + "ColorCode";
					shiftObj.ColorCode.add(sharedPreferences
							.getString(code, ""));
				} else {
					shiftObj.day
							.add(sharedPreferences.getString("day" + i, ""));
					shiftObj.ColorName.add(sharedPreferences.getString("day"
							+ i + "ColorName", ""));
					shiftObj.ColorCode.add(sharedPreferences.getString("day"
							+ i + "ColorCode", ""));
				}
			}
			// shiftObj.day01 = sharedPreferences.getString("day01", "");
			// shiftObj.day01ColorCode = sharedPreferences.getString(
			// "day01ColorCode", "");
			// shiftObj.day01ColorName = sharedPreferences.getString(
			// "day01ColorName", "");
			// shiftObj.day02 = sharedPreferences.getString("day02", "");
			// shiftObj.day02ColorCode = sharedPreferences.getString(
			// "day02ColorCode", "");
			// shiftObj.day02ColorName = sharedPreferences.getString(
			// "day02ColorName", "");
			// shiftObj.day03 = sharedPreferences.getString("day03", "");
			// shiftObj.day03ColorCode = sharedPreferences.getString(
			// "day03ColorCode", "");
			// shiftObj.day03ColorName = sharedPreferences.getString(
			// "day03ColorName", "");
			// shiftObj.day04 = sharedPreferences.getString("day04", "");
			// shiftObj.day04ColorCode = sharedPreferences.getString(
			// "day04ColorCode", "");
			// shiftObj.day04ColorName = sharedPreferences.getString(
			// "day04ColorName", "");
			// shiftObj.day05 = sharedPreferences.getString("day05", "");
			// shiftObj.day05ColorCode = sharedPreferences.getString(
			// "day05ColorCode", "");
			// shiftObj.day05ColorName = sharedPreferences.getString(
			// "day05ColorName", "");
			// shiftObj.day06 = sharedPreferences.getString("day06", "");
			// shiftObj.day06ColorCode = sharedPreferences.getString(
			// "day06ColorCode", "");
			// shiftObj.day06ColorName = sharedPreferences.getString(
			// "day06ColorName", "");

			System.out.println(">>>StartDate>>>" + shiftObj.startDate);
			System.out.println(">>>shiftName>>>" + shiftObj.shiftName);
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}

		return shiftObj;

	}

	public void getArrayOfColor(Shift shiftObj) {
		int i = 0;
		color = new int[shiftObj.ColorCode.size()];
		try {
			for (int j = 0; j < 18; j++) {
				try {
					String temp = shiftObj.ColorCode.get(j);
					temp = temp.replace("NULL", "0");
					// if (!shiftObj.ColorCode.get(j).equals("0") ||
					// !shiftObj.ColorCode.get(j).equals("NULL") ||
					// !shiftObj.ColorCode.get(j).equals(null) ||
					// !shiftObj.ColorCode.get(j).equals("") ) {
					if (!temp.equals("0")) {
						if (!temp.equals("")) {
							if (!temp.equals(null)) {
								color[i++] = getColor(shiftObj.ColorCode.get(j));
							}
						}

					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			// if (!shiftObj.day01ColorCode.equals("0")) {
			// color[i++] = getColor(shiftObj.day01ColorCode);
			// }
			//
			// if (!shiftObj.day02ColorCode.equals("0")) {
			// color[i++] = getColor(shiftObj.day02ColorCode);
			// }
			//
			// if (!shiftObj.day03ColorCode.equals("0")) {
			// color[i++] = getColor(shiftObj.day03ColorCode);
			// }
			//
			// if (!shiftObj.day04ColorCode.equals("0")) {
			// color[i++] = getColor(shiftObj.day04ColorCode);
			// }
			//
			// if (!shiftObj.day05ColorCode.equals("0")) {
			// color[i++] = getColor(shiftObj.day05ColorCode);
			// }
			//
			// if (!shiftObj.day06ColorCode.equals("0")) {
			// color[i++] = getColor(shiftObj.day06ColorCode);
			// }
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}

		totalCount = i;
	}

	public int getColor(String colorName) {
		colorName = colorName.toLowerCase(Locale.US);
		
//		ShapeDrawable sd1 = new ShapeDrawable(new RectShape());
//        sd1.setst
//        sd1.getPaint().setStyle(Style.STROKE);
//        sd1.getPaint().setStrokeWidth(CommonUtilities.stroke);
//        sd1.setPadding(15, 10, 15, 10);
//
//        sd1.getPaint().setPathEffect(
//                new CornerPathEffect(CommonUtilities.corner));
//        ln_back.setBackgroundDrawable(sd1);
        
		if (colorName.equalsIgnoreCase("#c93b3a")) {
			return R.drawable.selector_cell_red;
		}

		if (colorName.equalsIgnoreCase("#6ba06a")) {
			return R.drawable.selector_cell_green;			
		}

		if (colorName.equalsIgnoreCase("#4c4c4c")) {
			return R.drawable.selector_cell_gray;
		}

		if (colorName.equalsIgnoreCase("#8585ff")) {
			return R.drawable.selector_cell_blue;
		}

		if (colorName.equalsIgnoreCase("#33d6d6")) {
			return R.drawable.selector_cell_cyan;
		}

		if (colorName.equalsIgnoreCase("#367199")) {
			return R.drawable.selector_cell_brown;
		}
		
		if (colorName.equalsIgnoreCase("#1C86EE")) {
			return R.drawable.selector_cell_dodgerblue;
		}
		
		if (colorName.equalsIgnoreCase("#9acd32")) {
			return R.drawable.selector_cell_yellow_green;
		}
		
		if (colorName.equalsIgnoreCase("#3D9140")) {
			return R.drawable.selector_cell_cobalt_green;
		}
		
		if (colorName.equalsIgnoreCase("#d02090")) {
			return R.drawable.selector_cell_violet_red;
		}
				
		if (colorName.equalsIgnoreCase("#4A708B")) {
			return R.drawable.selector_cell_sky_blue;
		}
		
		if (colorName.equalsIgnoreCase("#CDAD00")) {
			return R.drawable.selector_cell_gold;
		}
		
		if (colorName.equalsIgnoreCase("#FF8000")) {
			return R.drawable.selector_cell_orange;
		}
		
		if (colorName.equalsIgnoreCase("#EE4000")) {
			return R.drawable.selector_cell_orange_red;
		}	
		
		if (colorName.equalsIgnoreCase("#7171C6")) {
			return R.drawable.selector_cell_slate_blue;
		}		
		
		if (colorName.equalsIgnoreCase("#8E388E")) {
			return R.drawable.selector_cell_beet;
		}
		
		if (colorName.equalsIgnoreCase("#800000")) {
			return R.drawable.selector_cell_maroon;
		}
		
		if (colorName.equalsIgnoreCase("#308014")) {
			return R.drawable.selector_cell_sap_green;
		}

		if (colorName.equalsIgnoreCase("#458B74")) {
			return R.drawable.selector_cell_aqua_marine;
		}
		
		if (colorName.equalsIgnoreCase("#0000ff")) {
			return R.drawable.selector_cell_aqua_marine;
		}
		
		if (colorName.equalsIgnoreCase("#00c78c")) {
			return R.drawable.selector_cell_dark_cyan;
		}

		if (colorName.equalsIgnoreCase("#ffff00")) {
			return R.drawable.selector_cell_yellow_green;
		}
		
		return R.color.white;
	}

	public void showList(String dateString) {
		if (hashMap.containsKey(dateString)) {
			AllEvents obj = hashMap.get(dateString);
			Intent intent = new Intent(Calendar.this, ListAllevents.class);
			intent.putExtra("DateEvents", obj);
			// // intent.putExtra("DateEvents", obj);
			// Bundle bundleObject = new Bundle();
			// bundleObject.putParcelable("DateEvents", obj);
			// intent.putExtras(bundleObject);
			startActivity(intent);
		}
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}
		currentYear = month.get(GregorianCalendar.YEAR);
		System.out.println("year: " + currentYear);

		String tempYear = String.valueOf(currentYear);

		currentMonth = month.get(GregorianCalendar.MONTH);

		String tempMonth = String.valueOf(currentMonth + 1);

		if (tempMonth.length() == 1)
			startDate = tempYear + "-0" + tempMonth + "-01";
		else
			startDate = tempYear + "-" + tempMonth + "-01";

		numOfDays = month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		System.out.println("days: " + numOfDays);

		SyncingActivity.startDate = startDate;
		SyncingActivity.numOfDays = numOfDays;
	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

		currentYear = month.get(GregorianCalendar.YEAR);
		System.out.println("year: " + currentYear);

		String tempYear = String.valueOf(currentYear);

		currentMonth = month.get(GregorianCalendar.MONTH);

		String tempMonth = String.valueOf(currentMonth + 1);

		if (tempMonth.length() == 1)
			startDate = tempYear + "-0" + tempMonth + "-01";
		else
			startDate = tempYear + "-" + tempMonth + "-01";

		numOfDays = month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		System.out.println("days: " + numOfDays);

		SyncingActivity.startDate = startDate;
		SyncingActivity.numOfDays = numOfDays;

	}

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	}

	public void getPayday() {
		try {
			new BackgroundNetwork(Calendar.this) {
				protected Void doInBackground(Void[] params) {
					DataEngine dataEngine = new DataEngine(Calendar.this);
					paydayArrayList = dataEngine.getPaydays();

					if (objAllEvents != null) {
						apptSummaryArrayList = objAllEvents.appointmentArrayList;
						compTimeArrayList = objAllEvents.compTimeArrayList;
						kellyDayList = objAllEvents.kellyDayArrayList;
						overTimeEntityArrayList = objAllEvents.overTimeArrayList;
						sickTimeEntityArrayList = objAllEvents.sickTimeArrayList;
						tradeTimeEntityArrayList = objAllEvents.tradeTimeArrayList;
						vacationEntityArrayList = objAllEvents.vacationTimeArrayList;
						workersCompArrayList = objAllEvents.workersCompArrayList;
						setAllevents();
					}
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					try {
						refreshCalendar();
					} catch (Exception er) {
						System.out.println(">>>Exception " + er
								+ "  >>> Message : " + er.getMessage());
					}
				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	public void getHoliday() {
		try {
			new BackgroundNetwork(Calendar.this) {
				protected Void doInBackground(Void[] params) {
					DataEngine dataEngine = new DataEngine(Calendar.this);
					holidayArrayList = dataEngine.getHolidays();

					return null;
				};

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					getPayday();

				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.calendartitle);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		try {
			title.setText(android.text.format.DateFormat.format("MMMM yyyy",
					month));
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	@Override
	protected void onResume() {
		try {
			if (!isCalendarOnCreate) {
				Shift shiftObj = new Shift();
				shiftObj = getShiftColor(shiftObj);
				getArrayOfColor(shiftObj);
			}
			isCalendarOnCreate = false;
			getAllEvents();
		} catch (Exception er) {

		}
		super.onResume();
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;
			// items.add("2014-04-17");
			// items.add("2014-04-16");
			// items.add("2014-04-13");
			// for(int i=0;i<objAllEvents.();i++)
			// {
			// itemvalue=MgtDateFormat.dateFormatYYMMDDCalendar(objArrayList.get(i).startDate);
			// items.add(itemvalue);
			// }
			String dateString = startDate;
			for (int i = 1; i <= numOfDays; i++) {
				if (hashMap.containsKey(dateString)) {
					items.add(dateString);
				}
				dateString = MgtDateFormat.incrementDate(dateString);
			}

			// for (int i = 0; i < 7; i++) {
			// itemvalue = df.format(itemmonth.getTime());
			// itemmonth.add(GregorianCalendar.DATE, 1);
			// items.add("2014-04-17");
			// items.add("2014-04-16");
			// items.add("2014-04-13");
			// // items.add("2012-09-12");
			// // items.add("2012-10-07");
			// // items.add("2012-10-15");
			// // items.add("2012-10-20");
			// // items.add("2012-11-30");
			// // items.add("2012-11-28");
			// }

			adapter.setItems(items);
			adapter.setAllEvents(hashMap);
			adapter.notifyDataSetChanged();
		}
	};

	public void getAllEvents() {
		CalendarAdapter.dayCount = 1;
		try {
			new BackgroundNetwork(Calendar.this) {
				protected Void doInBackground(Void[] params) {

					// List<NameValuePair> nameValuePairs = new
					// ArrayList<NameValuePair>();
					// nameValuePairs.add(new BasicNameValuePair("StartDate",
					// "2014-04-01"));
					// nameValuePairs.add(new BasicNameValuePair("Days", "15"));
					try {
						DataEngine dataEngine = new DataEngine(Calendar.this);

						// startDate=MgtDateFormat.dateFormatMMddYYYYCalendar(startDate);
						objAllEvents = dataEngine
								.getAllEvents("DateOfEvents?StartDate="
										+ startDate + "&Days="
										+ String.valueOf(numOfDays));
						// adapter.setAllEvents(objAllEvents);

						/*
						 * System.out.println(objAllEvents.toString());
						 * Log.e(">>AlleventObj>>", objAllEvents.toString());
						 */

					} catch (Exception er) {
						System.out.println(">>>Exception " + er
								+ "  >>> Message : " + er.getMessage());

					}
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					try {
						getHoliday();

						// if (objAllEvents != null) {
						// apptSummaryArrayList =
						// objAllEvents.appointmentArrayList;
						// compTimeArrayList = objAllEvents.compTimeArrayList;
						// kellyDayList = objAllEvents.kellyDayArrayList;
						// overTimeEntityArrayList =
						// objAllEvents.overTimeArrayList;
						// sickTimeEntityArrayList =
						// objAllEvents.sickTimeArrayList;
						// tradeTimeEntityArrayList =
						// objAllEvents.tradeTimeArrayList;
						// vacationEntityArrayList =
						// objAllEvents.vacationTimeArrayList;
						// workersCompArrayList =
						// objAllEvents.workersCompArrayList;
						//
						// setAllevents();
						// }
						// refreshCalendar();
					} catch (Exception er) {
						System.out.println(">>>Exception " + er
								+ "  >>> Message : " + er.getMessage());
					}
				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	public void setAllevents() {
		// String dateStr=MgtDateFormat.getDate(startDate);
		// String month=MgtDateFormat.getMonth(startDate);
		// String year=MgtDateFormat.getYear(startDate);

		String dateString = startDate;
		int date = Integer.valueOf(MgtDateFormat.getDate(startDate));
		numOfDays = MgtDateFormat.getNumberOfDays(startDate);

		Boolean isEvent = false;

		// AppointmentSummaryEntity objAppointment;
		// CompTimeEntity objCompTimeEntity;
		// KellyDayEntity objKellyDayEntity;
		// OverTimeEntity objOverTimeEntity;
		// SickTimeEntity objSickTimeEntity;
		// TradeTimeEntity objTradeTimeEntity;
		// VacationTimeEntity objVacationTimeEntity;
		// WorkersCompEntity objWorkersCompEntity;
		hashMap.clear();

		for (int i = date; i <= numOfDays; i++) {
			ArrayList<AppointmentSummaryEntity> tempAppointmentArrayList = new ArrayList<AppointmentSummaryEntity>();
			ArrayList<CompTimeEntity> tempCompArrayList = new ArrayList<CompTimeEntity>();
			ArrayList<KellyDayEntity> tempKellyDayList = new ArrayList<KellyDayEntity>();
			ArrayList<OverTimeEntity> tempOverTimeEntityArrayList = new ArrayList<OverTimeEntity>();
			ArrayList<SickTimeEntity> tempSickTimeEntityArrayList = new ArrayList<SickTimeEntity>();
			ArrayList<TradeTimeEntity> tempTradeTimeEntityArrayList = new ArrayList<TradeTimeEntity>();
			ArrayList<VacationTimeEntity> tempVacationEntityArrayList = new ArrayList<VacationTimeEntity>();
			ArrayList<WorkersCompEntity> tempWorkersCompArrayList = new ArrayList<WorkersCompEntity>();
			ArrayList<HolidayEntity> tempHolidayEntityArrayList = new ArrayList<HolidayEntity>();
			ArrayList<PaydayEntity> tempPaydayEntityArrayList = new ArrayList<PaydayEntity>();

			AllEvents objTempAllEvents = new AllEvents();

			if (apptSummaryArrayList != null) {
				tempAppointmentArrayList.clear();
				for (int j = 0; j < apptSummaryArrayList.size(); j++) {
					if (apptSummaryArrayList.get(j).eventDate
							.contains(dateString)) {
						isEvent = true;
						// objAppointment=apptSummaryArrayList.get(j);
						tempAppointmentArrayList.add(apptSummaryArrayList
								.get(j));
						// objTempAllEvents.appointmentArrayList.add(objAppointment);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.appointmentArrayList = tempAppointmentArrayList;
			}
			if (compTimeArrayList != null) {
				tempCompArrayList.clear();
				for (int j = 0; j < compTimeArrayList.size(); j++) {

					if (compTimeArrayList.get(j).eventDate.contains(dateString)) {
						isEvent = true;
						try {
							tempCompArrayList.add(compTimeArrayList.get(j));
							// objCompTimeEntity=compTimeArrayList.get(j);
							// objTempAllEvents.compTimeArrayList.add(objCompTimeEntity);
							// hashMap.put(String.valueOf(i), objTempAllEvents);
						} catch (Exception e) {
							Log.e("<<<Exception>>>",
									e.toString() + ">>>" + e.getMessage());
						}
					}
				}
				objTempAllEvents.compTimeArrayList = tempCompArrayList;
			}
			if (kellyDayList != null) {
				tempKellyDayList.clear();
				for (int j = 0; j < kellyDayList.size(); j++) {
					if (kellyDayList.get(j).date.contains(dateString)) {
						isEvent = true;
						tempKellyDayList.add(kellyDayList.get(j));
						// objKellyDayEntity=kellyDayList.get(j);
						// objTempAllEvents.kellyDayArrayList.add(objKellyDayEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.kellyDayArrayList = tempKellyDayList;
			}
			if (overTimeEntityArrayList != null) {
				tempOverTimeEntityArrayList.clear();
				for (int j = 0; j < overTimeEntityArrayList.size(); j++) {
					if (overTimeEntityArrayList.get(j).date
							.contains(dateString)) {
						isEvent = true;
						tempOverTimeEntityArrayList.add(overTimeEntityArrayList
								.get(j));
						// objOverTimeEntity=overTimeEntityArrayList.get(j);
						// objTempAllEvents.overTimeArrayList.add(objOverTimeEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.overTimeArrayList = tempOverTimeEntityArrayList;
			}
			if (sickTimeEntityArrayList != null) {
				tempSickTimeEntityArrayList.clear();
				for (int j = 0; j < sickTimeEntityArrayList.size(); j++) {
					if (sickTimeEntityArrayList.get(j).date
							.contains(dateString)) {
						isEvent = true;
						tempSickTimeEntityArrayList.add(sickTimeEntityArrayList
								.get(j));
						// objSickTimeEntity=sickTimeEntityArrayList.get(j);
						// objTempAllEvents.sickTimeArrayList.add(objSickTimeEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.sickTimeArrayList = tempSickTimeEntityArrayList;
			}
			if (tradeTimeEntityArrayList != null) {
				tempTradeTimeEntityArrayList.clear();
				for (int j = 0; j < tradeTimeEntityArrayList.size(); j++) {
					if (tradeTimeEntityArrayList.get(j).date
							.contains(dateString)) {
						isEvent = true;
						tempTradeTimeEntityArrayList
								.add(tradeTimeEntityArrayList.get(j));
						// objTradeTimeEntity=tradeTimeEntityArrayList.get(j);
						// objTempAllEvents.tradeTimeArrayList.add(objTradeTimeEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.tradeTimeArrayList = tempTradeTimeEntityArrayList;
			}
			if (vacationEntityArrayList != null) {
				tempVacationEntityArrayList.clear();
				for (int j = 0; j < vacationEntityArrayList.size(); j++) {
					if (vacationEntityArrayList.get(j).startDate
							.contains(dateString)) {
						isEvent = true;
						tempVacationEntityArrayList.add(vacationEntityArrayList
								.get(j));
						// objVacationTimeEntity=vacationEntityArrayList.get(j);
						// objTempAllEvents.vacationTimeArrayList.add(objVacationTimeEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.vacationTimeArrayList = tempVacationEntityArrayList;
			}
			if (workersCompArrayList != null) {
				tempWorkersCompArrayList.clear();
				for (int j = 0; j < workersCompArrayList.size(); j++) {
					if (workersCompArrayList.get(j).injuryDate
							.contains(dateString)) {
						isEvent = true;
						tempWorkersCompArrayList.add(workersCompArrayList
								.get(j));
						// objWorkersCompEntity=workersCompArrayList.get(j);
						// objTempAllEvents.workersCompArrayList.add(objWorkersCompEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.workersCompArrayList = tempWorkersCompArrayList;
			}

			if (holidayArrayList != null) {
				tempHolidayEntityArrayList.clear();
				for (int j = 0; j < holidayArrayList.size(); j++) {
					if (holidayArrayList.get(j).StartDate.contains(dateString)) {
						isEvent = true;
						tempHolidayEntityArrayList.add(holidayArrayList.get(j));
						// objWorkersCompEntity=workersCompArrayList.get(j);
						// objTempAllEvents.workersCompArrayList.add(objWorkersCompEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.holidayArrayList = tempHolidayEntityArrayList;
			}
			if (paydayArrayList != null) {
				tempPaydayEntityArrayList.clear();
				for (int j = 0; j < paydayArrayList.size(); j++) {
					if (paydayArrayList.get(j).StartDate.contains(dateString)) {
						isEvent = true;
						tempPaydayEntityArrayList.add(paydayArrayList.get(j));
						// objWorkersCompEntity=workersCompArrayList.get(j);
						// objTempAllEvents.workersCompArrayList.add(objWorkersCompEntity);
						// hashMap.put(String.valueOf(i), objTempAllEvents);
					}
				}
				objTempAllEvents.paydayArrayList = tempPaydayEntityArrayList;
			}
			if (isEvent == true) {
				hashMap.put(dateString, objTempAllEvents);

				// CalendarAdapter.hashMap.put(dateString, objTempAllEvents);
				isEvent = false;
			}
			dateString = MgtDateFormat.incrementDate(dateString);
		}// End of For Loop

		// CalendarAdapter.hashMap.putAll(hashMap);
		// AllEvents obj=null;
		// for(int i=1;i<=10;i++){
		// obj=new AllEvents();
		// if(hashMap.containsKey(String.valueOf(i))){
		// obj=hashMap.get(String.valueOf(i));
		// }
		// }
	}

}

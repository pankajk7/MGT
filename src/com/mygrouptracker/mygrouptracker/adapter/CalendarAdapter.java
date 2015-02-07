package com.mygrouptracker.mygrouptracker.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.crypto.spec.PSource;

import com.google.android.gms.internal.cs;
import com.google.android.gms.internal.in;
import com.mygrouptracker.mygrouptracker.R;
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
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month;
	public GregorianCalendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	int count;
	String itemvalue, curentDateString;
	DateFormat df;
	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;
	ArrayList<AppointmentSummaryEntity> apptSummaryArrayList;
	ArrayList<CompTimeEntity> compTimeArrayList;
	ArrayList<KellyDayEntity> kellyDayList;
	ArrayList<OverTimeEntity> overTimeEntityArrayList;
	ArrayList<SickTimeEntity> sickTimeEntityArrayList;
	ArrayList<TradeTimeEntity> tradeTimeEntityArrayList;
	ArrayList<VacationTimeEntity> vacationEntityArrayList;
	ArrayList<WorkersCompEntity> workersCompArrayList;
	ArrayList<HolidayEntity> holydayArrayList;
	ArrayList<PaydayEntity> paydayArrayList;
	AppointmentSummaryEntity objAppointment;
	CompTimeEntity objCompTimeEntity;
	KellyDayEntity objKellyDayEntity;
	OverTimeEntity objOverTimeEntity;
	SickTimeEntity objSickTimeEntity;
	TradeTimeEntity objTradeTimeEntity;
	VacationTimeEntity objVacationTimeEntity;
	WorkersCompEntity objWorkersCompEntity;
	HolidayEntity objHolidayEntity;
	PaydayEntity objPaydayEntity;
	AllEvents objAllEvents;
	public static HashMap<String, AllEvents> hashMap = new HashMap();
	String dateString;
	int objectCount = 0;
	Boolean isMultiple = true;
	Shift shiftObj;
	int colorCode[];
	static int colorCount;
	int totalCount;
	public static int dayCount;
	int previousPosition;
	int previousColorCode;
	boolean previousColorFlag;
	int previousMonth;
	int previousMonthCount;
	int indexCount;
	int nextIndexValue;
	int matchIndexValue;
	String currentDateString;
	String startDateString;
	boolean isMatchedValue = false;
	int previousDayOfYear;
	int previousYear;

	SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;

	public CalendarAdapter(Context c, GregorianCalendar monthCalendar,
			Shift shiftObj, int colorCode[], int totalCount,
			int currentMonthCountStart) {
		CalendarAdapter.dayString = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		mContext = c;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();
		this.hashMap = new HashMap<String, AllEvents>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		curentDateString = df.format(selectedDate.getTime());
		this.shiftObj = shiftObj;
		this.colorCode = new int[shiftObj.ColorCode.size()];
		colorCount = 0;
		dayCount = 1;
		this.colorCode = colorCode;
		this.totalCount = totalCount;
		previousPosition = -1;
		previousMonth = month.get(GregorianCalendar.MONTH);
		previousMonthCount = currentMonthCountStart;
		// String startYear = MgtDateFormat.getYear(shiftObj.startDate);
		// String currentYear = MgtDateFormat.getYear(curentDateString);
		// if(startYear.equals(currentYear)){
		//
		// }
		nextIndexValue = 0;
		refreshDays();
	}

	public void setAllEvents(HashMap<String, AllEvents> hashMap) {
		this.hashMap = hashMap;// (HashMap<String, AllEvents>) hashMap.clone();
		// this.dateString=dateString;
	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	@Override
	public int getCount() {
		if (dayString == null) {
			;
			return 0;
		}
		return dayString.size();
	}

	@Override
	public Object getItem(int position) {
		return dayString.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean isDateAfterStartDate(int position) {
		try {

			// int green = R.drawable.selector_cell_green;
			// int red = R.drawable.selector_cell_red;
			// int gray = R.drawable.selector_cell_gray;
			// int blue = R.drawable.selector_cell_blue;
			// int cyan = R.drawable.selector_cell_cyan;
			// int silver = R.drawable.selector_cell_silver;
			// int white = R.color.Calendar_white;

			// extracting number of days in current month and incrementing date
			df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			GregorianCalendar gc = new GregorianCalendar();
			gc = (GregorianCalendar) month.clone();
			int totalDays = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			// System.out.println("\n>>>DayCount>>>" + dayCount);
			gc.set(GregorianCalendar.DAY_OF_MONTH, dayCount);
			int dayofYear = gc.get(GregorianCalendar.DAY_OF_YEAR);

			// System.out.println("\n>>>Dayofyear>>>" + dayofYear);
			indexCount = dayofYear % totalCount;
			dayCount++;
			if (dayCount > totalDays) {
				dayCount = 1;
			}
			// TODO
			// get current date and start date
			startDateString = MgtDateFormat
					.dateFormatMMddYYYYCalendar(shiftObj.startDate);
			SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = sd.parse(startDateString);

			String tempString = df.format(gc.getTime());

			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = dt.parse(tempString);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			currentDateString = sdf.format(date1);
			// Date currentDate = dt.parse(currentDateString);
			Date currentDate = sdf.parse(currentDateString);
			startDateString = MgtDateFormat
					.dateFormatMMddYYYYCalendar(shiftObj.startDate);
			startDateString = startDateString.replace("/", "-");
			int StartYear = Integer.valueOf(MgtDateFormat
					.getYearFormat(startDateString));
			currentDateString = currentDateString.replace("/", "-");
			int CurrentYear = Integer.valueOf(MgtDateFormat
					.getYearFormat(currentDateString));
			if (previousYear != CurrentYear) {
				if (CurrentYear > StartYear) {
					isMatchedValue = false;
				}

				// when coming back to start year from just next year
				if (previousYear > StartYear) {
					isMatchedValue = false;
				}
				previousYear = CurrentYear;
			}

			// int[][] maxtix = new int[6][7];

			// comparing dates
			// means if current date is less than start date
			// System.out.println(">>>Position>>>" + position
			// + "\n>>>StartDate>>>" + startDate + "\n>>>currentDate>>>"
			// + currentDate);

			if (currentDate.compareTo(startDate) < 0) {
				return false;
				// false to set white color background
			}
			// else {
			// return true;
			// maxtix[0][0] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[0][1] = maxtix[1][0] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[0][2] = maxtix[1][1] = maxtix[2][0] =
			// colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[0][3] = maxtix[1][2] = maxtix[2][1] = maxtix[3][0] =
			// colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[0][4] = maxtix[1][3] = maxtix[2][2] = maxtix[3][1] =
			// maxtix[4][0] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[0][5] = maxtix[1][4] = maxtix[2][3] = maxtix[3][2] =
			// maxtix[4][1] = maxtix[5][0] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[0][6] = maxtix[1][5] = maxtix[2][4] = maxtix[3][3] =
			// maxtix[4][2] = maxtix[5][1] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[1][6] = maxtix[2][5] = maxtix[3][4] = maxtix[4][3] =
			// maxtix[5][2] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[2][6] = maxtix[3][5] = maxtix[4][4] = maxtix[5][3] =
			// colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[3][6] = maxtix[4][5] = maxtix[5][4] =
			// colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[4][6] = maxtix[5][5] = colorCode[colorCount++];
			// checkColorcount();
			//
			// maxtix[5][6] = colorCode[colorCount++];
			// checkColorcount();
			// }
			// int row = position / 7;
			// int col = position % 7;
			// return maxtix[row][col];

		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
		// return R.color.gray;
		return true;
	}

	public void getCorrectIndex(String startDateString, String currentDateString) {
		startDateString = startDateString.replace("/", "-");
		currentDateString = currentDateString.replace("/", "-");
		// int tempStartMonth = Integer.valueOf(MgtDateFormat
		// .getMonth(startDateString));
		// int tempCurrentMonth = Integer.valueOf(MgtDateFormat
		// .getMonth(currentDateString));
		int tempStartYear = Integer.valueOf(MgtDateFormat
				.getYearFormat(startDateString));
		int tempCurrentYear = Integer.valueOf(MgtDateFormat
				.getYearFormat(currentDateString));

		// TODO
		if (tempStartYear < tempCurrentYear) {
			isMatchedValue = true;
			Calendar cal = new GregorianCalendar(tempStartYear, 1, 1);
			int maxDaysInYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR);

			// getMatchedIndex(startDateString);
			// indexCount = 0;
			// nextIndexValue = indexCount + matchIndexValue;
			// nextIndexValue = getNextYearIndexValue(maxDaysInYear);

			int numOfDays = 0;
			while (tempStartYear < tempCurrentYear) {
				Calendar calendar = new GregorianCalendar(tempStartYear, 1, 1);
				int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
				numOfDays += totalDays;
				tempStartYear++;
			}
			int tempIndex = getNextYearIndexValue(maxDaysInYear);
			getYearMatchedIndex(numOfDays, tempIndex);
		} else if (tempStartYear <= tempCurrentYear) {
			isMatchedValue = true;
			nextIndexValue = 0;
			getMatchedIndex(startDateString);
			// GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
			// .getInstance();
			// SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
			// try {
			// Date startDate = s.parse(startDateString);
			// gc.setTime(startDate);
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			//
			// isMatchedValue = true;
			// int dayOfYear = gc.get(GregorianCalendar.DAY_OF_YEAR);
			// System.out.println(">>>DayofYear>>>" + dayOfYear);
			// indexCount = dayOfYear % totalCount;
			// matchIndexValue = 0;
			// int temp = indexCount;
			// for (int i = 0; i < totalCount; i++) {
			// // check when temp matching nextIndexvalue by adding
			// // matchindexvalue
			// matchIndexValue = i;
			// temp = checkIndex(temp + matchIndexValue);
			// if (temp == nextIndexValue) {
			// break;
			// }
			// }

		}
	}

	public int getNextYearIndexValue(int maxPreviousYearDays) {
		// Incrementing to get first day of next year count in total number of
		// days
		maxPreviousYearDays++;
		return maxPreviousYearDays % totalCount;
	}

	public void getYearMatchedIndex(int totalDays, int tempIndex) {
		nextIndexValue = tempIndex;
		 totalDays += 1; // this is because its new year 1st day which we want
		// to
		// addd n get index of it
		indexCount = (totalDays) % (totalCount);
		matchIndexValue = 0;
		int temp = indexCount;
		for (int i = 0; i < totalCount; i++) {
			// check when temp matching nextIndexvalue by adding
			// matchindexvalue
			matchIndexValue = i;
			temp = checkIndex(indexCount + matchIndexValue);
			if (temp == nextIndexValue) {
				break;
			}
		}
	}

	public void getMatchedIndex(String startDateString) {

		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
				.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
		try {
			Date startDate = s.parse(startDateString);
			gc.setTime(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int dayOfYear = gc.get(GregorianCalendar.DAY_OF_YEAR);
		// System.out.println(">>>DayofYear>>>" + dayOfYear);
		indexCount = dayOfYear % totalCount;
		matchIndexValue = 0;
		int temp = indexCount;
		for (int i = 0; i < totalCount; i++) {
			// check when temp matching nextIndexvalue by adding
			// matchindexvalue
			matchIndexValue = i;
			temp = checkIndex(temp + matchIndexValue);
			if (temp == nextIndexValue) {
				break;
			}
		}
	}

	public void checkColorcount() {

		if (colorCount >= totalCount) {
			colorCount = 0;
		}

	}

	public int checkIndexCount(int count) {
		return count % totalCount;
	}

	public int checkIndex(int count) {
		if (count >= totalCount) {
			return 0;
		}
		return count;
	}

	public void checkNextIndex() {

		if (colorCount >= totalCount) {
			colorCount = 0;
		}

	}

	public int matchIndex() {
		matchIndexValue = 1;
		while (true) {
			nextIndexValue += matchIndexValue;
			if (nextIndexValue == 0) {
				return matchIndexValue;
			}
			matchIndexValue++;
		}

	}

	public boolean isPreviousMonth() {
		if (previousMonth == month.get(GregorianCalendar.MONTH)) {
			;
			return true;
		}
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;

		// if pos 0 get count value depending on the month
		// if (position == 0) {
		// // checking previous position bcoz 0 pos coming one after another
		// if (previousPosition != 0) {
		// if (previousMonth == month.get(GregorianCalendar.MONTH)) {
		// colorCount = previousMonthCount;
		// } else {
		// previousMonth = month.get(GregorianCalendar.MONTH);
		// previousMonthCount = colorCount;
		// loginPreferences = mContext.getSharedPreferences(
		// "calendar", mContext.MODE_PRIVATE);
		// loginPrefsEditor = loginPreferences.edit();
		// previousPosition = 0;
		// String tempMonth = String.valueOf(month
		// .get(GregorianCalendar.MONTH));
		// String tempYear = String.valueOf(month
		// .get(GregorianCalendar.YEAR));
		// if (loginPreferences.getBoolean(tempMonth + tempYear
		// + "boolean", false)) {
		// colorCount = loginPreferences
		// .getInt(tempMonth + tempYear, 0);
		// previousMonthCount = colorCount;
		// System.out.println(">>>Count from preference>>>"
		// + colorCount);
		// } else {
		// loginPrefsEditor.putBoolean(tempMonth + tempYear
		// + "boolean", true);
		// loginPrefsEditor.putInt(tempMonth + tempYear, colorCount);
		// loginPrefsEditor.commit();
		// System.out
		// .println(">>>Count store to preference with month year>>>"
		// + tempMonth
		// + tempYear
		// + "\nCount"
		// + colorCount);
		// }
		// }
		// }
		// }

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_item, null);

		}

		dayView = (TextView) v.findViewById(R.id.date);
		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("-");
		// taking last part of date. ie; 2 from 2012-12-02
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		// checking whether the day is in current month or not.
		int view_backcolor = 0;
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			dayView.setTextColor(mContext.getResources().getColor(
					R.color.Calendar_textcolor_onwhite));
			dayView.setClickable(false);
			dayView.setFocusable(false);
			view_backcolor = R.color.Calendar_white;
		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(mContext.getResources().getColor(
					R.color.Calendar_textcolor_onwhite));
			dayView.setClickable(false);
			dayView.setFocusable(false);
			view_backcolor = R.color.Calendar_white;
		} else {
			// setting curent month's days in color.
			dayView.setTextColor(mContext.getResources().getColor(
					R.color.Calendar_textcolor_others));
			// putting same setting color if position is previous
			if (previousPosition == position) {
				if (previousColorFlag) {
					view_backcolor = previousColorCode;
				} else {
					view_backcolor = R.color.white;
				}
			} else { // calling function to check current date with start date
						// to fill color
				try {
					if (isDateAfterStartDate(position)) {

						previousColorFlag = true;
						// previousColorCode = colorCode[colorCount];
						// System.out.println(">>>Color count >>>" +
						// colorCount);
						// view_backcolor = colorCode[colorCount++];
						// checkColorcount();
						// TODO
						if (!isMatchedValue) {
							getCorrectIndex(startDateString, currentDateString);
						}
						// System.out.println("\n>>>Index>>>" + indexCount);
						// System.out.println("\n>>>matchvalue>>>" +
						// matchIndexValue);
						int index = checkIndexCount(indexCount
								+ matchIndexValue);
						nextIndexValue = index + 1;
						// System.out.println(">>>***Index***" + index);
						view_backcolor = colorCode[index];
						previousColorCode = colorCode[index];
					} else {
						previousColorFlag = false;
						view_backcolor = R.color.white;
					}

					previousPosition = position;

				} catch (Exception e) {
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				}
			}
		}

		v.setBackgroundResource(view_backcolor);
		// if (dayString.get(position).equals(curentDateString)) {
		// setSelected(v);
		// previousView = v;
		// } else {
		// v.setBackgroundResource(view_backcolor);
		// }
		dayView.setText(gridvalue);

		// create date string for comparison
		String date = dayString.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.layout_calendarevents_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			// if (date.length() > 0 && items != null) {
			// count=0;

			if (hashMap.containsKey(date)) {
				objAllEvents = new AllEvents();
				objAllEvents = hashMap.get(date);
				layout.setVisibility(View.VISIBLE);
				iw.setVisibility(View.VISIBLE);
				// iw.setBackgroundResource(0);
				// objAllEvents=new AllEvents();
				// objAllEvents=hashMap.get(date);
				objectCount = 0;
				apptSummaryArrayList = objAllEvents.appointmentArrayList;
				if (apptSummaryArrayList != null && objectCount <= 1) {
					// for(AppointmentSummaryEntity
					// obj:hashMap.get(date).appointmentArrayList){

					for (int i = 0; i < apptSummaryArrayList.size(); i++) {
						// if(obj.eventDate.contains(date)){
						objAppointment = apptSummaryArrayList.get(i);
						if (objAppointment.eventDate.contains(date)) {
							iw.setBackgroundResource(R.drawable.appointment);
							objectCount++;
						}
					}
				}
				compTimeArrayList = objAllEvents.compTimeArrayList;
				if (compTimeArrayList != null && objectCount <= 1) {
					// for(CompTimeEntity
					// obj:hashMap.get(date).compTimeArrayList){

					for (int i = 0; i < compTimeArrayList.size(); i++) {
						// if(obj.eventDate.contains(date)){
						objCompTimeEntity = compTimeArrayList.get(i);
						if (objCompTimeEntity.eventDate.contains(date)) {
							iw.setBackgroundResource(R.drawable.comptime);
							objectCount++;
						}
					}
				}
				kellyDayList = objAllEvents.kellyDayArrayList;
				if (kellyDayList != null && objectCount <= 1) {
					// for(KellyDayEntity
					// obj:hashMap.get(date).kellyDayArrayList){

					for (int i = 0; i < kellyDayList.size(); i++) {
						// if(obj.date.contains(date)){
						objKellyDayEntity = kellyDayList.get(i);
						if (objKellyDayEntity.date.contains(date)) {
							iw.setBackgroundResource(R.drawable.kellyday);
							objectCount++;
						}
					}
				}
				overTimeEntityArrayList = objAllEvents.overTimeArrayList;
				if (overTimeEntityArrayList != null && objectCount <= 1) {
					// for(OverTimeEntity
					// obj:hashMap.get(date).overTimeArrayList){

					for (int i = 0; i < overTimeEntityArrayList.size(); i++) {
						// if(obj.date.contains(date)){
						objOverTimeEntity = overTimeEntityArrayList.get(i);
						if (objOverTimeEntity.date.contains(date)) {
							iw.setBackgroundResource(R.drawable.overtime);
							objectCount++;
						}
					}
				}
				sickTimeEntityArrayList = objAllEvents.sickTimeArrayList;
				if (sickTimeEntityArrayList != null && objectCount <= 1) {

					for (int i = 0; i < sickTimeEntityArrayList.size(); i++) {
						objSickTimeEntity = sickTimeEntityArrayList.get(i);
						if (objSickTimeEntity.date.contains(date)) {
							iw.setBackgroundResource(R.drawable.sicktime);
							objectCount++;
						}
					}
				}
				tradeTimeEntityArrayList = objAllEvents.tradeTimeArrayList;
				if (tradeTimeEntityArrayList != null && objectCount <= 1) {

					for (int i = 0; i < tradeTimeEntityArrayList.size(); i++) {
						objTradeTimeEntity = tradeTimeEntityArrayList.get(i);
						if (objTradeTimeEntity.date.contains(date)) {
							iw.setBackgroundResource(R.drawable.tradetime);
							objectCount++;
						}
					}
				}
				vacationEntityArrayList = objAllEvents.vacationTimeArrayList;
				if (vacationEntityArrayList != null && objectCount <= 1) {

					for (int i = 0; i < vacationEntityArrayList.size(); i++) {
						objVacationTimeEntity = vacationEntityArrayList.get(i);
						if (objVacationTimeEntity.startDate.contains(date)) {
							iw.setBackgroundResource(R.drawable.vacationtime);
							objectCount++;
						}
					}
				}
				workersCompArrayList = objAllEvents.workersCompArrayList;
				if (workersCompArrayList != null && objectCount <= 1) {

					for (int i = 0; i < workersCompArrayList.size(); i++) {
						objWorkersCompEntity = workersCompArrayList.get(i);
						if (objWorkersCompEntity.injuryDate.contains(date)) {
							iw.setBackgroundResource(R.drawable.workerscomp);
							objectCount++;
						}
					}
				}

				holydayArrayList = objAllEvents.holidayArrayList;
				if (holydayArrayList != null && objectCount <= 1) {

					for (int i = 0; i < holydayArrayList.size(); i++) {
						objHolidayEntity = holydayArrayList.get(i);
						String xdate = MgtDateFormat
								.dateFormatYYMMDDCalendar(objHolidayEntity.StartDate);
						if (xdate.contains(date)) {
							iw.setBackgroundResource(R.drawable.h_icon);
							objectCount++;
						}
					}
				}

				paydayArrayList = objAllEvents.paydayArrayList;
				if (paydayArrayList != null && objectCount <= 1) {

					for (int i = 0; i < paydayArrayList.size(); i++) {
						objPaydayEntity = paydayArrayList.get(i);
						String xdate = MgtDateFormat
								.dateFormatYYMMDDCalendar(objPaydayEntity.StartDate);
						if (xdate.contains(date)) {
							iw.setBackgroundResource(R.drawable.p_icon);
							objectCount++;
						}
					}
				}
				if (objectCount > 1)
					iw.setBackgroundResource(R.drawable.exclaimation);
			}
		} else {
			layout.setVisibility(View.INVISIBLE);
			iw.setVisibility(View.INVISIBLE);
		}

		return v;
	}

	public View setSelected(View view) {
		if (previousView != null) {
			// previousView.setBackgroundResource();
		}
		previousView = view;
		// view.setBackgroundResource(R.drawable.calendar_cel_selectl);
		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		dayString.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			dayString.add(itemvalue);

		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}

}

package com.mygrouptracker.mygrouptracker.utility;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class MyDatePickerDialog extends DatePickerDialog{

	private Date maxDate;
	private Date minDate;

	public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
	    super(context, callBack, year, monthOfYear, dayOfMonth);        
	    init(year, monthOfYear, dayOfMonth);
	}

	public MyDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,    int dayOfMonth) {
	    super(context, theme, callBack, year, monthOfYear, dayOfMonth);
	    init(year, monthOfYear, dayOfMonth);
	}

	private void init(int year, int monthOfYear, int dayOfMonth){
	    Calendar cal = Calendar.getInstance();

	    cal.set(1970, Calendar.JANUARY, 1);
	    minDate = cal.getTime();

	    cal.set(3000, Calendar.JANUARY, 1);
	    maxDate = cal.getTime();

	    cal.set(year, monthOfYear, dayOfMonth);
	}

	@Override
	public void show() {
		super.show();
	};
	public void onDateChanged (final DatePicker view, int year, int month, int day){
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month, day);
	    Date currentDate = cal.getTime();

	    final Calendar resetCal = cal; 
	    if(!minDate.before(currentDate) ){
	        cal.setTime(minDate);
	        view.updateDate(resetCal.get(Calendar.YEAR), resetCal.get(Calendar.MONTH), resetCal.get(Calendar.DAY_OF_MONTH));

	    }else if(maxDate.before(currentDate)){
	        cal.setTime(maxDate);
	        view.updateDate(resetCal.get(Calendar.YEAR), resetCal.get(Calendar.MONTH), resetCal.get(Calendar.DAY_OF_MONTH));
	    }       
	}

	public void setMaxDate(Date date){
	    this.maxDate = date;
	}

	public void setMinDate(Date date){
		//   Calendar cal = Calendar.getInstance();
	    this.minDate = date;
	}   
	
	
}
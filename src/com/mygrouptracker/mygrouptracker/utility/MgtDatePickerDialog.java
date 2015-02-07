
package com.mygrouptracker.mygrouptracker.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.internal.ex;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MgtDatePickerDialog  implements OnClickListener, OnDateSetListener {   
EditText _editText;
private int _day;
private int _month;
private int _birthYear;
private Context _context;
private boolean isTimePickerCalled;
Date minDate,maxDate,existingDate;
public MgtDatePickerDialog(Context context,EditText txt)
{       
    Activity act = (Activity)context;
    this._editText = txt;
    _editText.setFocusable(false);
    this._editText.setOnClickListener(this);
    this._context = context;
    isTimePickerCalled=false;
   
}
public MgtDatePickerDialog(Context context,EditText txt,Date minDate,Date maxDate)
{       
    Activity act = (Activity)context;
    this._editText = txt;
    _editText.setFocusable(false);
    this._editText.setOnClickListener(this);
    this._context = context;
    isTimePickerCalled=false;
    this.minDate=minDate;
    this.maxDate=maxDate;
}
public MgtDatePickerDialog(Context context,EditText txt,Date minDate,Date maxDate,Date currentDate)
{       
    Activity act = (Activity)context;
    this._editText = txt;
    _editText.setFocusable(false);
    this._editText.setOnClickListener(this);
    this._context = context;
    isTimePickerCalled=false;
    this.minDate=minDate;
    this.maxDate=maxDate;
    this.existingDate=currentDate;
}
@Override
public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    _birthYear = year;
    _month = monthOfYear;
    _day = dayOfMonth;
    updateDisplay();
}
@Override
public void onClick(View v) {
	isTimePickerCalled=false;
	
    MyDatePickerDialog dialog =  new MyDatePickerDialog(_context, this, 2014, 4, 15);
    Calendar cal=Calendar.getInstance();
	if(existingDate==null)
	{
    dialog.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
	}
	
	else{
	dialog.updateDate(existingDate.getYear(),existingDate.getMonth(), existingDate.getDate());
	}
    SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
    Date minDatetemp=minDate;
    Date maxDatetemp=maxDate;
    
	try {
		if(minDatetemp==null)
		minDatetemp = dt.parse(MgtDateFormat.getCurrentDateMMDDYYYY());
		if(maxDatetemp==null)
		{
			maxDatetemp=dt.parse("01/01/2099");
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    dialog.setMinDate(minDatetemp);
    dialog.setMaxDate(maxDatetemp);
    minDatetemp=null;
    maxDatetemp=null;
    dialog.show();

}

// updates the date in the birth date EditText
private void updateDisplay() {

    _editText.setText(new StringBuilder()
    // Month is 0 based so add 1
    .append(_month+1).append("/").append(_day).append("/").append(_birthYear).append(" "));
//   if(isTimePickerCalled==false)
//   new MgtTimePickerDialog(_context,_editText).showDialog();
//    isTimePickerCalled=true;
    
}

}
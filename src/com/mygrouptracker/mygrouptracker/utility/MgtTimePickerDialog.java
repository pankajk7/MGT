
package com.mygrouptracker.mygrouptracker.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;

import android.widget.TimePicker;

public class MgtTimePickerDialog  implements OnClickListener, OnTimeSetListener{   
EditText _editText;
private int hh;
private int mm;
private int ss;
private Context _context;
private boolean isCalled=false;
public MgtTimePickerDialog(Context context,EditText txt)
{       
    Activity act = (Activity)context;
    this._editText = txt;
  //  _editText.setFocusable(false);
  //  this._editText.setOnClickListener(this);
    this._context = context;
   
}


@Override
public void onClick(View v) {
   

}

public void showDialog()
{
	isCalled=false;
	 MyTimePickerDialog dialog=new MyTimePickerDialog(_context, this, 24, 00, true);
	 Calendar cal=Calendar.getInstance();
	 
	 dialog.setMinTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
	 dialog.updateTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
	 dialog.show();
	}
// updates the date in the birth date EditText
private void updateDisplay() {

     String prev=_editText.getText().toString();
    StringBuilder s= new StringBuilder().append(prev)
    // Month is 0 based so add 1
    .append(" ").append(hh).append(":").append(mm).append(":").append("44");
   
    
   String dates=MgtDateFormat.dateFormatYYMMDDTime(s.toString());
   _editText.setText(dates);
    
    
}

@Override
public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	// TODO Auto-generated method stub
	hh=hourOfDay;
	mm=minute;
	if(isCalled==false)
	updateDisplay();
	isCalled=true;
	
}

}
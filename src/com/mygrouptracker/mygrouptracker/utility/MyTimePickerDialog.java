package com.mygrouptracker.mygrouptracker.utility;

import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class MyTimePickerDialog extends TimePickerDialog
{private  int minMin,minhour;
private int maxhour,maxMin;

	public MyTimePickerDialog(Context context, OnTimeSetListener callBack,
			int hourOfDay, int minute, boolean is24HourView) {
		super(context, callBack, hourOfDay, minute, is24HourView);
		// TODO Auto-generated constructor stub
	}

	public MyTimePickerDialog(Context context, int theme, OnTimeSetListener callBack,
			int hourOfDay, int minute, boolean is24HourView) {
		super(context, theme, callBack, hourOfDay, minute, is24HourView);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onTimeChanged(final TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		super.onTimeChanged(view, hourOfDay, minute);
		try{
		 	Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
		    cal.set(Calendar.MINUTE,minute);
		    Date currentDate = cal.getTime();
		    

		       
		    final Calendar resetCal = cal; 
		   
//		    if(minhour>hourOfDay||minMin>minute){
//		    //    cal.setTime(minDate);
//		       view.setCurrentHour(minhour);
//		       view.setCurrentMinute(minMin);
//
//		    }else
		    	if(maxhour<hourOfDay||maxMin<minute){
		        view.setCurrentHour(resetCal.get(Calendar.HOUR_OF_DAY));
			    view.setCurrentMinute(resetCal.get(Calendar.MINUTE));
		    }
		}catch(Exception er){}
		
	}
	
	public void setMaxTime(int hourOfDay,int minute){
		
		hourOfDay=hourOfDay%24;
		 this.maxhour = hourOfDay;
		    this.maxMin=minute;
	}

	public void setMinTime(int hourOfDay,int minute ){
		
		hourOfDay=hourOfDay%24;
		
	    this.minhour = hourOfDay;
	    this.minMin=minute;
	} 

}

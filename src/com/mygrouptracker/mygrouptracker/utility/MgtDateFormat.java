package com.mygrouptracker.mygrouptracker.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class MgtDateFormat {

	
	public static String getCurrentDateTime()
	{
		String datetime="";
		Calendar cal=Calendar.getInstance();
		datetime+=cal.get(Calendar.DATE);
		datetime+="/";
		datetime+=(cal.get(Calendar.MONTH)+1);
		datetime+="/";
		datetime+=cal.get(Calendar.YEAR);
		datetime+=" ";
		
		datetime+=(cal.get(Calendar.HOUR)+12);
		datetime+=":";
		datetime+=cal.get(Calendar.MINUTE);
		datetime+=":";
		datetime+="00";
	
		return datetime;
	}
	
	public static String getCurrentDateMMDDYYYY()
	{
		String datetime="";
		Calendar cal=Calendar.getInstance();
		int month=cal.get(Calendar.MONTH)+1;
		datetime+=month;
		datetime+="/";
		datetime+=(cal.get(Calendar.DATE));
		datetime+="/";
		datetime+=cal.get(Calendar.YEAR);
		//datetime+="";
		
	/*	datetime+=(cal.get(Calendar.HOUR)+12);
		datetime+=":";
		datetime+=cal.get(Calendar.MINUTE);
		datetime+=":";
		datetime+="00";*/
	
		return datetime;
	}
	
	public static String dateFormatYYMMDD(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
			date=date.replace("T", " ");
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("dd/MM/yyyy");
		 nd =dt.format(date1);
		return nd;
		}
		catch(Exception er)
		{
			return nd;
		}
	}
	public static String dateFormatSendingToServer(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
			date=date.replace("T", " ");
			
		    SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 nd =dt.format(date1);
		return nd;
		}
		catch(Exception er)
		{
			return nd;
		}
	}
	
	public static String dateFormatYYMMDDCalendar(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
			date=date.replace("T", " ");
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("yyyy-MM-dd");
		 nd =dt.format(date1);
		return nd;
		}catch(Exception er)
		{
			return nd;
		}
	}
	public static String dateFormatMMDDYYYYTIMECalendar(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
			date=date.replace("T", " ");
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("MM/dd/yyyy");
		 nd =dt.format(date1);
		return nd;
		}catch(Exception er)
		{
			return nd;
		}
	}
	public static String dateFormatMMddYYYYCalendar(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
			date=date.replace("T", " ");
			SimpleDateFormat dt;
			if(date.contains("/"))
			{
				dt = new SimpleDateFormat("dd/MM/yyyy");
			}
			else
			{
		     dt = new SimpleDateFormat("yyyy-MM-dd");
			}
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("MM/dd/yyyy");
		 nd =dt.format(date1);
		return nd;
		}catch(Exception er)
		{
			return nd;
		}
	}
	
	
	public static String dateFormatYYMMDDTime(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
			date=date.replace("T", " ");
		    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("dd/MM/yyyy");
		 nd =dt.format(date1);
		
		}catch(Exception er)
		{
//			return nd;
		}
		return nd;
	}
	
	public static String getDate(String date)
	{
		String nd=date;
		try{
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("dd");
		 nd =dt.format(date1);
		 if(nd.length()==1)
			 nd="0"+nd;
		}catch(Exception er)
		{
//			return nd;
		}
		return nd;
	}
	
	public static String getMonth(String date)
	{
		String nd=date;
		try{
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("MM");	       
		 nd =dt.format(date1);
		 if(nd.length()==1)
			 nd="0"+nd;
		}catch(Exception er)
		{
//			return nd;
		}
		return nd;
	}
	
	public static String getYear(String date)
	{
		String nd=date;
		try{
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("yyyy");
		 nd =dt.format(date1);
		
		}catch(Exception er)
		{
//			return nd;
		}
		return nd;
	}
	
	public static String getYearFormat(String date)
	{
		String nd=date;
		try{
		    SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("yyyy");
		 nd =dt.format(date1);
		
		}catch(Exception er)
		{
//			return nd;
		}
		return nd;
	}
	
	public static String getHour(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
				date=date.replace("T", " ");
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	       Date date1 = dt.parse(date);
	       dt=new SimpleDateFormat("hh");
		 nd = dt.format(date1);
	        
		}catch(Exception er)
		{
			System.out.println(">>Exception>>"+er.toString()+">>>"+er.getMessage());
//			return nd;
		}
		return nd;
	}
	
	public static String getMinutes(String date)
	{
		String nd=date;
		try{
			if(date.contains("T")==true)
				date=date.replace("T", " ");
			SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	       Date date1 = dt.parse(date);
	      
	       dt=new SimpleDateFormat("mm");
		 nd =dt.format(date1);
		}catch(Exception er)
		{
			System.out.println(">>Exception>>"+er.toString()+">>>"+er.getMessage());
//			return nd;
		}
		return nd;
	}
	
	
	public static String incrementDate(String date)
	{
		String nd=date;
		try{
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	        Date date1 = dt.parse(date);
	        Calendar c = Calendar.getInstance();
	        c.setTime(date1); 
	        c.add(Calendar.DATE,1);
	        dt=new SimpleDateFormat("yyyy-MM-dd");
	        nd =dt.format(c.getTime());
		
		}catch(Exception er)
		{
//			return nd;
		}
		return nd;
	}
	
	public static int getNumberOfDays(String date){
		String nd=date;
		int totaldays=0;
		try{
		    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	        Date date1 = dt.parse(date);
	        Calendar c = Calendar.getInstance();
	        
	        c.setTime(date1); 
	        totaldays=c.getActualMaximum(Calendar.DAY_OF_MONTH);
//	        c.add(Calendar.MONTH,1);
//	        dt=new SimpleDateFormat("yyyy-MM-dd");
//	        nd =dt.format(c.getTime());
		
		}catch(Exception e)
		{
			Log.e("Exception", e.toString());
//			return nd;
		}
		return totaldays;
	}
}

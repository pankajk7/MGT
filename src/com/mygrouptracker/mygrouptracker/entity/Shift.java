package com.mygrouptracker.mygrouptracker.entity;

import java.util.ArrayList;

public class Shift {

public String   id;
public String	shiftName;
public String	startDate;
public String	hoursPerDay;
public ArrayList<String> day;
public ArrayList<String> ColorName;
public ArrayList<String> ColorCode;
//public String	day02;
//public String	day02ColorName;
//public String	day02ColorCode;
//public String	day03;
//public String	day03ColorName;
//public String	day03ColorCode;
//public String	day04;
//public String	day04ColorName;
//public String	day04ColorCode;
//public String	day05;
//public String	day05ColorName;
//public String	day05ColorCode;
//public String	day06;
//public String	day06ColorName;
//public String	day06ColorCode;
	
	public Shift() {
		// TODO Auto-generated constructor stub
	}
	
	public Shift(String id, String shiftName){
		this.id=id;
		this.shiftName = shiftName;
	}

//	public Shift(String id,String shiftName, String startDate, String hoursPerDay,
//			String day01, String day01ColorName, String day01ColorCode,
//			String day02, String day02ColorName, String day02ColorCode,
//			String day03, String day03ColorName, String day03ColorCode,
//			String day04, String day04ColorName, String day04ColorCode,
//			String day05, String day05ColorName, String day05ColorCode,
//			String day06, String day06ColorName, String day06ColorCode) 
//	{
//
//		this.id=id;
//		this.shiftName = shiftName;
//		this.startDate = startDate;
//		this.hoursPerDay = hoursPerDay;
//		this.day01 = day01;
//		this.day01ColorName = day01ColorCode;
//		this.day01ColorCode = day01ColorCode;
//		this.day02 = day02;
//		this.day02ColorName = day02ColorName;
//		this.day02ColorCode = day02ColorCode;
//		this.day03 = day03;
//		this.day03ColorName = day03ColorName;
//		this.day03ColorCode = day03ColorCode;
//		this.day04 = day04;
//		this.day04ColorName = day04ColorName;
//		this.day04ColorCode = day04ColorCode;
//		this.day05 = day05;
//		this.day05ColorName = day05ColorName;
//		this.day05ColorCode = day05ColorCode;
//		this.day06 = day06;
//		this.day06ColorName = day06ColorName;
//		this.day06ColorCode = day06ColorCode;
//
//	}
	
	@Override
	public String toString() {
	 return	shiftName;
	};

}

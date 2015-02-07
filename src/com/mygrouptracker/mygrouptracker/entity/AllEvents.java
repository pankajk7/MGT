package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class AllEvents implements Serializable{

	public ArrayList<AppointmentSummaryEntity> appointmentArrayList;
	public ArrayList<CompTimeEntity> compTimeArrayList;
	public ArrayList<KellyDayEntity> kellyDayArrayList;
	public ArrayList<OverTimeEntity> overTimeArrayList;
	public ArrayList<SickTimeEntity> sickTimeArrayList;
	public ArrayList<TradeTimeEntity> tradeTimeArrayList;
	public ArrayList<VacationTimeEntity> vacationTimeArrayList;
	public ArrayList<WorkersCompEntity> workersCompArrayList;
	public ArrayList<HolidayEntity> holidayArrayList;
	public ArrayList<PaydayEntity> paydayArrayList;

	public AllEvents() {

		this.appointmentArrayList = new ArrayList<AppointmentSummaryEntity>();
		this.compTimeArrayList = new ArrayList<CompTimeEntity>();
		this.kellyDayArrayList = new ArrayList<KellyDayEntity>();
		this.overTimeArrayList = new ArrayList<OverTimeEntity>();
		this.sickTimeArrayList = new ArrayList<SickTimeEntity>();
		this.tradeTimeArrayList = new ArrayList<TradeTimeEntity>();
		this.vacationTimeArrayList = new ArrayList<VacationTimeEntity>();
		this.workersCompArrayList = new ArrayList<WorkersCompEntity>();
		this.holidayArrayList=new ArrayList<HolidayEntity>();
		this.paydayArrayList=new ArrayList<PaydayEntity>();
	}	

}

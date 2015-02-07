package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SickTimeEntity implements Serializable{
	public String id;
	public String date;
    public String notes;
    public String hours;
    public String isShare;
    
    public List<SickTimeEntity> getSickTimeSummary()
    {
		List<SickTimeEntity> listSickTime=new ArrayList<SickTimeEntity>();
		
		for(int i=1; i<=10 ; i++)
		{
			SickTimeEntity obj=new SickTimeEntity();
			obj.date=i+"/03/14";
			obj.notes="abc 123"+i;
			obj.hours="12";
			listSickTime.add(obj);
		}
		
    	return listSickTime;    	
    }

//	public String getDate() {
//		return date;
//	}
//
//	public void setDate(String date) {
//		this.date = date;
//	}
//	
	
}

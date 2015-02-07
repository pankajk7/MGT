package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompTimeEntity implements Serializable{
	public String id;
	public String eventDate;
	public String type;
	public String hours;
	public String desc;
	public String isShare;
	public String userId;
	public String eventType;
	
	
	 public List<CompTimeEntity> getCompTimeSummary()
	    {
	    	List<CompTimeEntity> listCompTime=new ArrayList<CompTimeEntity>();
			
			for(int i=1; i<=10 ; i++)
			{
				CompTimeEntity obj=new CompTimeEntity();
				obj.eventDate=i+"/03/14";
				obj.type="Earned";
				obj.hours="12";
				obj.desc="abc123";
				listCompTime.add(obj);
			}
			
	    	return listCompTime; 
	    }

}

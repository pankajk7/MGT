package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OverTimeEntity implements Serializable {
	public String id;
	public String date;
	public String hours;
	public String assignment;
	public String desc1;
	public String desc2;
	public String userId;
	public String entryDate;
	public String isShare;
    
    public List<OverTimeEntity> getOverTimeSummary()
    {
    	List<OverTimeEntity> listOverTime=new ArrayList<OverTimeEntity>();
		
		for(int i=1; i<=10 ; i++)
		{
			OverTimeEntity obj=new OverTimeEntity();
			obj.date=i+"/03/14";
			obj.assignment="abc 123"+i;
			obj.hours="12";
			obj.desc1="abc123";
			obj.desc2="abc123";
			listOverTime.add(obj);
		}
		
    	return listOverTime; 
    }
}

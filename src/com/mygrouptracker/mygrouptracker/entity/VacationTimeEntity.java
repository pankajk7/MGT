package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class VacationTimeEntity implements Serializable{
	public String id;
	public String startDate;
	public String endDate;
	public String note;
	public String hour;
	public String isShare;
	public String entryDate;
	
	
	public List<VacationTimeEntity> getVacationTimeSummary()
	{
		List<VacationTimeEntity> listVacationTime=new ArrayList<VacationTimeEntity>();
		
		for(int i=1; i<=10;i++)
		{
			VacationTimeEntity obj=new VacationTimeEntity();
			obj.startDate=i+"/03/14";
			obj.endDate=i+1+"/03/14";
			obj.note="abc 123"+i;
			obj.hour="12";
			listVacationTime.add(obj);
		}
		
		return listVacationTime;
	}
}

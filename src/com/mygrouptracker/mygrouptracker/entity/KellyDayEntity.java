package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class KellyDayEntity implements Serializable{
	public String id;
	public String date;
	public String repeat;
	public String userId;
	public String isShare;
	
	public List<KellyDayEntity> getKellyDaySummary()
	{		
		List<KellyDayEntity> listKellySummary=new ArrayList<KellyDayEntity>();
		
		for(int i=1;i<=10;i++)
		{
			KellyDayEntity kellysummary=new KellyDayEntity();
			kellysummary.date=i+"/3/14";		
			listKellySummary.add(kellysummary);
		}
		
		return listKellySummary;		
	}
}

package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSummaryEntity implements Serializable{
	
		public String description;
		public String isShare;
		public String name;
		public String eventDate;
		public String recurring;
		public String id;
		public String entryDate;
		public String userId;
		
	
	public List<AppointmentSummaryEntity> getapptSummary(){		
		List<AppointmentSummaryEntity> listapptsummary=new ArrayList<AppointmentSummaryEntity>();
		
		for(int i=1;i<=10;i++)
		{
			AppointmentSummaryEntity apptsummary=new AppointmentSummaryEntity();
			apptsummary.eventDate=i+"/3/14";
			apptsummary.description="Description "+i;
			if(i%3==0)
				apptsummary.recurring="Bi-Weekly";
			else
				apptsummary.recurring="None";
			
			listapptsummary.add(apptsummary);
		}
		
		return listapptsummary;		
	}

}

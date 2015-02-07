package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkersCompEntity implements Serializable {
	public String id;
	public String injuryDate;
	public String returnDate;
	public String notes;
	public String caseNumber;
	public String entryDate;
	public String isShare;
	
	
	public List<WorkersCompEntity> getOverTimeSummary()
    {
    	List<WorkersCompEntity> listWorkersComp=new ArrayList<WorkersCompEntity>();
		
		for(int i=1; i<=10 ; i++)
		{
			WorkersCompEntity obj=new WorkersCompEntity();
			obj.injuryDate=i+"/03/14";
			obj.returnDate=i+1+"/03/14";
			obj.notes="abc 123"+i;
			obj.caseNumber="12";
			listWorkersComp.add(obj);
		}
		
    	return listWorkersComp; 
    }

}

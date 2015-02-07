package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerEntity implements Serializable {

	public String id;
	public String userId;
	public String eventDate;
	public String desc1;
	public String desc2;
	public String desc3;
	public String amount;
	
	
	 public List<ExpenseTrackerEntity> getExpenseTracker()
	    {
	    	List<ExpenseTrackerEntity> listExpenseTracker=new ArrayList<ExpenseTrackerEntity>();
			
			for(int i=1; i<=10 ; i++)
			{
				ExpenseTrackerEntity obj=new ExpenseTrackerEntity();
				obj.eventDate=i+"/03/14";
				obj.desc1="mobile phone";
				obj.desc2="abc";
				obj.desc3="abc";
				obj.amount="$123"+i;
				listExpenseTracker.add(obj);
			}
			
	    	return listExpenseTracker; 
	    }
}

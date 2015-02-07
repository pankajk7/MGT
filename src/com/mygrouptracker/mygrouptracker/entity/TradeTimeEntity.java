package com.mygrouptracker.mygrouptracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TradeTimeEntity implements Serializable {
	public String id;
	public String name;
	public String date;
	public String hours;
	public String note;
	public String entryDate;
	public String userId;
	public String isOweMe;
	
	 public List<TradeTimeEntity> getTradeTimeSummary()
	    {
			List<TradeTimeEntity> listTradeTime=new ArrayList<TradeTimeEntity>();
			
			for(int i=1; i<=10 ; i++)
			{
				TradeTimeEntity obj=new TradeTimeEntity();
				obj.name="SMITH CHRISTOPHER";
				obj.date=i+"/03/14";
				obj.note="abc 123"+i;
				obj.hours="12";
				listTradeTime.add(obj);
			}
			
	    	return listTradeTime;    	
	    }
}

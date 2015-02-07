package com.mygrouptracker.mygrouptracker.entity;

import java.util.ArrayList;
import java.util.List;

public class TradeTimeArchiveEntity {
	public String name;
	public String date;
	public String hours;
	public String note;
	
	 public List<TradeTimeArchiveEntity> getTradeTimeArchive()
	    {
			List<TradeTimeArchiveEntity> listTradeTimeArchive=new ArrayList<TradeTimeArchiveEntity>();
			
			for(int i=1; i<=10 ; i++)
			{
				TradeTimeArchiveEntity obj=new TradeTimeArchiveEntity();
				obj.name="SMITH CHRISTOPHER";
				obj.date=i+"/03/14";
				obj.note="abc 123"+i;
				obj.hours="12";
				listTradeTimeArchive.add(obj);
			}
			
	    	return listTradeTimeArchive;    	
	    }
}

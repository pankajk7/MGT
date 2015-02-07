package com.mygrouptracker.mygrouptracker.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.entity.AllEvents;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.HolidayEntity;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.PaydayEntity;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListAllEventsBaseAdapter extends BaseAdapter {

	VacationTimeEntity objVacationTime;
	ArrayList<AppointmentSummaryEntity> appointmentArrayList;
	ArrayList<CompTimeEntity> compTimeArrayList;
	List<VacationTimeEntity> listVacationTime;
	LayoutInflater inflater;
	Button editButton;
	Context context;
	List list;
	AllEvents objAllEvents=new AllEvents();
	int objPosition;
	
	 private static final int APPOINTMENT = 0;
     private static final int COMPTIME = 1;
     private static final int KELLYDAY = 2;
     private static final int OVERTIME = 3;
     private static final int SICKTIME = 4;
     private static final int TRADETIME = 5;
     private static final int VACTIONTIME = 6;
     private static final int WORKERSCOMP = 7;
     private static final int PAYDAY = 8;
     private static final int HOLIDAY = 9;
     private static final int TYPE_MAX_COUNT = COMPTIME + KELLYDAY + OVERTIME + SICKTIME + TRADETIME + VACTIONTIME + PAYDAY + HOLIDAY + 1;

     private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
	
	public ListAllEventsBaseAdapter(Context context)
	{
	  	this.context=context;
//	  	listVacationTime=list;
	  	inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getItemViewType(int position) {
		
		int size=objAllEvents.appointmentArrayList.size();
//		 return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
		 if(size>position)
			 return APPOINTMENT;
		 size+=objAllEvents.compTimeArrayList.size();
		 if(size>position)
			 return COMPTIME;
		 size+=objAllEvents.kellyDayArrayList.size();
		 if(size>position)
			 return KELLYDAY;
		 size+=objAllEvents.overTimeArrayList.size();
		 if(size>position)
			 return OVERTIME;
		 size+=objAllEvents.sickTimeArrayList.size();
		 if(size>position)
			return SICKTIME;
		 size+=objAllEvents.tradeTimeArrayList.size();
		 if(size>position)
			 return TRADETIME;
		 size+=objAllEvents.vacationTimeArrayList.size();
		 if(size>position)
			 return VACTIONTIME;
		 size+=objAllEvents.workersCompArrayList.size();
		 if(size>position)
			 return WORKERSCOMP;
		 size+=objAllEvents.paydayArrayList.size();
		 if(size>position)
			 return PAYDAY;
		 size+=objAllEvents.holidayArrayList.size();
		 if(size>position)
			 return HOLIDAY;
		 
		 return 0;
	}
	
	@Override
	 public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	 }
	
	 public void addAppointment(ArrayList<AppointmentSummaryEntity> list) {
		 if(list!=null){
			 objAllEvents.appointmentArrayList=list;
		 }
			 notifyDataSetChanged();
     }
	 
	 public void addCompTime(ArrayList<CompTimeEntity> list) {
		 if(list!=null){
			 objAllEvents.compTimeArrayList=list;	
			 int size=objAllEvents.appointmentArrayList.size();	 
			 mSeparatorsSet.add(size-1);
		 }
         notifyDataSetChanged();
     }
	 
	 public void addKellyDay(ArrayList<KellyDayEntity> list) {
		 if(list!=null){
			 objAllEvents.kellyDayArrayList=list;	
			 int size=objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
       notifyDataSetChanged();
   }
	 
	 public void addOverTime(ArrayList<OverTimeEntity> list) {
		 if(list!=null){
			 objAllEvents.overTimeArrayList=list;	
			 int size=objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
       notifyDataSetChanged();
   }
	 
	 public void addSickTime(ArrayList<SickTimeEntity> list) {
		 if(list!=null){
			 objAllEvents.sickTimeArrayList=list;	
			 int size=objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.sickTimeArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
       notifyDataSetChanged();
   }
	 
	 public void addTradeTime(ArrayList<TradeTimeEntity> list) {
		 if(list!=null){
			 objAllEvents.tradeTimeArrayList=list;	
			 int size=getCount()-objAllEvents.vacationTimeArrayList.size()+objAllEvents.workersCompArrayList.size()+objAllEvents.paydayArrayList.size() + objAllEvents.holidayArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
       notifyDataSetChanged();
   }
	 
	 public void addVacationTime(ArrayList<VacationTimeEntity> list) {
		 if(list!=null){
			 objAllEvents.vacationTimeArrayList=list;	
			 int size=getCount()-objAllEvents.workersCompArrayList.size()+objAllEvents.paydayArrayList.size()+objAllEvents.holidayArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
       notifyDataSetChanged();
   }
	 
	 public void addWorkersCompTime(ArrayList<WorkersCompEntity> list) {
		 if(list!=null){
			 objAllEvents.workersCompArrayList=list;	
			 int size = getCount() - objAllEvents.paydayArrayList.size() + objAllEvents.holidayArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
       notifyDataSetChanged();
   }
	 
	 public void addPayday(ArrayList<PaydayEntity> list) {
		 if(list!=null){
			 objAllEvents.paydayArrayList=list;
			 int size = getCount() - objAllEvents.holidayArrayList.size();
			 mSeparatorsSet.add(size-1);
		 }
			 notifyDataSetChanged();
     }
	 
	 public void addHoliday(ArrayList<HolidayEntity> list) {
		 if(list!=null){
			 objAllEvents.holidayArrayList = list;
			 int size = getCount();
			 mSeparatorsSet.add(size-1);
		 }
			 notifyDataSetChanged();
     }
	 

//     public void addSeparatorItem(final String item) {
//         mData.add(item);
//         // save separator position
//         mSeparatorsSet.add(mData.size() - 1);
//         notifyDataSetChanged();
//     }
	 
	@Override
	public int getCount() {
		return objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+
			   objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()+
			   objAllEvents.sickTimeArrayList.size()+objAllEvents.tradeTimeArrayList.size()+
			   objAllEvents.vacationTimeArrayList.size()+objAllEvents.workersCompArrayList.size()+
			   objAllEvents.paydayArrayList.size() + objAllEvents.holidayArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if(convertView==null)
		{			
			 switch (type) {
             case APPOINTMENT:
                 convertView = inflater.inflate(R.layout.appointment_summary_items, null);
                 break;
             case COMPTIME:
                 convertView = inflater.inflate(R.layout.comp_time_summary_items, null);
                 break;
             case KELLYDAY:
            	 convertView = inflater.inflate(R.layout.kelly_day_summary_items, null);
            	 break;
             case OVERTIME:
            	 convertView = inflater.inflate(R.layout.overtime_summary_items, null);
            	 break;
             case SICKTIME:
            	 convertView = inflater.inflate(R.layout.sick_time_summary_items, null);
            	 break;
             case TRADETIME:
            	 convertView = inflater.inflate(R.layout.trade_time_summary_items, null);
            	 break;
             case VACTIONTIME:
            	 convertView = inflater.inflate(R.layout.vacation_time_summary_items, null);
            	 break;
             case WORKERSCOMP:
            	 convertView = inflater.inflate(R.layout.workers_comp_summary_items, null);
            	 break;
             case PAYDAY:
            	 convertView = inflater.inflate(R.layout.payday_layout, null);
            	 break;
             case HOLIDAY:
            	 convertView = inflater.inflate(R.layout.holiday_layout, null);
            	 break;
         }
		}
		switch (type) {
        case APPOINTMENT:        
        	TextView date = (TextView)convertView.findViewById(R.id.textview_appt_summary_items_date);
    		TextView notes = (TextView)convertView.findViewById(R.id.textview_appt_summary_items_notes);
    		TextView recurring = (TextView)convertView.findViewById(R.id.textview_appt_summary_items_recurring);
    		editButton=(Button)convertView.findViewById(R.id.btn_appointmentsummaryitems_edit);
    		editButton.setTag(position);
    		if(position==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_appointmentsummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		AppointmentSummaryEntity objAppointmentSummaryEntity= objAllEvents.appointmentArrayList.get(position);
    		
//    		date.setText(objAppointmentSummaryEntity.eventDate);
    		date.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objAppointmentSummaryEntity.eventDate));
    		notes.setText(objAppointmentSummaryEntity.description);
    		if(objAppointmentSummaryEntity.recurring.equals("0"))
    			recurring.setText("none");
    		else if(objAppointmentSummaryEntity.recurring.equals("1"))
    			recurring.setText("weekly");
    		else if(objAppointmentSummaryEntity.recurring.equals("2"))
    			recurring.setText("Biweekly");
    		else if(objAppointmentSummaryEntity.recurring.equals("3"))
    			recurring.setText("monthly");
    		 
            break;
        case COMPTIME:
            TextView dateTextView = (TextView)convertView.findViewById(R.id.textview_comptimesummary_date);		
            TextView typeTextView = (TextView)convertView.findViewById(R.id.textview_comptimesummary_type);
            TextView hoursTextView = (TextView)convertView.findViewById(R.id.textview_comptimesummary_hours);
            Button editButton=(Button)convertView.findViewById(R.id.btn_comptimesummary_edit);
            editButton.setTag(position); 
            
            CompTimeEntity objCompTime=new CompTimeEntity();
            objPosition=objAllEvents.appointmentArrayList.size()-position;
            if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_comptimesummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
            if(objPosition<0)
    			objPosition*=-1; //multiplying -1 becoz getting reverse position in negative
			objCompTime=objAllEvents.compTimeArrayList.get(objPosition);		
//		dateTextView.setText(objCompTime.eventDate);
			dateTextView.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objCompTime.eventDate));
			if(objCompTime.eventType.equals("0"))
				{
				typeTextView.setText("Earned");
				convertView.setBackgroundResource(R.color.green_listview);
				}
			else
			{
				typeTextView.setText("Taken");
				convertView.setBackgroundResource(R.color.red_listview);
			}
			hoursTextView.setText(objCompTime.hours);	
			/*if (position % 2 == 0) {
				convertView.setBackgroundResource(0);
				
			}
			else{
				convertView.setBackgroundResource(0);
				
			}*/
            break;
        case KELLYDAY:
        	TextView dateKellyDay = (TextView)convertView.findViewById(R.id.textview_kellydaysummary_date);
    		editButton=(Button)convertView.findViewById(R.id.btn_kellydaysummary_edit);
    		editButton.setTag(position);
    		
    		//multiplying -1 becoz getting reverse position in negative
    		
    		KellyDayEntity objKellyDayEntity=new KellyDayEntity();
    		objPosition=objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()-position;
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_kellydaysummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objKellyDayEntity= objAllEvents.kellyDayArrayList.get(objPosition);			
//    		date.setText(objKellyDayEntity.date);
    		dateKellyDay.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objKellyDayEntity.date));
    		break;
        case OVERTIME:
        	TextView dateOverTime = (TextView)convertView.findViewById(R.id.textview_overtimesummary_date);
    		TextView assignment = (TextView)convertView.findViewById(R.id.textview_overtimesummary_assignment);
    		TextView hours = (TextView)convertView.findViewById(R.id.textview_overtimesummary_hours);
    		editButton=(Button)convertView.findViewById(R.id.btn_overtimesummary_edit);
    		editButton.setTag(position); //To get position onClick of button in listview
    		
    		OverTimeEntity objOverTime=new OverTimeEntity();
    		objPosition=objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()-position;
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_overtimesummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objOverTime=objAllEvents.overTimeArrayList.get(objPosition);		
//    		date.setText(objOverTime.date);
    		dateOverTime.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objOverTime.date));
    		assignment.setText(objOverTime.assignment);
    		hours.setText(objOverTime.hours);
       	 break;
        case SICKTIME:
        	TextView dateSickTime = (TextView)convertView.findViewById(R.id.textview_sicktimesummary_date);
    		TextView notesSicktime = (TextView)convertView.findViewById(R.id.textview_sicktimesummary_notes);
    		TextView hoursSickTime = (TextView)convertView.findViewById(R.id.textview_sicktimesummary_hours);
    		editButton=(Button)convertView.findViewById(R.id.btn_sicktimesummary_edit);
    		editButton.setTag(position); //To get position onClick of button in listview
    		
    		SickTimeEntity objSickTime=new SickTimeEntity();
    		objPosition=(objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()-position);
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_sicktimesummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objSickTime=objAllEvents.sickTimeArrayList.get(objPosition);		
//    		date.setText(objSickTime.date);
    		dateSickTime.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objSickTime.date));
    		notesSicktime.setText(objSickTime.notes);
    		hoursSickTime.setText(objSickTime.hours);
       	 break;
        case TRADETIME:
        	TextView name = (TextView)convertView.findViewById(R.id.textview_tradetimesummary_name);
    		TextView hoursTradeTime = (TextView)convertView.findViewById(R.id.textview_tradetimesummary_hours);
    		editButton=(Button)convertView.findViewById(R.id.btn_tradetimesummary_edit);
    		editButton.setTag(position);
    		
    		TradeTimeEntity objTradeTimeSummary=new TradeTimeEntity();
    		objPosition=(objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()+objAllEvents.sickTimeArrayList.size()-position);
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_tradetimesummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objTradeTimeSummary=objAllEvents.tradeTimeArrayList.get(objPosition);		
    		name.setText(objTradeTimeSummary.name);		
    		hoursTradeTime.setText(objTradeTimeSummary.hours);
    		
    		
    		 if(objTradeTimeSummary.isOweMe.toLowerCase().equals("true"))
    	     {
    	    	 convertView.setBackgroundResource(R.color.green_listview);		 
    	     }
    	     else
    	     {
    	    	 convertView.setBackgroundResource(R.color.red_listview);		    
    	     }
    		
       	 break;
        case VACTIONTIME:
        	TextView startDate = (TextView)convertView.findViewById(R.id.textview_vacationtime_startdate);
    		TextView endDate = (TextView)convertView.findViewById(R.id.textview_vacationtime_enddate);
    		TextView note = (TextView)convertView.findViewById(R.id.textview_vacationtime_note);
    		TextView hour = (TextView)convertView.findViewById(R.id.textview_vacationtime_hour);
    		editButton=(Button)convertView.findViewById(R.id.btn_vacationtimesummary_edit);
    		editButton.setTag(position);
    			
    		VacationTimeEntity objVacationTime=new VacationTimeEntity();
    		objPosition=(objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()+objAllEvents.sickTimeArrayList.size()+objAllEvents.tradeTimeArrayList.size()-position);
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_vacationtimesummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objVacationTime=objAllEvents.vacationTimeArrayList.get(objPosition);		
//    		startDate.setText(objVacationTime.startDate);
//    		endDate.setText(objVacationTime.endDate);
    		startDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objVacationTime.startDate));
    		endDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objVacationTime.endDate));
    		note.setText(objVacationTime.note);
    		hour.setText(objVacationTime.hour);
       	 break;
        case WORKERSCOMP:
        	TextView injuryDate = (TextView)convertView.findViewById(R.id.textview_workerscomp_injurydate);
    		TextView returnDate = (TextView)convertView.findViewById(R.id.textview_workerscomp_returndate);
    		TextView notesWorkersComp = (TextView)convertView.findViewById(R.id.textview_workerscomp_notes);
    		editButton=(Button)convertView.findViewById(R.id.btn_workerscompsummary_edit);
    		editButton.setTag(position);
    			
    		WorkersCompEntity objWorkersComp=new WorkersCompEntity();
    		objPosition=(objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()+objAllEvents.sickTimeArrayList.size()+objAllEvents.tradeTimeArrayList.size()+objAllEvents.vacationTimeArrayList.size()-position);
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_workerscompsummary_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objWorkersComp=objAllEvents.workersCompArrayList.get(objPosition);		
//    		injuryDate.setText(objWorkersComp.injuryDate);
//    		returnDate.setText(objWorkersComp.returnDate);
    		injuryDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objWorkersComp.injuryDate));
    		returnDate.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objWorkersComp.returnDate));
    		notesWorkersComp.setText(objWorkersComp.notes);
       	 break;
        case PAYDAY:
        	TextView dateTextView3 = (TextView)convertView.findViewById(R.id.textview_payday_date);
        	TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_payday_name);
        	TextView frequencyTextView2 = (TextView)convertView.findViewById(R.id.textview_payday_frequency);
    		
    		//multiplying -1 becoz getting reverse position in negative
    		
    		PaydayEntity objPaydayEntity = new PaydayEntity();
    		objPosition=(objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()+objAllEvents.sickTimeArrayList.size()+objAllEvents.tradeTimeArrayList.size()+objAllEvents.vacationTimeArrayList.size()+objAllEvents.workersCompArrayList.size()-position);
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_payday_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objPaydayEntity = objAllEvents.paydayArrayList.get(objPosition);			
//    		date.setText(objKellyDayEntity.date);
    		dateTextView3.setText(MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(objPaydayEntity.PayDate));
    		nameTextView.setText(objPaydayEntity.PayDayName);
    		frequencyTextView2.setText(objPaydayEntity.Frequency);
    		break;
        case HOLIDAY:
        	TextView typpeTextView = (TextView)convertView.findViewById(R.id.textview_holiday_name);
        	TextView frequencyTextView = (TextView)convertView.findViewById(R.id.textview_holiday_frequency);
    		
    		//multiplying -1 becoz getting reverse position in negative
    		
    		HolidayEntity objHolidayEntity = new HolidayEntity();
    		objPosition=(objAllEvents.appointmentArrayList.size()+objAllEvents.compTimeArrayList.size()+objAllEvents.kellyDayArrayList.size()+objAllEvents.overTimeArrayList.size()+objAllEvents.sickTimeArrayList.size()+objAllEvents.tradeTimeArrayList.size()+objAllEvents.vacationTimeArrayList.size()+objAllEvents.workersCompArrayList.size()+ objAllEvents.paydayArrayList.size()-position);
    		if(objPosition==0){
    			LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linearlayout_holiday_titlelayout);
    			layout.setVisibility(View.VISIBLE);
    		}
    		if(objPosition<0)
    			objPosition*=-1;
    		objHolidayEntity= objAllEvents.holidayArrayList.get(objPosition);			
//    		date.setText(objKellyDayEntity.date);
    		typpeTextView.setText(objHolidayEntity.HolidayType);
    		frequencyTextView.setText(objHolidayEntity.Frequency);
    		break;
    }

		
		return convertView;
	}

}

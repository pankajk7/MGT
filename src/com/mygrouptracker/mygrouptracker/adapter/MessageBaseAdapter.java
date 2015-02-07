package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageBaseAdapter extends BaseAdapter{

	MessageEntity objMessageEntity;
	List<MessageEntity> listMessageEntity;
	LayoutInflater inflater;
	
	public MessageBaseAdapter(Activity activity, List<MessageEntity> list)
	{
		listMessageEntity=list;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(listMessageEntity==null){
			;
			return 0;
		}
		return listMessageEntity.size();
	}

	@Override
	public Object getItem(int position) {
		return listMessageEntity.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{			
			convertView = inflater.inflate(R.layout.messages_activity_items, parent,false);
		}
		
		TextView date = (TextView)convertView.findViewById(R.id.textview_messagesactivity_date);
		TextView title = (TextView)convertView.findViewById(R.id.textview_messagesactivity_header);
		TextView description = (TextView)convertView.findViewById(R.id.textview_messagesactivity_text);
				
		objMessageEntity=listMessageEntity.get(position);		
		date.setText(MgtDateFormat.dateFormatMMddYYYYCalendar(objMessageEntity.date));
		title.setText(objMessageEntity.title);
		description.setText(objMessageEntity.description);
		
		if(position==0)
	      {
	    	  convertView.setBackgroundResource(R.drawable.top_round_corners);
	      }
	    else if(position==getCount()-1)
	      {
	    	  convertView.setBackgroundResource(R.drawable.bottom_round_corners);
	      }
		
		return convertView;		
	}

}

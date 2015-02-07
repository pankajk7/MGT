package com.mygrouptracker.mygrouptracker.adapter;

import java.util.ArrayList;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.entity.States;
import com.mygrouptracker.mygrouptracker.entity.UserType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserTypeBaseAdapter extends BaseAdapter{
	Context context;
	ArrayList<UserType> stringList;
	LayoutInflater inflater;
	
	public UserTypeBaseAdapter(Context context, ArrayList<UserType> stringList){
		this.context = context;
		this.stringList = stringList;
		inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(stringList == null || stringList.size() == 0){
			return 0;
		}
		return stringList.size();
	}

	@Override
	public Object getItem(int position) {
		return stringList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_dialog__items_layout,
					parent, false);			
		}
		
		TextView text = (TextView) convertView.findViewById(R.id.textview_list_dialog_text);
		
		text.setText(stringList.get(position).type);
		
		return convertView;
	}

}

package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class BaseTabActivity extends TabActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.base_tab_activity);
		try
		{      
	        setContentView(R.layout.base_tab_activity);	 
	        setTabs();
		}
		catch(Exception ex)
		{
			ex.getMessage();
		}
	}
	
	public  void setTabs() {
		try
		{
		addTab("Calendar", R.drawable.tab_calendar, Calendar.class);
		addTab("Add Events", R.drawable.tab_events, CalendarEvents.class);
		addTab("Sync", R.drawable.tab_sync, SyncingActivity.class);
		addTab("Settings", R.drawable.tab_settings, CalendarSettingsActivity.class);
		}
		catch(Exception ex)
		{
			ex.getMessage();
		}
	}

	public  void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabSpec spec = tabHost.newTabSpec(labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.textview_tabtext);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.imageview_tabicon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}

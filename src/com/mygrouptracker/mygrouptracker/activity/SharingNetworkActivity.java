package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.adapter.SharingNetworkAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.SharingNetwork;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SharingNetworkActivity {
	
	Context context;
	TextView dateTextView;
	TextView emailTextView;
	Button deleteButton;
	Button addButton;
	ListView sharingNetworkListView;
	ArrayList<SharingNetwork> shaingNetworkArrayList;
	
	public SharingNetworkActivity(Context context){
		this.context=context;
	}
	
	public void createLayoutSharingNetwork(){
		
		FrameLayout baseLayout = (FrameLayout) ((CalendarSettingsActivity) context)
				.findViewById(R.id.layout_basecontainer);
		LinearLayout inflateLayout = (LinearLayout) ((CalendarSettingsActivity) context)
				.getLayoutInflater().inflate(R.layout.sharing_network_activity,null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);
		
		dateTextView=(TextView)((CalendarSettingsActivity)context).findViewById(R.id.textview_sharingnetworkitems_date);
		emailTextView=(TextView)((CalendarSettingsActivity)context).findViewById(R.id.textview_sharingnetworkitems_email);
		sharingNetworkListView=(ListView)((CalendarSettingsActivity)context).findViewById(R.id.listview_sharingnetwork_list);
		addButton=(Button)((CalendarSettingsActivity)context).findViewById(R.id.btn_sharingnetwork_addnewmember);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				CalendarSettingsActivity.calendarSettingsState="SharingNetwork";
 		 		new AddMemberActivity(context).createAddMemberLayout();
			}
		});
		
		try{
			new BackgroundNetwork(context) {
				
				@Override
				protected Void doInBackground(Void[] params) {
									
					DataEngine dataEngine = new DataEngine(context);
					shaingNetworkArrayList=dataEngine.getSharingNetworkList();
					
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
										
					try
					{
						SharingNetworkAdapter adapter=new SharingNetworkAdapter(context, shaingNetworkArrayList);
						sharingNetworkListView.setAdapter(adapter);	
						adapter.notifyDataSetChanged();
					
					}
					catch(Exception e){
						
						Log.e(">>>Error>>",e.getMessage());
					}
				};
			}.execute();
		}
		catch(Exception e){
			
		}
		
	}

}

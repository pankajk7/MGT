package com.mygrouptracker.mygrouptracker.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.CalendarSettingsActivity;
import com.mygrouptracker.mygrouptracker.activity.EditKellyDay;
import com.mygrouptracker.mygrouptracker.activity.SharingNetworkActivity;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.SharingNetwork;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SharingNetworkAdapter extends BaseAdapter {

	Context context;
	ArrayList <SharingNetwork> sharingNetworkArrayList;
	SharingNetwork objSharingNetwork;
	LayoutInflater inflater;
	TextView dateTextView;
	TextView emailTextView;
	Button deleteButton;
	Button addMemberButton;
	String response;
	List<NameValuePair> nameValuePairs;
	String eventNameString="network";
	
	
	public SharingNetworkAdapter(Context context, ArrayList <SharingNetwork> list){
		this.context=context;
		sharingNetworkArrayList=list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(sharingNetworkArrayList==null){
			;
			return 0;
		}
		return sharingNetworkArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return sharingNetworkArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.sharing_network_items, parent,false);
		}
		
		dateTextView = (TextView)convertView.findViewById(R.id.textview_sharingnetworkitems_date);
		emailTextView = (TextView)convertView.findViewById(R.id.textview_sharingnetworkitems_email);
		addMemberButton=(Button)convertView.findViewById(R.id.btn_sharingnetwork_addnewmember);
		deleteButton=(Button)convertView.findViewById(R.id.btn_sharingnetworkitems_delete);		
		deleteButton.setTag(position);
		
		objSharingNetwork=new SharingNetwork();
		objSharingNetwork = sharingNetworkArrayList.get(position);
		
//		date.setText(objKellyDayEntity.date);
		dateTextView.setText(MgtDateFormat.dateFormatYYMMDD(objSharingNetwork.eventDate));
		emailTextView.setText(objSharingNetwork.email);
		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer)v.getTag();
				objSharingNetwork=new SharingNetwork();
				objSharingNetwork=sharingNetworkArrayList.get(position); //getting object from list by position
//				Intent intent=new Intent(context,EditKellyDay.class);
//				intent.putExtra("KellyDayEntity", objKellyDayEntity);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
				try{
					new BackgroundNetwork(context) {
						
						@Override
						protected Void doInBackground(Void[] params) {
							
							
							DataEngine dataEngine = new DataEngine(context);
							response=dataEngine.deleteRecord(eventNameString+"/"+objSharingNetwork.id);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
							dlgAlert.setTitle("Message");
							dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
							dlgAlert.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
//											calendarSettingsState="CalendarSettings";
											new SharingNetworkActivity(context).createLayoutSharingNetwork();
										}
									});
							dlgAlert.setCancelable(true);
							dlgAlert.create().show();
						};
					}.execute();
				}
				catch(Exception e){
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				 }
			}
		});
		
		return convertView;
	}

}

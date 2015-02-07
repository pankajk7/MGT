package com.mygrouptracker.mygrouptracker.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;

import com.google.android.gms.maps.model.LatLng;
import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterCompTime;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.SingleNameListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.StationLocationBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.entity.Station;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.CallBackClass;
import com.mygrouptracker.mygrouptracker.utility.SingleNameListPopulate;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class StationLocation extends BaseClass implements LocationListener,
GpsStatus.Listener{

	// XML node keys
    static final String KEY_TAG = "stations"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    
    String rawxmlString="stations.xml";
	ListView listview;
	StationLocationBaseAdapter adapter = null;
//    List<SingleNameListEntity> stationDataCollection;
    View view;
    Context context;
    ArrayList<Station> stationArrayList = new ArrayList<Station>();
    Location location;
	protected LocationManager locationManager;
	private CountDownTimer countDownTimer;
	private final long startTime = 20 * 1000;
	private final long interval = 1 * 1000;
	LatLng startLatLng;
	Double startLat;
	Double startLng;
	FrameLayout framelayout;
	CallBackClass startCallBackClass;
    
//    public StationLocation(Context context)
//    {
//    	this.context=context;
//    }
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createLayoutStationLocation();
		
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
    }
    
    @Override
	public void onBackPressed() {
    	finish();
    }
    
    public void createLayoutStationLocation()
    {
    		framelayout = (FrameLayout) findViewById(R.id.container_layout);
    		framelayout.removeAllViews();
    		framelayout.addView(getLayoutInflater().inflate(
				R.layout.station_location, null));
    			
    			
    			listview = (ListView)findViewById(R.id.list_stationlocations);
    			
//    			try {
    				
    				//this.title.setTextColor(Color.BLUE);
    				
//    				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//    		        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//    		        Document doc = docBuilder.parse (context.getAssets().open(rawxmlString));

    		        try {
    					new BackgroundNetwork(StationLocation.this) {
    						protected Void doInBackground(Void[] params) {
    							try
    							{
    							DataEngine dataEngine = new DataEngine(StationLocation.this);
    							 
    							 stationArrayList = dataEngine.getStationList();
    							}
    							catch(Exception e)
    							{
    								
    							}
    							return null;
    						};

    						protected void onPostExecute(Void result) {
    							super.onPostExecute(result);	
    							try
    							{
    								
    								
    								StationLocationBaseAdapter stationAdapter = new StationLocationBaseAdapter(
    										StationLocation.this, stationArrayList);
    								listview.setAdapter(stationAdapter);
    							
    								stationAdapter.notifyDataSetChanged();
    							}
    							catch(Exception e){
    								System.out.print(">>>Exception BenefitsActivity>>>"+e.toString()+">>>"+e.getMessage());
    							}
    		        	        		
    							
    						};
    					}.execute();
    				} catch (Exception e) {
    					System.out.println(">>>Exception " + e + "  >>> Message : "
    							+ e.getMessage());
    				}

    			
    		        listview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							
							Station stationObj = new Station();
							stationObj = stationArrayList.get(arg2);
							try {
//								startLatLng = new LatLng(startLat,startLng);
								Intent intent=new Intent(StationLocation.this, LocationActivity.class);
//								Toast.makeText(context, stationObj.latitude, Toast.LENGTH_LONG).show();
//								System.out.print(">>>>>"+stationObj.latitude+">>>>>>>>");
								intent.putExtra("Latitude", stationObj.latitude);
								intent.putExtra("Longitude", stationObj.longitude);
								intent.putExtra("name", stationObj.stationName);
								intent.putExtra("address", stationObj.stationAddress);
								intent.putExtra("phone", stationObj.stationPhone);
//								intent.putExtra("StartLatitude", String.valueOf(startLat));
//								intent.putExtra("StartLongitude", String.valueOf(startLng));
								startActivity(intent);
								finish();
								
						}
							 catch (Exception e) {
									System.out.println(">>>Exception " + e + "  >>> Message : "
											+ e.getMessage());
								}
							
						}
					});
    			
//    		backButton=(Button)((Home_Navigation)context).findViewById(R.id.btn_back);
//    		backButton.setVisibility(View.VISIBLE);
//    		backButton.setOnClickListener(new OnClickListener() {
//    		
//    		@Override
//    		public void onClick(View v) {
//    			finish();
//    		 }
//    		});
    }

    
    public void requestupdate(String provider) {
		try {
			if (provider.equalsIgnoreCase("gps")) {
				if (locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					locationManager.addGpsStatusListener(this);
					// countDownTimer.start();
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 50, 10,
							StationLocation.this);
				} else {
					countDownTimer.cancel();
					countDownTimer.onFinish();
					// Toast.makeText(getApplicationContext(),
					// "Location Service Disabled, Please turn on GPS",
					// Toast.LENGTH_SHORT).show();
				}

			} 
			if (provider.equalsIgnoreCase("network")) {
//				countDownTimer.start();
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 50, 10,
						StationLocation.this);
			}
		} catch (Exception e) {

		}
	}
    
	@Override
	public void onGpsStatusChanged(int event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		startLat=location.getLatitude();
		startLng=location.getLongitude();
		locationManager.removeUpdates(this);
		countDownTimer.onFinish();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    
    
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.station_location);
//		
//		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
//		framelayout.addView(getLayoutInflater().inflate(R.layout.station_location, null));
//		
//try {
//			
//			//this.title.setTextColor(Color.BLUE);
//			
//			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//	        Document doc = docBuilder.parse (getAssets().open(rawxmlString));
//
//	        stationDataCollection = new ArrayList<SingleNameListEntity>();
//	        	        			
//	        SingleNameListPopulate list=new SingleNameListPopulate();
//	        stationDataCollection=list.populateListView(doc,rawxmlString,KEY_TAG);
//	
//	        StationLocationBaseAdapter adapter = new StationLocationBaseAdapter(StationLocation.this,stationDataCollection);						
//			listview = (ListView) findViewById(R.id.list_stationlocations);					
//			listview.setAdapter(adapter);	
//			listview.setSelector(R.drawable.tranparent_selection);
//		}
//		
//		catch (IOException ex) {
//			Log.e("Error", ex.getMessage());
//		}
//		catch (Exception ex) {
//			Log.e("Error", "Loading exception");
//		}
//
//	backButton=(Button)findViewById(R.id.btn_back);
//	backButton.setVisibility(View.VISIBLE);
//	backButton.setOnClickListener(new OnClickListener() {
//	
//	@Override
//	public void onClick(View v) {
//		finish();
//	 }
//	});
//		
//	}

}

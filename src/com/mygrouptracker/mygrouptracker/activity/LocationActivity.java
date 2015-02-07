package com.mygrouptracker.mygrouptracker.activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.utility.DirectionHelper;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends FragmentActivity implements
		LocationListener, GpsStatus.Listener {

	GoogleMap googleMap = null;
	LatLng startLatLng;
	LatLng endLatLng;
	// LatLng jaipur=new LatLng(26.9260, 75.8235);
	// LatLng endLatLng= new LatLng(29.760, -95.369);//houston
	// LatLng endLatLng= new LatLng(32.780, -96.800);//dallas
	// LatLng endLatLng= new LatLng(26.912, 75.787);//Jaipur

	Location location;
	protected LocationManager locationManager;
	private CountDownTimer countDownTimer;
	private final long startTime = 5 * 1000;
	private final long interval = 1 * 1000;
	Double endLat;
	Double endLng;
	Double startLat;
	Double startLng;
	String name;
	String address;
	String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.location_activity);
		if (googleMap == null)
			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

		try {
			Intent intent = getIntent();
			endLat = Double.valueOf(intent.getStringExtra("Latitude"));
			endLng = Double.valueOf(intent.getStringExtra("Longitude"));
			name = intent.getStringExtra("name");
			address = intent.getStringExtra("address");
			phone = intent.getStringExtra("phone");
		} catch (Exception e) {
			e.printStackTrace();
		}

		endLatLng = new LatLng(endLat, endLng);
		// startLat=Double.valueOf(intent.getStringExtra("StartLatitude"));
		// startLng=Double.valueOf(intent.getStringExtra("StartLongitude"));
		// startLatLng=new LatLng(startLat, startLng);

		googleMap.setMyLocationEnabled(true);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		MarkerOptions marker = new MarkerOptions();

		 marker.title(name+"\n").position(new LatLng(endLat, endLng)).snippet(address+"\n"+",(M)"+phone);
		 googleMap.addMarker(marker);
		// google
		// moving camera with animation to Latlng
		googleMap.animateCamera(CameraUpdateFactory
				.newLatLngZoom(endLatLng, 12.0f));
		
//		showMyLocation();

		// startLatLng=new LatLng(googleMap.getMyLocation().getLatitude(),
		// googleMap.getMyLocation().getLongitude());
		// initilizeMap();
		// try
		// {
		//
		// locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50,10,LocationActivity.this);
		// locationManager.addGpsStatusListener(this);
		// countDownTimer=new CountDownTimer(startTime,interval) {
		//
		// @Override
		// public void onTick(long millisUntilFinished) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onFinish() {
		// // TODO Auto-generated method stub
		// startLatLng = new LatLng(startLat,startLng);
		// initilizeMap();
		// }
		// }.start();
		//
		// }
		// catch(Exception ex)
		// {
		// System.out.print(">>>>Error>>>>"+ex.toString()+">>>>"+ex.getMessage());
		// }
	}
	
	public void showMyLocation(){
		countDownTimer = new CountDownTimer(startTime, interval) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				// startLatLng = new LatLng(startLat,startLng);
				// initilizeMap();

				try {
					if (googleMap != null) {
						startLat = googleMap.getMyLocation().getLatitude();
						startLng = googleMap.getMyLocation().getLongitude();

						startLatLng = new LatLng(startLat, startLng);

						DirectionHelper dir = new DirectionHelper(googleMap,
								getApplicationContext());

						dir.getDirections(startLatLng, endLatLng);
						
						

//						MarkerOptions marker = new MarkerOptions();
//
//						 marker.title(name).position(new LatLng(endLat, endLng));
//						 googleMap.addMarker(marker);
						// google
						// moving camera with animation to Latlng
						googleMap.animateCamera(CameraUpdateFactory
								.newLatLngZoom(startLatLng, 12.0f));

					}
				} catch (Exception e) {
					Log.e("Exception>>>", e.toString());
				}
			}
		}.start();
	}

	@SuppressLint("NewApi")
	private void initilizeMap() {

		// if (googleMap == null) {
		// googleMap = ((SupportMapFragment)
		// getSupportFragmentManager().findFragmentById( R.id.map)).getMap();
		// // googleMap = ((MapFragment)
		// getFragmentManager().findFragmentById(R.id.map)).getMap();
		// // Toast.makeText(getApplicationContext(), "Google Map Connected",
		// Toast.LENGTH_SHORT).show();
		// // check if map is created successfully or not
		// if (googleMap == null) {
		// Toast.makeText(getApplicationContext(),
		// "Sorry! unable to create maps", Toast.LENGTH_SHORT)
		// .show();
		// }
		// else
		// {
		startLatLng = new LatLng(googleMap.getMyLocation().getLatitude(),
				googleMap.getMyLocation().getLongitude());
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(startLatLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(5));
		DirectionHelper directionObj = new DirectionHelper(googleMap,
				LocationActivity.this);
		directionObj.getDirections(startLatLng, endLatLng);
		// }
		// }
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onGpsStatusChanged(int event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {

		Toast.makeText(
				getApplicationContext(),
				"> Latitude > " + location.getLatitude() + "\n\n> Longitude > "
						+ location.getLongitude(), Toast.LENGTH_LONG).show();

		startLat = location.getLatitude();
		startLng = location.getLongitude();
		locationManager.removeUpdates(this);
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

}

package com.mygrouptracker.mygrouptracker.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mygrouptracker.mygrouptracker.data.DirectionsJSONParser;

public class DirectionHelper {
	
	GoogleMap map;
	Context appcontext;
	ArrayList<LatLng> markerPoints;
	
	
	
	public DirectionHelper(GoogleMap mp,Context context)
	{
		map=mp;
		appcontext=context;
		
		//map.setOnMapClickListener(this);
	}
	private String getDirectionsUrl(LatLng origin,LatLng dest){

	    // Origin of route
	    String str_origin = "origin="+origin.latitude+","+origin.longitude;

	    // Destination of route
	    String str_dest = "destination="+dest.latitude+","+dest.longitude;

	    // Sensor enabled
	    String sensor = "sensor=false";

	    // Building the parameters to the web service
	    String parameters = str_origin+"&"+str_dest+"&"+sensor;

	    // Output format
	    String output = "json";

	    String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

	    return url;
	}

	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		  if(markerPoints.size()>1){
              markerPoints.clear();
              map.clear();
          }

          markerPoints.add(point);

          MarkerOptions options = new MarkerOptions();

          options.position(point);

           if(markerPoints.size()==1){
                 options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
          }else if(markerPoints.size()==2){
              options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
          }

          map.addMarker(options);

          if(markerPoints.size() >= 2){
              LatLng origin = markerPoints.get(0);
              LatLng dest = markerPoints.get(1);

              String url = getDirectionsUrl(origin, dest);

              DownloadTask downloadTask = new DownloadTask();

              downloadTask.execute(url);
          }
      }
	
	
	public void getDirections(LatLng Orign,LatLng Dest)
	{
		 String url = getDirectionsUrl(Orign,Dest);

         DownloadTask downloadTask = new DownloadTask();

         downloadTask.execute(url);
	}
	private String downloadUrl(String strUrl) throws IOException{
	    String data = "";
	    InputStream iStream = null;
	    HttpURLConnection urlConnection = null;
	    try{
	        URL url = new URL(strUrl);

	        urlConnection = (HttpURLConnection) url.openConnection();

	        // Connecting to url
	        urlConnection.connect();

	        // Reading data from url
	        iStream = urlConnection.getInputStream();

	        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

	        StringBuffer sb  = new StringBuffer();

	        String line = "";
	        while( ( line = br.readLine())  != null){
	            sb.append(line);
	        }

	        data = sb.toString();

	        br.close();

	    }catch(Exception e){
	        Log.d("Exception while downloading url", e.toString());
	    }finally{
	        iStream.close();
	        urlConnection.disconnect();
	    }
	    return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String>{

	    // Downloading data in non-ui thread
	    @Override
	    protected String doInBackground(String... url) {

	        // For storing data from web service
	        String data = "";

	        try{
	            // Fetching the data from web service
	            data = downloadUrl(url[0]);
	        }catch(Exception e){
	            Log.d("Background Task",e.toString());
	        }
	        return data;
	    }

	    // Executes in UI thread, after the execution of
	    // doInBackground()
	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);

	        ParserTask parserTask = new ParserTask();
	       

	        // Invokes the thread for parsing the JSON data
	        parserTask.execute(result);
	    }
	  
	}

     int road_distance=0;
	public int getRoadDistance()
	{
		return road_distance;
		
	}
	 class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>>{

		 @Override
		    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

		        JSONObject jObject;
		        List<List<HashMap<String, String>>> routes = null;

		        try{
		            jObject = new JSONObject(jsonData[0]);
		            DirectionsJSONParser parser = new DirectionsJSONParser();

		            // Starts parsing data
		            routes = parser.parse(jObject);
//		            road_distance=parser.getDistance();
		        }catch(Exception e){
		            e.printStackTrace();
		        }
		        return routes;
		    }

	    @Override
	    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
	    	try{
	        ArrayList<LatLng> points = null;
	        PolylineOptions lineOptions = null;
	        MarkerOptions markerOptions = new MarkerOptions();

	        // Traversing through all the routes
	        for(int i=0;i<result.size()-1;i++){
	            points = new ArrayList<LatLng>();
	            lineOptions = new PolylineOptions();

	            // Fetching i-th route
	            List<HashMap<String, String>> path = result.get(i);

	            // Fetching all the points in i-th route
	            for(int j=0;j<path.size();j++){
	                HashMap<String,String> point = path.get(j);

	                double lat = Double.parseDouble(point.get("lat"));
	                double lng = Double.parseDouble(point.get("lng"));
	                LatLng position = new LatLng(lat, lng);

	                points.add(position);
	          }  

	            //Adding Distance to Variables by sunilsoni
	         //   HashMap<String,String> point = path.get(path.size());
	        //    road_distance=Integer.parseInt(point.get("distance"));
	            
	            /////////////////////////////
	            // Adding all the points in the route to LineOptions
	            lineOptions.addAll(points);
	            lineOptions.width(3);
	            lineOptions.color(Color.BLUE);
	        }

	        
	        
	        // Drawing polyline in the Google Map for the i-th route]
	        
	        
	        //getting distance //Get Distance From JsonOBJECT
	        
	        List dlist=new ArrayList<HashMap<String, String>>();
	        dlist=result.get(result.size()-1);
	        HashMap<String, String> md=new HashMap<String, String>();
	        md=(HashMap<String, String>)dlist.get(0);
//	        road_distance=Integer.parseInt(md.get("distance"));
//	        Toast.makeText(appcontext, (road_distance/1000)+" Km.", Toast.LENGTH_LONG).show();
	        //--------To-------------
	        map.addPolyline(lineOptions);
	    	
	    	}
	    	catch (Exception e) {
				// TODO: handle exception
	    		  Toast.makeText(appcontext, "ERROR IN FINDING DISTANCE"+e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	    
	}

}

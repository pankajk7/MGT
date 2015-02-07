package com.mygrouptracker.mygrouptracker.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.mygrouptracker.mygrouptracker.R;

import android.content.Context;
import android.util.Log;

public class JsonParserClass {

	Context context;
	String jsonString;
	
	public JsonParserClass(Context context)
	{
	 this.context=context;	
	}
	
	
	public String getJsonArray()
	{
		JSONObject jsonObject=null;
	try {
	    BufferedInputStream resourceStream = new BufferedInputStream(context.getResources().openRawResource(R.raw.kellyday));
	    BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

	    String line;
	    while((line = reader.readLine()) != null) {
	        jsonString += line;
	    }
	    reader.close();
	    resourceStream.close();
	    //jsonObject=new JSONObject(jsonString);
	}
	catch(Exception ex) {
	    Log.e("myApp", ex.getMessage());
	}
	return jsonString;
	}
}

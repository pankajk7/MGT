package com.mygrouptracker.mygrouptracker.utility;

import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.data.DataEngine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CommonDeleteFunction {

	//it is use to delete 
	public String response;
	public CommonDeleteFunction() {
		// TODO Auto-generated constructor stub
	}
	public void delete( final Context context,final String eventNameString,final String idString,final String message,final String layout)
	{

		
		try{
		new BackgroundNetwork(context) {
			protected Void doInBackground(Void[] params) {
				
				 /* nameValuePairs = new ArrayList<NameValuePair>();
				  nameValuePairs.add(new BasicNameValuePair("Id", idString));
				  nameValuePairs.add(new BasicNameValuePair("Description", descriptionEditText.getText().toString()));
				  nameValuePairs.add(new BasicNameValuePair("IsShare", String.valueOf(shareBoolean)));
				  nameValuePairs.add(new BasicNameValuePair("Name", descriptionEditText.getText().toString()));	
				  String dateTime=eventDateEditText.getText().toString();
				  dateTime=MgtDateFormat.dateFormatSendingToServer(dateTime);
				  nameValuePairs.add(new BasicNameValuePair("EventDate", dateTime));
				  nameValuePairs.add(new BasicNameValuePair("Recurring", String.valueOf(recurringSpinner.getSelectedItemPosition())));*/
				  
				DataEngine dataEngine = new DataEngine(context);
//				dataEngine.postKellyDayTest();	
				response=dataEngine.deleteRecord(eventNameString+"/"+idString);
				return null;
			};

			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
				dlgAlert.setTitle("Message");	
				if(new StatusInfo().getStatusInfo(response).equals("Success"))
				{
					dlgAlert.setMessage(message);
				}
				else
				{
				dlgAlert.setMessage(new StatusInfo().getStatusInfo(response));
				}
				dlgAlert.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {	
							    ((CalendarEvents)context).createLayouts(layout);
							}
							
						});
//				dlgAlert.setCancelable(false);
				dlgAlert.create().show();
				
			};
		}.execute();
	  }
	 catch (Exception e) 
	    {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
		
	
	}
}

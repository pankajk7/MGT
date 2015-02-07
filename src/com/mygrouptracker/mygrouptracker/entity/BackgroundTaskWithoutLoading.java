package com.mygrouptracker.mygrouptracker.entity;
import android.content.Context;
import android.os.AsyncTask;


public class BackgroundTaskWithoutLoading extends AsyncTask<Void, Void, Void> {

	Context context;

	public BackgroundTaskWithoutLoading(Context activity) {
		context = activity;
	}

 	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}

	public void onPostExecuteDeveloperMethodForPublicAccess(Void result) {
		super.onPostExecute(result);
 	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
 	}

	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}
}

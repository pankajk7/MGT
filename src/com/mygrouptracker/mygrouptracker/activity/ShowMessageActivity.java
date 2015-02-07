package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ShowMessageActivity extends BaseClass {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.show_message_layout);
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.show_message_layout, null));
		TextView headingTextView = (TextView) findViewById(R.id.textview_show_message_heading);		
		TextView messageTextView = (TextView) findViewById(R.id.textview_show_message_message);
		
		try {
			Intent intent = getIntent();
			headingTextView.setText(intent.getStringExtra("heading"));
			messageTextView.setText(intent.getStringExtra("message"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		super.onBackPressed();
	}
	
}

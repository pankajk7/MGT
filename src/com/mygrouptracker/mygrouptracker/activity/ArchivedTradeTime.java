package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ArchivedTradeTime extends BaseClass {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.archived_trade_time);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.archived_trade_time, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.archived_trade_time, null));
			
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
	}

}

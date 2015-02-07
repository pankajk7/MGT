package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterCompTime;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterTradeTimeSummary;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TradeTimeSummary extends BaseClass {

	TradeTimeEntity objTradeTime=new TradeTimeEntity();
	ListView listTradeTime;
	Button addTradeTime;
	Button viewArchiveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.trade_time_summary);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.trade_time_summary, null));
		
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.trade_time_summary, null));
			
		BaseAdapterTradeTimeSummary tradetimeAdapter = new BaseAdapterTradeTimeSummary(this,objTradeTime.getTradeTimeSummary());
        
		listTradeTime=(ListView)findViewById(R.id.list_tradetimesummary);
		listTradeTime.setAdapter(tradetimeAdapter);
        
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		addTradeTime=(Button)findViewById(R.id.btn_tradetimesummary_addtradetime);
		addTradeTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TradeTimeSummary.this, AddTradeTime.class);
				startActivity(intent);
			}
		});
		
		viewArchiveButton=(Button)findViewById(R.id.btn_tradetimesummary_viewarchive);
		viewArchiveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TradeTimeSummary.this, TradeTimeArchive.class);
				startActivity(intent);
			}
		});
	}

}

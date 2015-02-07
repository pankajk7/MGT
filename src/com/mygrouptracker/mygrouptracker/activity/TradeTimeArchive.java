package com.mygrouptracker.mygrouptracker.activity;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterTradeTimeArchive;
import com.mygrouptracker.mygrouptracker.adapter.BaseAdapterTradeTimeSummary;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeArchiveEntity;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

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
import android.widget.SlidingDrawer;

public class TradeTimeArchive extends BaseClass {

	TradeTimeArchiveEntity objTradeTimeArchive=new TradeTimeArchiveEntity();
	ListView listTradeTimeArchive;
	
	Button emailButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
//		setContentView(R.layout.trade_time_archive);
		
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.trade_time_archive, null));
		emailButton = (Button) findViewById(R.id.btn_tradetimearchive_email);
//		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.container_layout);
//		linearLayout.addView(getLayoutInflater().inflate(R.layout.trade_time_archive, null));
			
//		BaseAdapterTradeTimeArchive tradetimearchiveAdapter = new BaseAdapterTradeTimeArchive(this,objTradeTimeArchive.getTradeTimeArchive());
        
		listTradeTimeArchive=(ListView)findViewById(R.id.list_tradetimearchive);
//		listTradeTimeArchive.setAdapter(tradetimearchiveAdapter);
        
		backButton=(Button)findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			finish();
		 }
		});
		
		emailButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				for (TradeTimeArchive obj : kellyDayList) {
////					listString += "\n" + "Event " + (i++) + "\n";
//					listString += "Date :- " + MgtDateFormat.dateFormatMMDDYYYYTIMECalendar(obj.date) + "\n";
//					listString += "-----------------" + "\n";
//				}
//				Intent intent = new Intent(Intent.ACTION_SEND);
//				intent.setType("plain/text");
//				intent.putExtra(
//						Intent.EXTRA_EMAIL,
//						new String[] { ""
//								+ LoginActivity.userNameString + "" });
//				intent.putExtra(Intent.EXTRA_SUBJECT,
//						"KellyDay Summary");
//				intent.putExtra(Intent.EXTRA_TEXT, listString);
//				startActivity(Intent.createChooser(intent, ""));
			}
		});		
	}

}

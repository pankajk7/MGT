package com.mygrouptracker.mygrouptracker.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.NameValuePair;
import org.w3c.dom.Document;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.LinksBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.MessageBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.SingleNameListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.SocialMediaBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.Links;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.entity.SocialMedia;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.SingleNameListPopulate;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class SocialMediaActivity extends BaseClass {

	// XML node keys
    static final String KEY_TAG = "socialmedia"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    
    String rawxmlString="socialmedia.xml";
	ListView listview;
	ListBaseAdapter adapter = null;
    SocialMedia socialMediaObj=new SocialMedia();
    ArrayList<SocialMedia> socialMediaArrayList;
    ArrayList<Links> linksArrayList=new ArrayList<Links>();
	Links linkObj=new Links();
    View view;
    Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.social_media_activity);
		

		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(R.layout.social_media_activity, null));
		
		listview = (ListView) findViewById(R.id.list_socialmedia);		
		
		 try {
				new BackgroundNetwork(SocialMediaActivity.this) {
					protected Void doInBackground(Void[] params) {
						
						DataEngine dataEngine = new DataEngine(SocialMediaActivity.this);
						linksArrayList=dataEngine.getSocialMediaList();
						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);						
						
						LinksBaseAdapter adapter = new LinksBaseAdapter(SocialMediaActivity.this,linksArrayList);						
									
						listview.setAdapter(adapter);	
						adapter.notifyDataSetChanged();
					};
				}.execute();
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}
		 
		 listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				linkObj=linksArrayList.get(arg2);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkObj.linkAddress));
				startActivity(browserIntent);
			}
		});

		backButton=(Button) findViewById(R.id.btn_baselayout_backbutton);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

}

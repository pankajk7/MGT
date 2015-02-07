package com.mygrouptracker.mygrouptracker.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.adapter.BenefitsBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.SingleNameListBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.Links;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
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

public class ResourcesActivity extends BaseClass {

	// XML node keys
    static final String KEY_TAG = "resource"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    
    String rawxmlString="resource.xml";
	ListView resourceListView;
	ListBaseAdapter adapter = null;
    List<SingleNameListEntity> mygroupsList;
    View view;
    Button backButton;
    ArrayList<Links> linksArrayList;
    Links linkObj;
    int expensePosition;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.resources_activity);
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.resources_activity, null));
		resourceListView = (ListView) findViewById(R.id.list_resourceactivity);
		try 
		{			
//			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//	        Document doc = docBuilder.parse (getAssets().open(rawxmlString));
//
//	        mygroupsList = new ArrayList<SingleNameListEntity>();
//	        SingleNameListPopulate list=new SingleNameListPopulate();
//	        mygroupsList=list.populateListView(doc,rawxmlString,KEY_TAG);
	
//	        SingleNameListBaseAdapter adapter = new SingleNameListBaseAdapter(ResourcesActivity.this,mygroupsList);						
//	        resourceListView = (ListView) findViewById(R.id.list_resourceactivity);					
//	        resourceListView.setAdapter(adapter);	
//			//listview.setSelector(R.drawable.tranparent_selection);
//	        resourceListView.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					Intent intent;
//					switch(arg2)
//					{
//					 case 7: intent=new Intent(ResourcesActivity.this,ExpenseTracker.class);
//					 		 startActivity(intent);
//					 		 break;
//					 		 
//					 default: 
//						 	 break;
//					}
//					
//				}
//			});
			new BackgroundNetwork(ResourcesActivity.this) {
				protected Void doInBackground(Void[] params) {
					
					DataEngine dataEngine = new DataEngine(ResourcesActivity.this);
					linksArrayList=dataEngine.getResourceList();
					
					//Additional item for the list
					linksArrayList.add(new Links("Expense Tracker",""));
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);	
					try
					{
						BenefitsBaseAdapter adapter = new BenefitsBaseAdapter(ResourcesActivity.this,linksArrayList);
						resourceListView.setAdapter(adapter);	
						adapter.notifyDataSetChanged();
						
						expensePosition=linksArrayList.size()-1;
							
							
					}
					catch(Exception e){
						System.out.print(">>>Exception>>>"+e.toString()+">>>"+e.getMessage());
					}
				};
			}.execute();
		}
		catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}

        resourceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Intent intent;
				
				if(expensePosition==arg2){
					intent=new Intent(ResourcesActivity.this,ExpenseTracker.class);
				 	startActivity(intent);
				}
				else{ 		 
				 linkObj=linksArrayList.get(arg2);
				 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkObj.linkAddress));
				 startActivity(browserIntent);
				}
				
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

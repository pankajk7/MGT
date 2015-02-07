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
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.MessageBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.SingleNameListBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MessageListPopulate;
import com.mygrouptracker.mygrouptracker.utility.SingleNameListPopulate;

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

public class MessagesActivity extends BaseClass {
    
	ListView listview;
	ListBaseAdapter adapter = null;
    List<MessageEntity> messageList;
    MessageEntity messageObj=new MessageEntity();
    View view;
    Button backButton;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.messages_activity);
		
		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
		framelayout.addView(getLayoutInflater().inflate(R.layout.messages_activity, null));
		listview = (ListView) findViewById(R.id.list_messagesactivity);	
		
		  try {
				new BackgroundNetwork(MessagesActivity.this) {
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngine = new DataEngine(MessagesActivity.this);						
						messageList=dataEngine.getMessagesList(LoginActivity.userType);
						return null;
					};

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);	
						try
						{
							MessageBaseAdapter adapter = new MessageBaseAdapter(MessagesActivity.this,messageList);						
										
							listview.setAdapter(adapter);	
							adapter.notifyDataSetChanged();
						 
						}
						catch(Exception e){
							System.out.print(">>>Exception MessageActivity>>>"+e.toString()+">>>"+e.getMessage());
						}
					};
				}.execute();
				
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent(MessagesActivity.this, ShowMessageActivity.class); 
						intent.putExtra("heading", messageList.get(arg2).title);
						intent.putExtra("message", messageList.get(arg2).description);
						startActivity(intent);
					}
				});
				
			} catch (Exception e) {
				System.out.println(">>>Exception " + e + "  >>> Message : "
						+ e.getMessage());
			}						
		
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

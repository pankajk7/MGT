package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.R.menu;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.Banners;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseTabTitleActivity extends Activity {

	Button backButton;
	TextView staticBannerTextView;
	LinearLayout statLinearLayout;
	public static ArrayList<Banners> bannersArrayList;
    Banners objBanners;
    Boolean isStatic=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_tab_title_activity);
//		FrameLayout framelayout=(FrameLayout)findViewById(R.id.container_layout);
//		framelayout.addView(getLayoutInflater().inflate(R.layout.shift_type, null));
		backButton=(Button)findViewById(R.id.btn_backbutton);
		staticBannerTextView=(TextView)findViewById(R.id.textview_basetabtitle_staticbanner);
		statLinearLayout=(LinearLayout)findViewById(R.id.layout_basetabtitleActivity_static);
//		if(bannersArrayList==null)
//			getBanners(isStatic);
//		else{
//			objBanners=bannersArrayList.get(0);
//			staticBannerTextView.setText(objBanners.description);
//		}
		
		Drawable d1  = getResources().getDrawable(R.drawable.logo);
		statLinearLayout.setBackgroundDrawable(d1);
		 
		statLinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				try{
//					String url=objBanners.link.toString();
//					if (!url.startsWith("http://") && !url.startsWith("https://"))
//						   url = "http://" + url;
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mygrouptracker.com/img/logo.png"));
					startActivity(browserIntent);
				}
				catch(Exception e){
					Log.e("HomeNavigationException>>>", e.toString());
				}
		
			}
		});
			
	}
	
	public void getBanners(Boolean bool){
		final Boolean isStaticBoolean=bool;
		objBanners=new Banners();
		bannersArrayList=new ArrayList<Banners>();
		try{
			new BackgroundNetwork(BaseTabTitleActivity.this) {
				protected Void doInBackground(Void[] params) {
					
					
					DataEngine objDataEngine=new DataEngine(BaseTabTitleActivity.this);
					bannersArrayList=objDataEngine.getBannersList(isStaticBoolean);
					
					return null;
				};				
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					
					if(isStaticBoolean==true)
					{
						try{
							objBanners=bannersArrayList.get(0);
							staticBannerTextView.setText(objBanners.description);
							
						}
						catch(Exception ex){
							Log.e("HomeNavigationException>>>", ex.toString());
//							System.out.print(">>>HomeNavigation_getBanner_Exception>>"+ex.toString()+">>>"+ex.getMessage())
						}
				   }
					
				};
			}.execute();
		}
		catch(Exception e){
			
		}
	}

}

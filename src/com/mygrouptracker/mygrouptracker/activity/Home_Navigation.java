package com.mygrouptracker.mygrouptracker.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.drawable;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.MessageBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.Banners;
import com.mygrouptracker.mygrouptracker.utility.listview_populate;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Home_Navigation extends BaseClass {

	// XML node keys
	static final String KEY_TAG = "home"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_ICON = "icon";
	static final String KEY_NAME = "name";

	public static String homeStateString = "finish";
	String rawxmlString = "home.xml";
	ListView listview;
	ListBaseAdapter adapter = null;
	List<HashMap<String, String>> homeDataCollection;
	View view;
	FrameLayout framelayout;
	ArrayList<Banners> bannersArrayList;
	Banners objBanners;
	Boolean isStatic;
	public static int countMessage;
	List<MessageEntity> messageList;

	// Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.home_navigation);

		// isStatic=true;
		// getBanners(isStatic);
		// isStatic=false;
		// getBanners(isStatic);

		getMessagesCount();
		

		homeStateString = "finish";

		// backButton=(Button)findViewById(R.id.btn_baselayout_backbutton);
		// backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (homeStateString.equals("HomeNavigation"))
					createHomeLayout();
				else if (homeStateString.equals("CalendarActivity"))
					createLayouts("CalendarActivity");
				else if (homeStateString.equals("StationActivity"))
					createLayouts("StationActivity");
			}
		});

		// FrameLayout
		// framelayout=(FrameLayout)findViewById(R.id.container_layout);
		// framelayout.addView(getLayoutInflater().inflate(R.layout.home_navigation,
		// null));

		// LinearLayout
		// linearLayout=(LinearLayout)findViewById(R.id.container_layout);
		// linearLayout.addView(getLayoutInflater().inflate(R.layout.home_navigation,
		// null));

		// LayoutInflater inflater = (LayoutInflater)
		// this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
		// view = inflater.inflate(R.layout.home_navigation, null);
		//
		// ListView listview=(ListView) view.findViewById(R.id.list);
		// listview.setBackgroundResource(R.drawable.listview_rounded_corners);

		// listview=(ListView)findViewById(R.id.listView1);
	}

	@Override
	protected void onResume() {
		getShift();
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		if (homeStateString.equals("HomeNavigation"))
			createHomeLayout();
		else if (homeStateString.equals("CalendarActivity"))
			createLayouts("CalendarActivity");
		else if (homeStateString.equals("StationActivity"))
			createLayouts("StationActivity");
		else if (homeStateString.equals("finish"))
			finish();
		// else if (homeStateString.equals("CompTimeSummary"))
		// createLayouts("CompTimeSummary");
		// else if (homeStateString.equals("KellyDaySummary"))
		// createLayouts("KellyDaySummary");
		// else if (homeStateString.equals("OverTimeSummary"))
		// createLayouts("OverTimeSummary");
		// else if (homeStateString.equals("SickTimeSummary"))
		// createLayouts("SickTimeSummary");
		// else if (homeStateString.equals("TradeTimeSummary"))
		// createLayouts("TradeTimeSummary");
		// else if (homeStateString.equals("VacationTimeSummary"))
		// createLayouts("VacationTimeSummary");
		// else if (homeStateString.equals("WorkersCompSummary"))
		// createLayouts("WorkersCompSummary");
		else
			finish();
	}

	public void createHomeLayout() {
		homeStateString = "finish";
		framelayout = (FrameLayout) findViewById(R.id.container_layout);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.home_navigation, null));

		try {

			// this.title.setTextColor(Color.BLUE);

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(getAssets().open("home.xml"));

			homeDataCollection = new ArrayList<HashMap<String, String>>();

			listview_populate list = new listview_populate();
			homeDataCollection = list.populateListView(doc, rawxmlString,
					KEY_TAG);

			ListBaseAdapter adapter = new ListBaseAdapter(Home_Navigation.this,
					homeDataCollection);
			listview = (ListView) findViewById(R.id.list);
			listview.setAdapter(adapter);
			listview.setSelector(getResources().getDrawable(
					R.drawable.tranparent_selection));

			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent;
					switch (position) {
					case 0:
						createLayouts("CalendarActivity");
						// intent = new Intent(Home_Navigation.this,
						// BaseTabActivity.class);
						// startActivity(intent);
						break;
					case 1:
						// createLayouts("StationActivity");
						intent = new Intent(Home_Navigation.this,
								StationLocation.class);
						startActivity(intent);
						break;
					case 2:
						intent = new Intent(Home_Navigation.this,
								MyGroupsActivity.class);
						startActivity(intent);
						break;
					case 3:
						intent = new Intent(Home_Navigation.this,
								MessagesActivity.class);
						startActivity(intent);
						break;
					case 4:
						intent = new Intent(Home_Navigation.this,
								BenefitsActivity.class);
						startActivity(intent);
						break;
					case 5:
						intent = new Intent(Home_Navigation.this,
								ResourcesActivity.class);
						startActivity(intent);
						break;
					case 6:
						intent = new Intent(Home_Navigation.this,
								SocialMediaActivity.class);
						startActivity(intent);
						break;
					case 7:
						intent = new Intent(Home_Navigation.this,
								StoreActivity.class);
						startActivity(intent);
						break;
					default:
						break;
					}
				}
			});

		}

		catch (IOException ex) {
			Log.e("Error", ex.getMessage());
		} catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}

	}

	public void createLayouts(String layoutNameString) {
		{
			Intent intent;
			if (layoutNameString.equals("HomeNavigation")) {
				homeStateString = "finish";
				createHomeLayout();
			} else if (layoutNameString.equals("CalendarActivity")) {
				intent = new Intent(Home_Navigation.this, BaseTabActivity.class);
				startActivity(intent);
			} else if (layoutNameString.equals("StationActivity")) {
				// homeStateString="HomeNavigation";
				// new
				// StationLocation(Home_Navigation.this).createLayoutStationLocation();
			} else
				finish();

		}
	}

	public void getBanners(Boolean bool) {
		final Boolean isStaticBoolean = bool;
		objBanners = new Banners();
		bannersArrayList = new ArrayList<Banners>();
		try {
			new BackgroundNetwork(Home_Navigation.this) {
				protected Void doInBackground(Void[] params) {

					DataEngine objDataEngine = new DataEngine(
							Home_Navigation.this);
					bannersArrayList = objDataEngine
							.getBannersList(isStaticBoolean);

					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					if (isStaticBoolean == true) {
						try {
							objBanners = bannersArrayList.get(0);
							staticBannerTextView
									.setText(objBanners.description);
							staticBannerTextView
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {

											try {
												String url = objBanners.link
														.toString();
												if (!url.startsWith("http://")
														&& !url.startsWith("https://"))
													url = "http://" + url;
												Intent browserIntent = new Intent(
														Intent.ACTION_VIEW, Uri
																.parse(url));
												startActivity(browserIntent);
											} catch (Exception e) {
												Log.e("HomeNavigationException>>>",
														e.toString());
											}

										}
									});
						} catch (Exception ex) {
							Log.e("HomeNavigationException>>>", ex.toString());
							// System.out.print(">>>HomeNavigation_getBanner_Exception>>"+ex.toString()+">>>"+ex.getMessage())
						}
					} else {
						try {
							objBanners = bannersArrayList.get(0);
							dynamicBannerTextView
									.setText(objBanners.description);
							dynamicBannerTextView
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											try {
												String url = objBanners.link
														.toString();
												if (!url.startsWith("http://")
														&& !url.startsWith("https://"))
													url = "http://" + url;
												Intent browserIntent = new Intent(
														Intent.ACTION_VIEW, Uri
																.parse(url));
												startActivity(browserIntent);
											} catch (Exception ex) {
												Log.e("HomeNavigationException>>>",
														ex.toString());
												// System.out.print(">>>HomeNavigation_getBanner_Exception>>"+ex.toString()+">>>"+ex.getMessage())
											}

										}
									});
						} catch (Exception ex) {
							Log.e("HomeNavigationException>>>", ex.toString());
							// System.out.print(">>>HomeNavigation_getBanner_Exception>>"+ex.toString()+">>>"+ex.getMessage())
						}

					}

				};
			}.execute();
		} catch (Exception e) {

		}
	}

	public void getMessagesCount() {
		try {
			new BackgroundNetwork(Home_Navigation.this) {
				protected Void doInBackground(Void[] params) {
					DataEngine dataEngine = new DataEngine(Home_Navigation.this);
					messageList = dataEngine.getMessagesList(LoginActivity.userType);
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					try {
						countMessage = 0;
						if (messageList != null) {
							for (MessageEntity obj : messageList) {
								countMessage++;
							}
						}
						createHomeLayout();
						System.out.println(">>>Total Message Count>>"
								+ String.valueOf(countMessage));

					} catch (Exception e) {
						System.out.print(">>>Exception MessageActivity>>>"
								+ e.toString() + ">>>" + e.getMessage());
					}
				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	public void getShift() {
		SharedPreferences sharedPreferences;
		final SharedPreferences.Editor sharedPrefsEditor;

		sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		final String memberShiftId = sharedPreferences.getString(
				"memberShiftId", "");
		sharedPreferences = getSharedPreferences("shift", MODE_PRIVATE);
		sharedPrefsEditor = sharedPreferences.edit();

		new BackgroundNetwork(Home_Navigation.this) {
			Shift shiftObj = new Shift();

			protected Void doInBackground(Void[] params) {
				DataEngine dataEngine = new DataEngine(Home_Navigation.this);
				shiftObj = dataEngine.getShift(memberShiftId);
				return null;
			};

			protected void onPostExecute(Void result) {
				if (shiftObj != null) {
					sharedPrefsEditor
							.putString("shiftName", shiftObj.shiftName);
					sharedPrefsEditor
							.putString("startDate", shiftObj.startDate);
//					shiftObj.day = new ArrayList<String>();
//					shiftObj.ColorCode = new ArrayList<String>();
//					shiftObj.ColorName = new ArrayList<String>();
					
					for (int i = 0, j=1; i < 18; i++,j++) {
						try {
							if (i < 10) {
								sharedPrefsEditor.putString("day0" + j,
										shiftObj.day.get(i));
								sharedPrefsEditor.putString("day0" + j
										+ "ColorName",
										shiftObj.ColorName.get(i));
								sharedPrefsEditor.putString("day0" + j
										+ "ColorCode",
										shiftObj.ColorCode.get(i));
							} else {
								sharedPrefsEditor.putString("day" + j,
										shiftObj.day.get(i));
								sharedPrefsEditor.putString("day" + j
										+ "ColorName",
										shiftObj.ColorName.get(i));
								sharedPrefsEditor.putString("day" + j
										+ "ColorCode",
										shiftObj.ColorCode.get(i));
							}
						} catch (Exception er) {
							er.printStackTrace();
						}
					}
					// sharedPrefsEditor.putString("day01", shiftObj.day01);
					// sharedPrefsEditor.putString("day01ColorName",
					// shiftObj.day01ColorName);
					// sharedPrefsEditor.putString("day01ColorCode",
					// shiftObj.day01ColorCode);
					// sharedPrefsEditor.putString("day02", shiftObj.day02);
					// sharedPrefsEditor.putString("day02ColorName",
					// shiftObj.day02ColorName);
					// sharedPrefsEditor.putString("day02ColorCode",
					// shiftObj.day02ColorCode);
					// sharedPrefsEditor.putString("day03", shiftObj.day03);
					// sharedPrefsEditor.putString("day03ColorName",
					// shiftObj.day03ColorName);
					// sharedPrefsEditor.putString("day03ColorCode",
					// shiftObj.day03ColorCode);
					// sharedPrefsEditor.putString("day04", shiftObj.day04);
					// sharedPrefsEditor.putString("day04ColorName",
					// shiftObj.day04ColorName);
					// sharedPrefsEditor.putString("day04ColorCode",
					// shiftObj.day04ColorCode);
					// sharedPrefsEditor.putString("day05", shiftObj.day05);
					// sharedPrefsEditor.putString("day05ColorName",
					// shiftObj.day05ColorName);
					// sharedPrefsEditor.putString("day05ColorCode",
					// shiftObj.day05ColorCode);
					// sharedPrefsEditor.putString("day06", shiftObj.day06);
					// sharedPrefsEditor.putString("day06ColorName",
					// shiftObj.day06ColorName);
					// sharedPrefsEditor.putString("day06ColorCode",
					// shiftObj.day06ColorCode);
					sharedPrefsEditor.commit();

				}
				super.onPostExecute(result);
			}
		}.execute();

	}

}

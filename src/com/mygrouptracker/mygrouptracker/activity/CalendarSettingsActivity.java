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
import com.mygrouptracker.mygrouptracker.adapter.SingleNameListBaseAdapter;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.utility.SingleNameListPopulate;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CalendarSettingsActivity extends BaseTabTitleActivity {

	// XML node keys
	static final String KEY_TAG = "calendarsettings"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";

	public static String calendarSettingsState = "finish";
	String rawxmlString = "calendarsettings.xml";
	ListView listview;
	ListBaseAdapter adapter = null;
	List<SingleNameListEntity> mygroupsList;
	View view;

	// Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.calendar_settings_activity);
		createCalendarLayout();
	}

	public void createCalendarLayout() {
		calendarSettingsState = "finish";
		FrameLayout framelayout = (FrameLayout) findViewById(R.id.layout_basecontainer);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.calendar_settings_activity, null));
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(getAssets().open(rawxmlString));

			mygroupsList = new ArrayList<SingleNameListEntity>();
			SingleNameListPopulate list = new SingleNameListPopulate();
			mygroupsList = list.populateListView(doc, rawxmlString, KEY_TAG);

			SingleNameListBaseAdapter adapter = new SingleNameListBaseAdapter(
					CalendarSettingsActivity.this, mygroupsList);
			listview = (ListView) findViewById(R.id.list_calendarsettings);
			listview.setAdapter(adapter);
			// listview.setSelector(R.drawable.tranparent_selection);

			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						calendarSettingsState = "CalendarSettings";
						new ProfileSettings(CalendarSettingsActivity.this)
								.createLayoutProfileSettings();
						break;
					case 1:
						calendarSettingsState = "CalendarSettings";
						new ShiftTypeActivity(CalendarSettingsActivity.this)
								.createLayoutShiftType();
						break;
					case 2:
						calendarSettingsState = "CalendarSettings";
						new ShiftActivity(CalendarSettingsActivity.this)
								.createLayoutShiftActivity();
						break;
					case 3:
						calendarSettingsState = "CalendarSettings";
						if (LoginActivity.userType.equalsIgnoreCase("active")) {
							new SharingNetworkActivity(
									CalendarSettingsActivity.this)
									.createLayoutSharingNetwork();
						} else {
							showMessage("Only Active/Member type user can have their Sharing Network.");
						}
						break;
					case 4:
						calendarSettingsState = "CalendarSettings";
						new ChangePassword(CalendarSettingsActivity.this)
								.createLayoutChangePassword();
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

		backButton = (Button) findViewById(R.id.btn_backbutton);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (calendarSettingsState.equals("CalendarSettings"))
					createCalendarLayout();
				else if (calendarSettingsState.equals("SharingNetwork"))
					new SharingNetworkActivity(CalendarSettingsActivity.this)
							.createLayoutSharingNetwork();
				else
					finish();
			}
		});
	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				CalendarSettingsActivity.this);
		dlgAlert.setTitle("Error");

		dlgAlert.setMessage(message);

		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	@Override
	public void onBackPressed() {
		if (calendarSettingsState.equals("CalendarSettings"))
			createCalendarLayout();
		else if (calendarSettingsState.equals("SharingNetwork"))
			new SharingNetworkActivity(CalendarSettingsActivity.this)
					.createLayoutSharingNetwork();
		else
			finish();
	}

}

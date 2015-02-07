package com.mygrouptracker.mygrouptracker.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.data.RestfulService;
import com.mygrouptracker.mygrouptracker.entity.LoginUser;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.JsonParserClass;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseClass {

	BackgroundNetwork checkLoginBackgroundNetwork;
	static public String sessionTokenString;
	static public String userNameString;
	static public String passwordString;
	static public String idString;
	static public String userType;

	public static LoginUser loginUserObj;
	EditText userNameEditText;
	EditText passwordEditText;
	TextView registerTextView;
	CheckBox rememberMeCheckBox;
	Button loginButton;
	SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		// setContentView(R.layout.login_activity);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		// setContentView(R.layout.base_layout);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.window_title);

		FrameLayout framelayout = (FrameLayout) findViewById(R.id.container_layout);
		framelayout.removeAllViews();
		framelayout.addView(getLayoutInflater().inflate(
				R.layout.login_activity, null));
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		rememberMeCheckBox = (CheckBox) findViewById(R.id.checkbox_loginactivity_rememberme);
		loginButton = (Button) findViewById(R.id.btn_login);
		registerTextView = (TextView) findViewById(R.id.textview_loginactivity_register);
		userNameEditText = (EditText) findViewById(R.id.edittext_loginactivity_username);
		passwordEditText = (EditText) findViewById(R.id.edittext_loginactivity_password);

		String registerFormatString = "<html><body><u>Register</u></body></html>";
		registerTextView.setText(Html.fromHtml(registerFormatString));

		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		Boolean saveLogin = loginPreferences.getBoolean("saveLogin", false);

		if (saveLogin == true) {
			userNameEditText.setText(loginPreferences
					.getString("userEmail", ""));
			passwordEditText
					.setText(loginPreferences.getString("password", ""));
			rememberMeCheckBox.setChecked(true);
		}

		registerTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// new
				// Registration(LoginActivity.this).createRegistrationLayout();
				Intent intent = new Intent(LoginActivity.this,
						RegistrationActivity.class);
				// intent.putExtra("SessionToken", loginUserObj.sessionToken);
				startActivity(intent);
				finish();
			}
		});

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (userNameEditText.getText().toString().equals("")
						|| passwordEditText.getText().toString().equals("")) {

					return;
				}
				try {
					checkLoginBackgroundNetwork = new BackgroundNetwork(
							LoginActivity.this) {
						@Override
						protected Void doInBackground(Void[] params) {

							userNameEditText = (EditText) findViewById(R.id.edittext_loginactivity_username);
							passwordEditText = (EditText) findViewById(R.id.edittext_loginactivity_password);

							DataEngine dataEngineObj = new DataEngine(
									LoginActivity.this);
							loginUserObj = dataEngineObj.checkLogin(
									userNameEditText.getText().toString(),
									passwordEditText.getText().toString());
							return null;
						};

						@Override
						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							try {
								if (loginUserObj.sessionToken != null) {

									if (loginUserObj.status == true) {

										Intent intent = new Intent(
												LoginActivity.this,
												Home_Navigation.class);
										sessionTokenString = loginUserObj.sessionToken;
										userNameString = userNameEditText
												.getText().toString();
										passwordString = passwordEditText
												.getText().toString();
										userType = loginUserObj.type;
										
										// intent.putExtra("SessionToken",
										// loginUserObj.sessionToken);
										if (rememberMeCheckBox.isChecked()) {

											loginUserObj.email = userNameEditText
													.getText().toString();
											loginUserObj.password = passwordEditText
													.getText().toString();

											loginPrefsEditor.putBoolean(
													"saveLogin", true);
											loginPrefsEditor.putString(
													"userEmail",
													userNameEditText.getText()
															.toString());
											loginPrefsEditor.putString(
													"password",
													passwordEditText.getText()
															.toString());
											loginPrefsEditor.putString(
													"firstName",
													loginUserObj.firstName);
											loginPrefsEditor.putString(
													"lastName",
													loginUserObj.lastName);
											loginPrefsEditor.putString("badge",
													loginUserObj.badge);
											loginPrefsEditor.putString("id",
													loginUserObj.userRefNo);
											loginPrefsEditor.putString(
													"memberShiftId",
													loginUserObj.memberShiftId);
											idString = loginUserObj.userRefNo;
											loginPrefsEditor.commit();
										} else {
											loginUserObj.email = userNameEditText
													.getText().toString();
											loginUserObj.password = passwordEditText
													.getText().toString();
											loginPrefsEditor.clear();
											loginPrefsEditor.commit();
										}
										startActivity(intent);
										finish();
									} else {
										showMessage("User is not Active.");
									}

								} else {
									System.out
											.println(">>>Invalid UserName or Password >>>");
									showMessage(new StatusInfo()
											.getStatusInfo(RestfulService.responseForLogin));
								}
							} catch (Exception ex) {
								showMessage(new StatusInfo()
										.getStatusInfo(RestfulService.responseForLogin));
								System.out.println(">>>Exception " + ex
										+ "  >>> Message : " + ex.getMessage());
							}

						};
					};
				} catch (Exception e) {

					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				}

				checkLoginBackgroundNetwork.execute();

			}
		});
	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				LoginActivity.this);
		dlgAlert.setTitle("Error");

		dlgAlert.setMessage(message);

		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
}

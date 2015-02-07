package com.mygrouptracker.mygrouptracker.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.R.id;
import com.mygrouptracker.mygrouptracker.R.layout;
import com.mygrouptracker.mygrouptracker.adapter.ListDialogBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.StatesBaseAdapter;
import com.mygrouptracker.mygrouptracker.adapter.UserTypeBaseAdapter;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.MemberGroupType;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.entity.ShiftRegister;
import com.mygrouptracker.mygrouptracker.entity.ShiftType;
import com.mygrouptracker.mygrouptracker.entity.States;
import com.mygrouptracker.mygrouptracker.entity.UserType;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.MgtValidation;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class RegistrationActivity extends Activity {

	FrameLayout framelayout;
	EditText userEmailEditText;
	EditText passwordEditText;
	EditText confirmPasswordEditText;
	EditText firstNameEditText;
	EditText lastNameEditText;
	EditText phoneNumberEditText;
	EditText badgeEditText;
	Button registerButton;
	Button backFormButton;

	TextView groupTextView;
	TextView stateTextView;
	TextView typeTextView;
	TextView helpTextView;

	Spinner memberTypeSpinner;
	Spinner shiftSpinner;
	Spinner memberGroupTypeSpinner;
	String eventNameString = "userregistration";
	String response;
	List<NameValuePair> nameValuePairs;
	ArrayList<ShiftType> memberTypeArrayList;
	ArrayList<Shift> shiftArrayList;
	ArrayList<ShiftRegister> shiftRegisterArrayList;
	ArrayList<MemberGroupType> memberGroupTypeList;
	ArrayList<UserType> userTypeList;
	ArrayList<States> statesList;
	String idUserTypeString = "";
	String idGroupString = "";

	MemberGroupType objMemberGroupType;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// framelayout = (FrameLayout) findViewById(R.id.container_layout);
		// framelayout.removeAllViews();
		// framelayout.addView(getLayoutInflater()
		// .inflate(R.layout.register, null));

		userEmailEditText = (EditText) findViewById(R.id.edittext_registration_useremail);
		passwordEditText = (EditText) findViewById(R.id.edittext_registration_password);
		confirmPasswordEditText = (EditText) findViewById(R.id.edittext_registration_confirmpassword);
		firstNameEditText = (EditText) findViewById(R.id.edittext_registration_firstname);
		lastNameEditText = (EditText) findViewById(R.id.edittext_registration_lastname);
		phoneNumberEditText = (EditText) findViewById(R.id.edittext_registration_phonenumber);
		registerButton = (Button) findViewById(R.id.btn_registration_register);
		badgeEditText = (EditText) findViewById(R.id.edittext_registration_badge);
		memberTypeSpinner = (Spinner) findViewById(R.id.spinner_register_membertype);
		shiftSpinner = (Spinner) findViewById(R.id.spinner_register_shifttype);
		// memberGroupTypeSpinner = (Spinner)
		// findViewById(R.id.spinner_register_type);
		backFormButton = (Button) findViewById(R.id.btn_registration_back);
		groupTextView = (TextView) findViewById(R.id.textview_register_group);
		stateTextView = (TextView) findViewById(R.id.textview_register_state);
		typeTextView = (TextView) findViewById(R.id.textview_register_type);
		helpTextView = (TextView) findViewById(R.id.textview_registration_help);

		registerButton.getBackground().setAlpha(64);
		registerButton.setEnabled(false);
		
		stateTextView.setText("");
		stateTextView.setHint("Select a state.");
		
		getStatesValue();
		getUserTypeValue();

		backFormButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegistrationActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});

		helpTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://mygrouptrackerapp.com/contact.html"));
				startActivity(browserIntent);
			}
		});

		// backButton=(Button)findViewById(R.id.btn_back);
		// backButton.setVisibility(View.VISIBLE);
		// backButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(RegistrationActivity.this,
		// LoginActivity.class);
		// startActivity(intent);
		// finish();
		// }
		// });

		// userEmailEditText.setText("jhonny7777@name.com");
		// passwordEditText.setText("7777");
		// firstNameEditText.setText("Jhonny");
		// lastNameEditText.setText("Depp");
		// phoneNumberEditText.setText("7777777777");

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				badgeEditText.setText("0");
				if (new MgtValidation(RegistrationActivity.this)
						.CheckRegistration(userEmailEditText, passwordEditText,
								firstNameEditText, lastNameEditText,
								phoneNumberEditText, badgeEditText, groupTextView) == false) {
					return;
				}

				if (!passwordEditText.getText().toString()
						.equals(confirmPasswordEditText.getText().toString())) {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
							RegistrationActivity.this);
					dlgAlert.setTitle("Error");
					dlgAlert.setMessage("Password not matched");
					dlgAlert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					dlgAlert.setCancelable(true);
					dlgAlert.create().show();
					return;
				}

				try {
					new BackgroundNetwork(RegistrationActivity.this) {
						protected Void doInBackground(Void[] params) {

							// ShiftType objShiftType = new ShiftType();
							// objShiftType = (ShiftType) memberTypeSpinner
							// .getSelectedItem();
							//
							// ShiftRegister objShiftRegister;
							// objShiftRegister = (ShiftRegister) shiftSpinner
							// .getSelectedItem();
							//
							// MemberGroupType objMemberGroupType;
							// objMemberGroupType = (MemberGroupType)
							// memberGroupTypeSpinner
							// .getSelectedItem();

							nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair("Email",
									userEmailEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"Password", passwordEditText.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"FirstName", firstNameEditText.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"LastName", lastNameEditText.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair("Phone",
									phoneNumberEditText.getText().toString()));
							// nameValuePairs.add(new
							// BasicNameValuePair("Badge",
							// badgeEditText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("Badge",
									"0"));
							// nameValuePairs.add(new
							// BasicNameValuePair("MemberType",
							// objShiftType.idStr ing));
							// nameValuePairs.add(new
							// BasicNameValuePair("ShiftType",
							// objShiftRegister.id));
							nameValuePairs.add(new BasicNameValuePair(
									"MemberType", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"ShiftType", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"AdminGroupId_fk", idGroupString));
							nameValuePairs.add(new BasicNameValuePair(
									"GroupMemberTypeId", idUserTypeString));

							DataEngine dataEngine = new DataEngine(
									RegistrationActivity.this);
							response = dataEngine.addRecordRegistration(
									eventNameString, nameValuePairs);
							return null;
						};

						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							if (response != null) {
								AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
										RegistrationActivity.this);
								dlgAlert.setTitle("Message");
								if (new StatusInfo().getStatusInfo(response)
										.equalsIgnoreCase("Success")) {
									dlgAlert.setMessage("User registered successfully.");
								} else {
									dlgAlert.setMessage(new StatusInfo()
											.getStatusInfo(response));
								}

								dlgAlert.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												Intent intent = new Intent(
														RegistrationActivity.this,
														LoginActivity.class);
												startActivity(intent);
												finish();
											}
										});
								dlgAlert.setCancelable(true);
								dlgAlert.create().show();
							}
						};
					}.execute();
				} catch (Exception e) {
					System.out.println(">>>Exception " + e + "  >>> Message : "
							+ e.getMessage());
				}

			}
		});

	}

	// public void getTypeValue() {
	// try {
	// // ArrayList<MemberGroupType> memberGroupTypeList = new
	// // ArrayList<MemberGroupType>();
	// // memberGroupTypeList.add(new MemberGroupType("GroupMember"));
	// // memberGroupTypeList.add(new MemberGroupType("Family"));
	// // memberGroupTypeList.add(new MemberGroupType("Retiree"));
	//
	// new BackgroundNetwork(RegistrationActivity.this) {
	// @Override
	// protected Void doInBackground(Void... params) {
	//
	// memberGroupTypeList = new DataEngine(
	// RegistrationActivity.this).getMemberGroupTypeList();
	//
	// return super.doInBackground(params);
	// }
	//
	// protected void onPostExecute(Void result) {
	// try {
	// ArrayAdapter<MemberGroupType> dataAdapter = new
	// ArrayAdapter<MemberGroupType>(
	// RegistrationActivity.this,
	// android.R.layout.simple_spinner_item,
	// memberGroupTypeList);
	// dataAdapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// memberTypeSpinner.setAdapter(dataAdapter);
	// dataAdapter.notifyDataSetChanged();
	//
	// memberTypeSpinner
	// .setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	// public void onItemSelected(
	// android.widget.AdapterView<?> arg0,
	// View arg1, int arg2, long arg3) {
	// objMemberGroupType = (MemberGroupType) memberTypeSpinner
	// .getItemAtPosition(arg2);
	//
	// }
	//
	// @Override
	// public void onNothingSelected(
	// AdapterView<?> arg0) {
	// // TODO Auto-generated method stub
	//
	// };
	// });
	// // memberTypeSpinner.setOnItemClickListener(new
	// // OnItemClickListener() {
	// //
	// // @Override
	// // public void onItemClick(AdapterView<?> arg0, View
	// // arg1,
	// // int arg2, long arg3) {
	// // MemberGroupType objMemberGroupType =
	// // (MemberGroupType) memberGroupTypeSpinner
	// // .getItemAtPosition(arg2);
	// //
	// // }
	// // });
	// super.onPostExecute(result);
	// }
	//
	// catch (Exception e) {
	// super.onPostExecute(result);
	// e.printStackTrace();
	// }
	// };
	// }.execute();
	//
	// } catch (Exception e) {
	// System.out.print(">>>ShiftTypeActivity Error>>>" + e.toString()
	// + ">>>" + e.getMessage());
	// Log.e("ShiftTypeerror", e.toString() + ">>>" + e.getMessage());
	// }
	// }

	public void getStatesValue() {
		try {

			new BackgroundNetwork(RegistrationActivity.this) {

				@Override
				protected Void doInBackground(Void[] params) {
					statesList = new DataEngine(RegistrationActivity.this)
							.getStatesList();

					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					try {
					
						stateTextView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								showStatesListDialog();
							}
						});

					} catch (Exception e) {
						System.out.println(">>>Exception " + e
								+ "  >>> Message : " + e.getMessage());
					}
				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	public void getMemberGroupTypeSpinnerValue(final String stateID) {
		try {

			new BackgroundNetwork(RegistrationActivity.this) {

				@Override
				protected Void doInBackground(Void[] params) {

					// memberGroupTypeList = new ArrayList<MemberGroupType>();
					// memberGroupTypeList.add(new
					// MemberGroupType("GroupMember"));
					// memberGroupTypeList.add(new MemberGroupType("Family"));
					// memberGroupTypeList.add(new MemberGroupType("Retiree"));
					// DataEngine dataEngine = new DataEngine(
					// RegistrationActivity.this);
					// memberGroupTypeList =
					// dataEngine.getMemberGroupTypeList();

					memberGroupTypeList = new DataEngine(
							RegistrationActivity.this)
							.getMemberGroupTypeList(stateID);

					return null;
				};

				@SuppressLint("NewApi") 
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					try {

						MemberGroupType obj = memberGroupTypeList.get(0);
						groupTextView.setEnabled(true);
						groupTextView.setText(obj.groupName);
						idGroupString = obj.id;						
						registerButton.getBackground().setAlpha(255);
						registerButton.setEnabled(true);
						groupTextView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								showGroupListDialog();

							}
						});

					} catch (Exception e) {
						groupTextView.setEnabled(false);
						groupTextView.setText("");
						registerButton.getBackground().setAlpha(64);
						registerButton.setEnabled(false);
						groupTextView
								.setHint("No Group found on the selected state.");
						System.out.println(">>>Exception " + e
								+ "  >>> Message : " + e.getMessage());
					}
				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	public void showMessage(String message) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				RegistrationActivity.this);
		dlgAlert.setTitle("Message");
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(RegistrationActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	public void getUserTypeValue() {
		try {

			new BackgroundNetwork(RegistrationActivity.this) {

				@Override
				protected Void doInBackground(Void[] params) {
					userTypeList = new DataEngine(RegistrationActivity.this)
							.getUserTypeList();

					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					try {

						UserType obj = userTypeList.get(0);
						typeTextView.setText(obj.type);
						idUserTypeString = obj.id;
						typeTextView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								showUserTypeListDialog();
							}
						});

					} catch (Exception e) {
						System.out.println(">>>Exception " + e
								+ "  >>> Message : " + e.getMessage());
					}
				};
			}.execute();
		} catch (Exception e) {
			System.out.println(">>>Exception " + e + "  >>> Message : "
					+ e.getMessage());
		}
	}

	// public void getMemberTypeSpinnersValue() {
	// try {
	// new BackgroundNetwork(RegistrationActivity.this) {
	// protected Void doInBackground(Void[] params) {
	//
	// memberTypeArrayList = new ArrayList<ShiftType>();
	// DataEngine dataEngine = new DataEngine(
	// RegistrationActivity.this);
	// memberTypeArrayList = dataEngine.getShiftTypeList();
	//
	// return null;
	// };
	//
	// protected void onPostExecute(Void result) {
	// super.onPostExecute(result);
	//
	// try {
	// ArrayAdapter<ShiftType> dataAdapter = new ArrayAdapter<ShiftType>(
	// RegistrationActivity.this,
	// android.R.layout.simple_spinner_item,
	// memberTypeArrayList);
	// dataAdapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// memberTypeSpinner.setAdapter(dataAdapter);
	// dataAdapter.notifyDataSetChanged();
	//
	// memberTypeSpinner
	// .setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(
	// AdapterView<?> arg0, View arg1,
	// int arg2, long arg3) {
	// ShiftType objShiftType = (ShiftType) shiftSpinner
	// .getItemAtPosition(arg2);
	//
	// }
	// });
	// } catch (Exception e) {
	// System.out.print(">>>ShiftTypeActivity Error>>>"
	// + e.toString() + ">>>" + e.getMessage());
	// Log.e("ShiftTypeerror",
	// e.toString() + ">>>" + e.getMessage());
	// }
	// };
	// }.execute();
	// } catch (Exception e) {
	// System.out.println(">>>Exception " + e + "  >>> Message : "
	// + e.getMessage());
	// }
	//
	// }
	//
	// public void getShiftTypeSpinnerValue() {
	// try {
	//
	// new BackgroundNetwork(RegistrationActivity.this) {
	//
	// @Override
	// protected Void doInBackground(Void[] params) {
	//
	// shiftRegisterArrayList = new ArrayList<ShiftRegister>();
	// DataEngine dataEngine = new DataEngine(
	// RegistrationActivity.this);
	// shiftRegisterArrayList = dataEngine.getShiftRegisterList();
	//
	// return null;
	// };
	//
	// protected void onPostExecute(Void result) {
	// super.onPostExecute(result);
	//
	// try {
	//
	// String str = shiftRegisterArrayList.toString();
	// ArrayAdapter<ShiftRegister> dataAdapter = new
	// ArrayAdapter<ShiftRegister>(
	// RegistrationActivity.this,
	// android.R.layout.simple_spinner_item,
	// shiftRegisterArrayList);
	//
	// dataAdapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// shiftSpinner.setAdapter(dataAdapter);
	// dataAdapter.notifyDataSetChanged();
	//
	// shiftSpinner
	// .setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(
	// AdapterView<?> parent, View view,
	// int position, long id) {
	// // TODO Auto-generated method stub
	// // Shift
	// // shift=(Shift)parent.getItemAtPosition(position);
	// // Toast.makeText(context,
	// // shift.shiftName+" "+position+" Hourspday "+shift.hoursPerDay,
	// // Toast.LENGTH_LONG).show();
	// }
	//
	// @Override
	// public void onNothingSelected(
	// AdapterView<?> parent) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// } catch (Exception e) {
	//
	// Log.e(">>>Error>>", e.getMessage());
	// }
	// };
	// }.execute();
	// } catch (Exception e) {
	// System.out.println(">>>Exception " + e + "  >>> Message : "
	// + e.getMessage());
	// }
	// }

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(RegistrationActivity.this,
				LoginActivity.class);
		startActivity(intent);
		finish();
	}

	public void showStatesListDialog() {
		final Dialog dialog1 = new Dialog(RegistrationActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.list_dialog_layout);

		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_list_dialog_listiview);

		StatesBaseAdapter adapter = new StatesBaseAdapter(
				RegistrationActivity.this, statesList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				stateTextView.setText(statesList.get(position).stateName);
				String stateID = statesList.get(position).id;
				// natureOfEnquiryTextView.setText(stringList.get(position).listString);
				// messagesEditText.setFocusableInTouchMode(true);
				// messagesEditText.requestFocus();
				dialog1.dismiss();
				getMemberGroupTypeSpinnerValue(stateID);
			}

		});

		dialog1.show();

		// dialog1.setOnDismissListener(new OnDismissListener() {
		//
		// @Override
		// public void onDismiss(DialogInterface dialog) {
		// messagesEditText.post(new Runnable() {
		// public void run() {
		// messagesEditText.requestFocusFromTouch();
		// InputMethodManager lManager = (InputMethodManager)
		// ContactUsActivity.this
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
		// lManager.showSoftInput(messagesEditText, 0);
		// }
		// });
		//
		// }
		// });

	}

	public void showGroupListDialog() {
		final Dialog dialog1 = new Dialog(RegistrationActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.list_dialog_layout);

		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_list_dialog_listiview);

		// memberGroupTypeList = new ArrayList<MemberGroupType>();
		// memberGroupTypeList.add(new MemberGroupType("GroupMember"));
		// memberGroupTypeList.add(new MemberGroupType("Family"));
		// memberGroupTypeList.add(new MemberGroupType("Retiree"));

		ListDialogBaseAdapter adapter = new ListDialogBaseAdapter(
				RegistrationActivity.this, memberGroupTypeList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				groupTextView.setText(memberGroupTypeList.get(position).groupName);
				idGroupString = memberGroupTypeList.get(position).id;
				// natureOfEnquiryTextView.setText(stringList.get(position).listString);
				// messagesEditText.setFocusableInTouchMode(true);
				// messagesEditText.requestFocus();
				dialog1.dismiss();
			}

		});

		dialog1.show();

		// dialog1.setOnDismissListener(new OnDismissListener() {
		//
		// @Override
		// public void onDismiss(DialogInterface dialog) {
		// messagesEditText.post(new Runnable() {
		// public void run() {
		// messagesEditText.requestFocusFromTouch();
		// InputMethodManager lManager = (InputMethodManager)
		// ContactUsActivity.this
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
		// lManager.showSoftInput(messagesEditText, 0);
		// }
		// });
		//
		// }
		// });

	}

	public void showUserTypeListDialog() {
		final Dialog dialog1 = new Dialog(RegistrationActivity.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.list_dialog_layout);

		ListView stringListView = (ListView) dialog1
				.findViewById(R.id.listview_list_dialog_listiview);

		// memberGroupTypeList = new ArrayList<MemberGroupType>();
		// memberGroupTypeList.add(new MemberGroupType("GroupMember"));
		// memberGroupTypeList.add(new MemberGroupType("Family"));
		// memberGroupTypeList.add(new MemberGroupType("Retiree"));

		UserTypeBaseAdapter adapter = new UserTypeBaseAdapter(
				RegistrationActivity.this, userTypeList);
		stringListView.setAdapter(adapter);

		stringListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				typeTextView.setText(userTypeList.get(position).type);
				idUserTypeString = userTypeList.get(position).id;
				// natureOfEnquiryTextView.setText(stringList.get(position).listString);
				// messagesEditText.setFocusableInTouchMode(true);
				// messagesEditText.requestFocus();
				dialog1.dismiss();
			}

		});

		dialog1.show();
	}

}

package com.mygrouptracker.mygrouptracker.utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MgtValidation
{
Context context;
	public MgtValidation(Context cont) {
		
		context=cont;
		
	}
	
	public void showMessage(String message){
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
		dlgAlert.setTitle("Error");
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	public  boolean CheckApointment(EditText eventDateEditText,EditText descriptionEditText )
	{
		try {
		
		if(	descriptionEditText.getText().toString().trim().equals(""))
		{
			descriptionEditText.requestFocus();
//			Toast.makeText(context, "Please enter description", Toast.LENGTH_SHORT).show();
			showMessage("Please enter description");
			return false;
		}
		else
		return true;
	} catch (Exception e) {
	
		return false;
        }
	}
	public  boolean CheckCompTime(EditText hourEditText ,EditText noteEditText,EditText descriptionEditText )
	{
		
		if(hourEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter Hours", Toast.LENGTH_SHORT).show();
			showMessage("Please enter Hours");
			return false;
		}
//		else
//		if(noteEditText.getText().toString().trim().equals(""))
//		{
//			Toast.makeText(context, "Please enter Hours", Toast.LENGTH_SHORT).show();
//			return false;
//		}
		if(descriptionEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter description", Toast.LENGTH_SHORT).show();
			showMessage("Please enter description");
			return false;
		}
		return true;
	}
	
//	public  boolean CheckExpenseTracker(EditText eventDateEditText,EditText descriptionEditText )
//	{
//		if(eventDateEditText.getText().toString().trim().equals(""))
//		{
//			Toast.makeText(context, "Please enter Hours", Toast.LENGTH_SHORT).show();
//			return false;
//		}
//		else
//		return false;
//	}
	public  boolean CheckKellyDay(EditText repeatEditText)
	{
		if(repeatEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter repeat", Toast.LENGTH_SHORT).show();
			showMessage("Please enter repeat");
			return false;
		}
		else
		return true;
	}
	public  boolean CheckOverTime(EditText hourEditText,EditText assignmentEditText,EditText desc1EditText,EditText desc2EditText)
	{
		if(hourEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter hours", Toast.LENGTH_SHORT).show();
			showMessage("Please enter hours");
			return false;
		}
		else
			if(assignmentEditText.getText().toString().trim().equals(""))
			{
//				Toast.makeText(context, "Please enter assignment", Toast.LENGTH_SHORT).show();
				showMessage("Please enter assignment");
				return false;
			}
			else
				if(desc1EditText.getText().toString().trim().equals(""))
				{
//					Toast.makeText(context, "Please enter desc 1", Toast.LENGTH_SHORT).show();
					showMessage("Please enter desc 1");
					return false;
				}
				else
					if(desc2EditText.getText().toString().trim().equals(""))
					{
//						Toast.makeText(context, "Please enter desc 2", Toast.LENGTH_SHORT).show();
						showMessage("Please enter desc 2");
						return false;
					}
					else
						return true;
		
	}
	public  boolean CheckSickTime(EditText hourEditText,EditText notesEditText )
	{
		if(hourEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter hours", Toast.LENGTH_SHORT).show();
			showMessage("Please enter hours");
			return false;
		}
		else
			if(notesEditText.getText().toString().trim().equals(""))
			{
//				Toast.makeText(context, "Please enter notes", Toast.LENGTH_SHORT).show();
				showMessage("Please enter notes");
				return false;
			}
		return true;
	}
	public  boolean CheckTradeTime(EditText nameEditText,EditText hourEditText,EditText noteEditText )
	{
		if(nameEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show();
			showMessage("Please enter name");
			return false;
		}
		else
			if(hourEditText.getText().toString().trim().equals(""))
			{
//				Toast.makeText(context, "Please enter hours", Toast.LENGTH_SHORT).show();
				showMessage("Please enter hours");
				return false;
			}
			else
				if(noteEditText.getText().toString().trim().equals(""))
				{
//					Toast.makeText(context, "Please enter note", Toast.LENGTH_SHORT).show();
					showMessage("Please enter note");
					return false;
				}
				else
					return true;
		
	}
	public  boolean CheckVacationTime(EditText noteEditText,EditText hourEditText )
	{
		if(noteEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter hours", Toast.LENGTH_SHORT).show();
			showMessage("Please enter hours");
			return false;
		}
		else
			if(hourEditText.getText().toString().trim().equals(""))
			{
//				Toast.makeText(context, "Please enter note", Toast.LENGTH_SHORT).show();
				showMessage("Please enter note");
				return false;
			}
			else
				return true;
	}
	public  boolean CheckWorkersComp(EditText caseNumberEditText,EditText noteEditText )
	{
		if(caseNumberEditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter case no.", Toast.LENGTH_SHORT).show();
			showMessage("Please enter case no.");
			return false;
		}
		else
			if(noteEditText.getText().toString().trim().equals(""))
			{
//				Toast.makeText(context, "Please enter note", Toast.LENGTH_SHORT).show();
				showMessage("Please enter note");
				return false;
			}
			else
				return true;
	}
	public boolean CheckWorkers(EditText eventDateEditText,EditText descriptionEditText )
	{
		String ev=eventDateEditText.getText().toString();
		if(ev.equals(""))
		{
			return false;
		}
		ev=descriptionEditText.getText().toString();
		if(ev.equals(""))
		{
			return false;
		}
		return false;
	}
	
	public  boolean CheckExpenseTracker(EditText desc1EditText,EditText desc2EditText,EditText desc3EditText,EditText amountEditText)
	{
		if(desc1EditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter desc1 text", Toast.LENGTH_SHORT).show();
			showMessage("Please enter desc1 text");
			return false;
		}
		else if(desc2EditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter desc2 text", Toast.LENGTH_SHORT).show();
			showMessage("Please enter desc2 text");
			return false;
		}
		else if(desc3EditText.getText().toString().trim().equals(""))
		{
//			Toast.makeText(context, "Please enter desc3 text", Toast.LENGTH_SHORT).show();
			showMessage("Please enter desc3 text");
			return false;
		}
		else if(amountEditText.getText().toString().trim().equals("") || amountEditText.getText().toString().equals("$"))
		{
//			Toast.makeText(context, "Please enter amount", Toast.LENGTH_SHORT).show();
			showMessage("Please enter amount");
			return false;
		}
		else
			return true;
		
	}
	
	public boolean CheckChangePassword(EditText newPasswordEditText,EditText oldPasswordEditText,EditText confirmPasswordEditText )
	{
		String ev=oldPasswordEditText.getText().toString();
		if(ev.equals(""))
		{
//			Toast.makeText(context, "Old Password Text Can't be empty", Toast.LENGTH_SHORT).show();
			showMessage("Old Password Text Can't be empty");
			return false;
		}
		ev=newPasswordEditText.getText().toString();
		if(ev.equals(""))
		{
//			Toast.makeText(context, "New Password Text Can't be empty", Toast.LENGTH_SHORT).show();
			showMessage("New Password Text Can't be empty");
			return false;
		}
		ev=confirmPasswordEditText.getText().toString();
		if(ev.equals(""))
		{
//			Toast.makeText(context, "confirm Password Text Can't be empty", Toast.LENGTH_SHORT).show();
			showMessage("confirm Password Text Can't be empty");
			return false;
		}
		return true;
	}
	public boolean checkPasswordLength(String password,int length)
	{
		if(password.length()<length)
		{
			showMessage("Password length must be greater than "+length+" char.");
			return false;
		}
		else
			return true;
		
	}
	public  boolean CheckRegistration(EditText userEmailEditText,EditText passwordEditText,EditText firstNameEditText,EditText lastNameEditText,EditText phoneNumberEditText, EditText badgeEditText, TextView groupNameTextView)
	{
		if(firstNameEditText.getText().toString().trim().equals(""))
		{
			showMessage("Please enter First Name");			
			return false;
		}
		else
			if(lastNameEditText.getText().toString().trim().equals(""))
			{
//				Toast.makeText(context, "Please enter assignment", Toast.LENGTH_SHORT).show();
				showMessage("Please enter Last Name");
				return false;
			}
			else
				if(userEmailEditText.getText().toString().trim().equals(""))
				{
//					Toast.makeText(context, "Please enter desc 1", Toast.LENGTH_SHORT).show();
					showMessage("Please Enter UserEmail address");
					return false;
				}
				else
					if(passwordEditText.getText().toString().trim().equals(""))
					{
//						Toast.makeText(context, "Please enter desc 2", Toast.LENGTH_SHORT).show();
						showMessage("Please enter password");						
						return false;
					}
				else
					if(phoneNumberEditText.getText().toString().trim().equals(""))
					{
//							Toast.makeText(context, "Please enter desc 2", Toast.LENGTH_SHORT).show();
							showMessage("Please enter Phone Number");
							return false;
					}
				else
					if(badgeEditText.getText().toString().trim().equals(""))
					{
//								Toast.makeText(context, "Please enter desc 2", Toast.LENGTH_SHORT).show();
								showMessage("Please enter Badge");
								return false;
					}
					else
						if(groupNameTextView.getText().toString().trim().equals(""))
						{
//									Toast.makeText(context, "Please enter desc 2", Toast.LENGTH_SHORT).show();
									showMessage("Please select a group");
									return false;
						}
					else
						return true;
		
	}
	

}

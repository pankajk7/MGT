package com.mygrouptracker.mygrouptracker.utility;

import android.util.Base64;

public class AuthorizationUtility {
	
	public String getLoginAuthorization(String userName, String password) {
		   String sourceString=userName+":"+password;
		    return ("Basic "+Base64.encodeToString(sourceString.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP));		   
		 }
	
	public String getAuthorization(String userName, String sessionToken) {
		   String sourceString=userName+":"+sessionToken;
		    return ("Basic "+Base64.encodeToString(sourceString.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP));		   
		 }
}

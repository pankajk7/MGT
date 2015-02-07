package com.mygrouptracker.mygrouptracker.utility;

public class StatusInfo {
	
	String SUCCESS = "Success"; 
	String RECORD_NOT_FOUND = "RecordNotFound";
	String OPERATION_FAILED = "OperationFailed";
	String RECORD_LOCKED = "RecordLocked";
	String CHILD_RECORD_FOUND = "ChildRecordFound";
	String RECORD_ALREADY_EXIST = "RecordAlreadyExist";
	String INVALID_USER_PASSWORD = "InvalidUserPassword";
	String USER_ACCOUNT_EXPIRED = "UserAccountExpired";
	String USER_NOT_LOGGED_IN = "UserNotLoggedIn";
	String INVALID_SESSION_INFO = "InvalidSessionInfo";
	String INVALID_REQUEST_HEADER_INFO = "InvalidReqHeaderInfo";
	String INVALID_RECORD_IN_DATABASE = "InvalidRecordInDataBase";
	String INVALID_PARAMETER = "InvalidParameter";
	String USER_ACCOUNT_DISABLED = "UserAccountDisabled";
	String ACCESS_DENINED = "AccessDenined";
	String INVALID_DATE = "InvalidDate";
	String CONCURRENCY_FAILURE = "ConcurrencyFailure";
	String DBERROR = "DBError";
	String INVALID_EMAIL = "InvalidEmail";
	String INVALID_FIRST_NAME = "InvalidFirstName";
	String INVALID_LAST_NAME = "InvalidLastName";
	String INVALID_PERMISSIONS = "InvalidPermissions";
	String NETWORK_NOT_FOUND = "NetworkNotFound";
	String NETWORK_NOT_FOUND_MESSAGE = "Network not found.";
	String INVALID_PASSWORD = "InvalidPassword";
	String REQUEST_ALREADY_SENT = "RequestAlreadySent";
	
	public String getStatusInfo(String status){
		if(status.equals(SUCCESS))
			return "Success";
		else if(status.equals(RECORD_NOT_FOUND))
			return "Record not found.";
		else if(status.equals(OPERATION_FAILED))
			return "Operation Failed";
		else if(status.equals(RECORD_LOCKED))
			return "Record locked.";
		else if(status.equals(CHILD_RECORD_FOUND))
			return "Child record not found.";
		else if(status.equals(RECORD_ALREADY_EXIST))
			return "Record already exist.";
		else if(status.equals(INVALID_USER_PASSWORD))
			return "Invalid Username or Password.";
		else if(status.equals(USER_ACCOUNT_EXPIRED))
			return "User account expired.";
		else if(status.equals(USER_NOT_LOGGED_IN))
			return "User not logged in.";
		else if(status.equals(INVALID_SESSION_INFO))
			return "Invalid session info.";
		else if(status.equals(INVALID_REQUEST_HEADER_INFO))
			return "Invalid request header.";
		else if(status.equals(INVALID_RECORD_IN_DATABASE))
			return "Invalid record in database.";
		else if(status.equals(INVALID_PARAMETER))
			return "Invalid parameter.";
		else if(status.equals(USER_ACCOUNT_DISABLED))
			return "User account disabled.";
		else if(status.equals(ACCESS_DENINED))
			return "Access denied.";
		else if(status.equals(INVALID_DATE))
			return "Invalid date.";
		else if(status.equals(CONCURRENCY_FAILURE))
			return "Concurrency failure.";
		else if(status.equals(DBERROR))
			return "DB error.";
		else if(status.equals(INVALID_EMAIL))
			return "Invalid email.";
		else if(status.equals(INVALID_FIRST_NAME))
			return "Invalid first name.";
		else if(status.equals(INVALID_LAST_NAME))
			return "Invalid last name.";
		else if(status.equals(INVALID_PERMISSIONS))
			return "Invalid permissions.";
		else if(status.equals(NETWORK_NOT_FOUND))
			return "Network not found.";
		else if(status.equals(NETWORK_NOT_FOUND_MESSAGE))
			return "Network not found";		
		else if(status.equals(INVALID_PASSWORD))
			return "Password length must be greater than 6 character";
		else if(status.equals(REQUEST_ALREADY_SENT))
			return "Request already sent.";
		else
			return "";
	}

}

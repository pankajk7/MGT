package com.mygrouptracker.mygrouptracker.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.ExpenseTracker;
import com.mygrouptracker.mygrouptracker.activity.LoginActivity;
import com.mygrouptracker.mygrouptracker.data.DataParser;

import com.mygrouptracker.mygrouptracker.entity.AllEvents;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.ExpenseTrackerEntity;
import com.mygrouptracker.mygrouptracker.entity.HolidayEntity;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.Links;
import com.mygrouptracker.mygrouptracker.entity.LoginUser;
import com.mygrouptracker.mygrouptracker.entity.MemberGroupType;
import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.PaydayEntity;
import com.mygrouptracker.mygrouptracker.entity.SharingNetwork;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.entity.ShiftRegister;
import com.mygrouptracker.mygrouptracker.entity.ShiftType;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.entity.SocialMedia;
import com.mygrouptracker.mygrouptracker.entity.States;
import com.mygrouptracker.mygrouptracker.entity.Station;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.UserType;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.Banners;
import com.mygrouptracker.mygrouptracker.utility.StatusInfo;

public class DataEngine {
	Context context;
	String urlString = "http://mygrouptrackerapp.com/api";
//	String urlString="http://service.mygrouptrackerapp.com.192-185-7-4.secure5.win.hostgator.com/";
//	String urlString="http://192.168.1.31:78";
	
	public DataEngine(Context context) {
		this.context = context;
	}

//	public InputStream getDataFromUrl(String urlString)
//	{
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpContext localContext = new BasicHttpContext();
//		HttpGet httpGet = new HttpGet(urlString);
//		
//        try {
//        	if(httpGet!=null)
//        	{
//              HttpResponse response = httpClient.execute(httpGet, localContext);
//              HttpEntity entity = response.getEntity();
//              InputStream inputStream=entity.getContent();
//              return inputStream;
//        	}
//        } catch (Exception e) {
//       	  e.getMessage();
//        }
//        return null;
//	}
	
	public LoginUser checkLogin(String userName, String password)
	{
		urlString = urlString + "/loginuser";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseLoginDetails(restfulService.getLogin(urlString,userName,password));
	}
        
	public ArrayList<AppointmentSummaryEntity> getAppointmentSummary() {
		
		urlString = urlString + "/appointment";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseAppointmentSummary(restfulService.get(urlString));
	}
	
	
	public ArrayList<HolidayEntity> getHolidays() {		
		urlString = urlString + "/holiday";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseHoliday(restfulService.get(urlString));
	}
	public ArrayList<PaydayEntity> getPaydays() {
		
		urlString = urlString + "/payday";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parsePayday(restfulService.get(urlString));
	}
	public ArrayList<CompTimeEntity> getCompTime() {
		
		urlString = urlString + "/comptime";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseCompTime(restfulService.get(urlString));
	}
	
	public ArrayList<KellyDayEntity> getKellyDay() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.kellyday);
//		return new DataParser().parseKellyDay(inputStream);
		urlString = urlString + "/kellyday";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseKellyDay(restfulService.get(urlString));
	}

	public ArrayList<OverTimeEntity> getOverTime() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.overtime);
//		return new DataParser().parseOverTime(inputStream);
		urlString = urlString + "/overtime";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseOverTime(restfulService.get(urlString));
	}
	
	public ArrayList<SickTimeEntity> getSickTime() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.sicktime);
//		return new DataParser().parseSickTime(inputStream);
		urlString = urlString + "/sicktime";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseSickTime(restfulService.get(urlString));
	}
	
	public ArrayList<TradeTimeEntity> getTradeTime() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.tradetime);
//		return new DataParser().parseTradeTime(inputStream);
		urlString = urlString + "/tradetime";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseTradeTime(restfulService.get(urlString));
	}
	
	public ArrayList<TradeTimeEntity> getTradeTimeArchive() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.tradetime);
//		return new DataParser().parseTradeTime(inputStream);
		urlString = urlString + "/tradetimearchive";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseTradeTime(restfulService.get(urlString));
	}
	
	public ArrayList<VacationTimeEntity> getVacationTime() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.vacationtime);
//		return new DataParser().parseVacationTime(inputStream);
		urlString = urlString + "/vacationtime";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseVacationTime(restfulService.get(urlString));
	}
	
	public ArrayList<WorkersCompEntity> getWorkersComp() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.workercomp);
//		return new DataParser().parseWorkersComp(inputStream);
		urlString = urlString + "/workerscomp";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseWorkersComp(restfulService.get(urlString));
	}
	
	public ArrayList<ExpenseTrackerEntity> getExpenseTracker(){
		urlString = urlString + "/expense";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseExpenseTrackerList(restfulService.get(urlString));
	}
	
	public ArrayList<Station> getStationList() {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.workercomp);
//		return new DataParser().parseWorkersComp(inputStream);
		urlString = urlString + "/station";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseStationList(restfulService.get(urlString));
	}
	
	public ArrayList<MessageEntity> getMessagesList(String userType) {
		userType = userType.toLowerCase(Locale.US);
		urlString = urlString + "/message?Type="+userType;
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseMessagesList(restfulService.get(urlString));
	}
	
//	public SingleNameListEntity getBenefitList(){
//		urlString = urlString + "/benefit";
//		RestfulService restfulService=new RestfulService();
//		DataParser dataParser= new DataParser();		
//		return dataParser.parseBenefitList(restfulService.get(urlString));
//	}
	
	public AllEvents getAllEvents(String parameter) {
//		InputStream inputStream = this.context.getResources().openRawResource(
//				R.raw.vacationtime);
//		return new DataParser().parseVacationTime(inputStream);
		
		urlString = urlString + "/"+parameter;
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseAllEvents(restfulService.get(urlString));
	}
	
	public ArrayList<Links> getSocialMediaList() {
		urlString = urlString + "/socialmedialink";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseSocialMediaList(restfulService.get(urlString));
	}
	
	public ArrayList<Links> getBenifitsList() {
		urlString = urlString + "/benefit";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseBenefitList(restfulService.get(urlString));
	}
	
	public ArrayList<Links> getResourceList() {
		urlString = urlString + "/resource";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseResourceList(restfulService.get(urlString));
	}
	
	public ArrayList<Links> getMyGroupList() {
		urlString = urlString + "/mygroup";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseMyGroupList(restfulService.get(urlString));
	}
	
	public ArrayList<Links> getStoreList() {
		urlString = urlString + "/store";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseStoreList(restfulService.get(urlString));
	}
	
//	public List<ShiftType> getShiftTypeList() {
//		urlString = urlString + "/membertype";
//		RestfulService restfulService=new RestfulService();
//		DataParser dataParser= new DataParser();		
//		return dataParser.parseShiftTypeList(restfulService.get(urlString));
//	}
	
	public ArrayList<Shift> getShiftList() {
		urlString = urlString + "/shift";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseShiftList(restfulService.get(urlString));
	}
	

	public ArrayList<Banners> getBannersList(Boolean isStatic) {
		if(isStatic==true)
			urlString = urlString + "/banner?isStaticBanner="+isStatic;
		else
			urlString = urlString + "/banner";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseBannersList(restfulService.get(urlString));
	}
	
	public ArrayList<MemberGroupType> getMemberGroupTypeList(String stateId) {	
		urlString = urlString + "/GroupById?Id="+stateId;
				
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseMemberGroupTypeList(restfulService.get(urlString));
	}
	
	public ArrayList<States> getStatesList() {
		urlString = urlString + "/MobileStates";		
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseStatesList(restfulService.get(urlString));
	}
	
	public ArrayList<UserType> getUserTypeList() {
		urlString = urlString + "/groupmembertype";				
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseUserTypeList(restfulService.get(urlString));
	}
	
	public ArrayList<ShiftType> getShiftTypeList() {
		urlString = urlString + "/membertype";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseShiftTypeList(restfulService.get(urlString));
	}
	
//	public ArrayList<Shift> getShiftList() {
//		urlString = urlString + "/shift";
//		RestfulService restfulService=new RestfulService();
//		DataParser dataParser= new DataParser();		
//		return dataParser.parseShiftList(restfulService.get(urlString));
//	}
	
	public Shift getShift(String memberShiftId) {
		urlString = urlString + "/shift/?Id="+ memberShiftId;
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseShift(restfulService.get(urlString));
	}
	
	public ArrayList<ShiftRegister> getShiftRegisterList() {
		urlString = urlString + "/shifttype";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseShiftRegisterList(restfulService.getWithoutAuthorization(urlString));
	}
	
	public ArrayList<SharingNetwork> getSharingNetworkList() {
		urlString = urlString + "/network/"+LoginActivity.idString;
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseSharingNetworkList(restfulService.get(urlString));
	}
	
	public String updateRecord(String eventNameString, List<NameValuePair> nameValuePairList) {
		urlString = urlString + "/"+ eventNameString;
		RestfulService restfulService=new RestfulService();	
		return restfulService.post(urlString,nameValuePairList);
		}
	
	public String deleteRecord(String eventNameString) {
		urlString = urlString + "/"+ eventNameString;
		RestfulService restfulService=new RestfulService();	
		return restfulService.delete(urlString);
		}
	
	
//	public StatusInfo updateRecordEnum(String eventNameString, List<NameValuePair> arrayList) {
//		urlString = urlString + "/"+ eventNameString;
//		RestfulService restfulService=new RestfulService();	
//		String str=restfulService.post(urlString,arrayList);
//		return ;
//		}
	
	public String addRecord(String eventNameString, List<NameValuePair> nameValuePairList)
	{
		urlString = urlString + "/"+eventNameString;
		RestfulService restfulService=new RestfulService();	
		return restfulService.put(urlString,nameValuePairList);
	}
	
	public String addRecordRegistration(String eventNameString, List<NameValuePair> arrayList)
	{
		urlString = urlString + "/"+eventNameString;
		RestfulService restfulService=new RestfulService();	
		return restfulService.putRegistration(urlString,arrayList);
		
	}
	
	
		
	
//	public int postAppointment(List<NameValuePair> arrayList) {
//		urlString = urlString + "/appointment";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postCompTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/comptime";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postKellyDay(List<NameValuePair> arrayList) {
//		urlString = urlString + "/kellyday";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postOverTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/overtime";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postSickTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/sicktime";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postTradeTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/tradetime";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postVacationTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/vacationtime";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
//	
//	public int postWorkersCompTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/workerscomp";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
	
	
	

//	public int updateAppointment(List<NameValuePair> arrayList) {
//		urlString = urlString + "/appointment";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.put(urlString,arrayList);
//		}
//	
//	public int updateCompTime(List<NameValuePair> arrayList) {
//		urlString = urlString + "/comptime";
//		RestfulService restfulService=new RestfulService();	
//		return restfulService.post(urlString,arrayList);
//		}
	
}

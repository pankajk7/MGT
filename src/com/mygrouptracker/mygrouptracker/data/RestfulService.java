package com.mygrouptracker.mygrouptracker.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.AbstractHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.mygrouptracker.mygrouptracker.activity.LoginActivity;
import com.mygrouptracker.mygrouptracker.utility.AuthorizationUtility;

import android.util.Base64;
import android.util.Log;

public class RestfulService {
		private int timeout = 30000;
		private HttpClient httpClient;
		private HttpPost httpPost;
		private HttpGet httpGet;
		private HttpResponse httpResponse;
		HttpPut httpPut;
		
		

		private String error;
		private String responseString = null;

		private ArrayList data;
		
		public static String responseForLogin = "";

		public RestfulService(){
			this.httpClient = new DefaultHttpClient();
			this.data = new ArrayList();
		}

		public void setTimeout(int timeout){
			this.timeout = timeout;
		}

		public void post(String url){
			this.post(url, null);
		}
		

		public String post(String url, List<NameValuePair> nameValuePairList){
			String response = "";
			httpPost = new HttpPost(url);
			httpClient.getParams().setParameter("http.socket.timeout", timeout);
			httpClient = new DefaultHttpClient();
			AuthorizationUtility authorizationObj=new AuthorizationUtility();
			httpPost.addHeader("Authorization",authorizationObj.getAuthorization(LoginActivity.userNameString,LoginActivity.sessionTokenString));
			StatusLine statusLine ;
			int responseCode=0;
			AbstractHttpEntity abstractHttpEntity;
			try {
				abstractHttpEntity = new UrlEncodedFormEntity(nameValuePairList, HTTP.UTF_8);			
				abstractHttpEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
				abstractHttpEntity.setContentEncoding("UTF-8");
				httpPost.setEntity(abstractHttpEntity);
				httpResponse =httpClient.execute(httpPost);
				
				Header[] headers = httpResponse.getAllHeaders();
				 
				for (Header header : headers) {
					if(header.getName().toString().equals("Status"))
						{
						  response=header.getValue().toString();
						  return response;
						}
				}
				
				statusLine= httpResponse.getStatusLine();
				Log.d(">>> Server Response >>>", statusLine.toString());
				
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));	
			} catch (UnsupportedEncodingException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} 
			return response;
		}

		
		public String put(String url, List<NameValuePair> nameValuePairs)
		{
			String response="";
			int responseCode=0;
			httpPut = new HttpPut(url);
			httpClient.getParams().setParameter("http.socket.timeout", timeout);
			httpClient = new DefaultHttpClient();
			AuthorizationUtility authorizationObj=new AuthorizationUtility();
			httpPut.addHeader("Authorization",authorizationObj.getAuthorization(LoginActivity.userNameString,LoginActivity.sessionTokenString));
			StatusLine statusLine ;			
			AbstractHttpEntity abstractHttpEntity;
			try {
				abstractHttpEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);			
				abstractHttpEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
				abstractHttpEntity.setContentEncoding("UTF-8");
				httpPut.setEntity(abstractHttpEntity);
				httpResponse =httpClient.execute(httpPut);
				
				Header[] headers = httpResponse.getAllHeaders();
				for (Header header : headers) {
					if(header.getName().toString().equals("Status"))
					{
						  response=header.getValue().toString();
						  return response;
					}
				}
				
				statusLine= httpResponse.getStatusLine();
				Log.d(">>> Server Response >>>", statusLine.toString());
				
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));	
			Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}
		
		public String delete(String url)
		{
			String response="";
			int responseCode=0;
			HttpDelete httpDelete = new HttpDelete(url);
			httpClient.getParams().setParameter("http.socket.timeout", timeout);
			httpClient = new DefaultHttpClient();
			AuthorizationUtility authorizationObj=new AuthorizationUtility();
			httpDelete.addHeader("Authorization",authorizationObj.getAuthorization(LoginActivity.userNameString,LoginActivity.sessionTokenString));
			StatusLine statusLine ;			
			try {
				httpResponse = httpClient.execute(httpDelete);
				statusLine= httpResponse.getStatusLine();
				 
				Header[] headers = httpResponse.getAllHeaders();
				for (Header header : headers) {
					if(header.getName().toString().equals("Status"))
					{
						  response=header.getValue().toString();
						  return response;
					}
				}
				
				statusLine= httpResponse.getStatusLine();
				Log.d(">>> Server Response >>>", statusLine.toString());
				
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));	
			Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}
	
		public String putRegistration(String url, List<NameValuePair> nameValuePairs)
		{
			String response="";
			int responseCode=0;
			httpPut = new HttpPut(url);
			httpClient.getParams().setParameter("http.socket.timeout", timeout);
			httpClient = new DefaultHttpClient();
			StatusLine statusLine ;
			AbstractHttpEntity abstractHttpEntity;
			try {
				abstractHttpEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);			
				abstractHttpEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
				abstractHttpEntity.setContentEncoding("UTF-8");
				httpPut.setEntity(abstractHttpEntity);
				httpResponse =httpClient.execute(httpPut);
				
				Header[] headers = httpResponse.getAllHeaders();
				for (Header header : headers) {
					if(header.getName().toString().equals("Status"))
					{
						  response=header.getValue().toString();
						  return response;
					}
				}
				
				statusLine= httpResponse.getStatusLine();
				Log.d(">>> Server Response >>>", statusLine.toString());
				
				responseCode = httpResponse.getStatusLine().getStatusCode();
				Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));	
			Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}
		
		
		public InputStream get(String url){			
			InputStream inputStream=null;
			httpClient.getParams().setParameter("http.socket.timeout", timeout);
			HttpContext localContext = new BasicHttpContext();
			httpGet = new HttpGet(url);
			AuthorizationUtility authorizationObj=new AuthorizationUtility();
			httpGet.addHeader("Authorization",authorizationObj.getAuthorization(LoginActivity.userNameString,LoginActivity.sessionTokenString));

			try {
				 httpResponse= httpClient.execute(httpGet,localContext);
				 HttpEntity httpEntity = httpResponse.getEntity();
				 inputStream= httpEntity.getContent();
				 StatusLine statusLine= httpResponse.getStatusLine();
					Log.d(">>> Server Response >>>", statusLine.toString());
					
				 int responseCode = httpResponse.getStatusLine().getStatusCode();
					Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));
				 return inputStream;
			} catch (UnsupportedEncodingException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			}
			return null;
		}	

		public String getError() {
			return this.error;
		}
		
		public InputStream getLogin(String urlString,String userName, String password)
		{			
			InputStream inputStream=null;
			httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			httpGet = new HttpGet(urlString);
			AuthorizationUtility authorizationObj=new AuthorizationUtility();

			httpGet.addHeader("Authorization",authorizationObj.getLoginAuthorization(userName, password));
			try {
				 httpResponse= httpClient.execute(httpGet,localContext);
				 HttpEntity httpEntity = httpResponse.getEntity();
				 
				 Header[] headers = httpResponse.getAllHeaders();
					for (Header header : headers) {
						if(header.getName().toString().equals("Status"))
						{
							  responseForLogin=header.getValue().toString();
							  if(!responseForLogin.equals("Success")){
								  return null;
							  }
							  break;
						}
					}
					
				 inputStream = httpEntity.getContent();
				 StatusLine statusLine= httpResponse.getStatusLine();
					Log.d(">>> Server Response >>>", statusLine.toString());
					
				 int responseCode = httpResponse.getStatusLine().getStatusCode();
					Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));
				 return inputStream;
			   }
				catch(Exception ex)
				{
					Log.d(">>>Login User", ex.toString()+">>>"+ex.getMessage());
				}							
			return inputStream;			
		}
		
		public InputStream getWithoutAuthorization(String url){			
			InputStream inputStream=null;
			httpClient.getParams().setParameter("http.socket.timeout", timeout);
			HttpContext localContext = new BasicHttpContext();
			httpGet = new HttpGet(url);

			try {
				 httpResponse= httpClient.execute(httpGet,localContext);
				 HttpEntity httpEntity = httpResponse.getEntity();
				 inputStream= httpEntity.getContent();
				 Header[] headers = httpResponse.getAllHeaders();
				 
					for (Header header : headers) {
						System.out.println("Key : " + header.getName() 
						      + " ,Value : " + header.getValue());
						Log.e("Headers", "Key : " + header.getName() 
						      + " ,Value : " + header.getValue());
					}
				 StatusLine statusLine= httpResponse.getStatusLine();
					Log.d(">>> Server Response >>>", statusLine.toString());
					
				 int responseCode = httpResponse.getStatusLine().getStatusCode();
					Log.d(">>> Server Response Code >>>", String.valueOf(responseCode));
				 return inputStream;
			} catch (UnsupportedEncodingException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				this.error = e.getMessage();
				e.printStackTrace();
			}
			return null;
		}	

		public String getResponseText(){
			return this.responseString;
		}
	
}

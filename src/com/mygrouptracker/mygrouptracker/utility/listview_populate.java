package com.mygrouptracker.mygrouptracker.utility;

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

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.adapter.ListBaseAdapter;

public class listview_populate {
	
	// XML node keys    
    static final String KEY_ID = "id";
    static final String KEY_ICON = "icon";
    static final String KEY_NAME = "name";
    
    List<HashMap<String,String>> homeDataCollection;
    
	public List<HashMap<String,String>> populateListView(Document doc, String rawxmlString, String KEY_TAG)
	{

      try {			
    	    

	        homeDataCollection = new ArrayList<HashMap<String,String>>();
	        
	        // normalize text representation
            doc.getDocumentElement ().normalize ();
	                    
            NodeList homelist = doc.getElementsByTagName(KEY_TAG);
            
			HashMap<String,String> map = null;
			
			for (int i = 0; i < homelist.getLength(); i++) {
				 
				   map = new HashMap<String,String>(); 
				   
				   Node HomeNode = homelist.item(i);
				   
	                if(HomeNode.getNodeType() == Node.ELEMENT_NODE){

	                    Element HomeElement = (Element)HomeNode;
	                    //1.-------
	                    NodeList idList = HomeElement.getElementsByTagName(KEY_ID);
	                    Element firstIdElement = (Element)idList.item(0);
	                    NodeList textIdList = firstIdElement.getChildNodes();
	                    //--id
	                    map.put(KEY_ID, ((Node)textIdList.item(0)).getNodeValue().trim());
	                    
	                  //2.-------
	                    NodeList iconList = HomeElement.getElementsByTagName(KEY_ICON);
	                    Element IconElement = (Element)iconList.item(0);
	                    NodeList textIconList = IconElement.getChildNodes();
	                    //--icon list
	                    map.put(KEY_ICON, ((Node)textIconList.item(0)).getNodeValue().trim());
	                    
	                    //3.----
	                    NodeList nameList = HomeElement.getElementsByTagName(KEY_NAME);
	                    Element cityElement = (Element)nameList.item(0);
	                    NodeList textCityList = cityElement.getChildNodes();
	                    //Name
	                    map.put(KEY_NAME, ((Node)textCityList.item(0)).getNodeValue().trim());
	               
	                    //Add to the Arraylist
	                    homeDataCollection.add(map);
				}		
			}
				return homeDataCollection;	
			
		}
		catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
      
		return null;
		
	}

}

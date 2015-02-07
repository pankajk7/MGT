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
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;

public class SingleNameListPopulate {
	
	// XML node keys    
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    
    List<SingleNameListEntity> singlenameList;
    
	public List<SingleNameListEntity> populateListView(Document doc, String rawxmlString, String KEY_TAG)
	{

      try {			
    	    

    	  singlenameList = new ArrayList<SingleNameListEntity>();
	        
	        // normalize text representation
            doc.getDocumentElement ().normalize ();
	                    
            NodeList stationList = doc.getElementsByTagName(KEY_TAG);
            
			SingleNameListEntity objSingleName;
			
			for (int i = 0; i < stationList.getLength(); i++) {
				 
				objSingleName=new SingleNameListEntity();
				   
				   Node HomeNode = stationList.item(i);
				   
	                if(HomeNode.getNodeType() == Node.ELEMENT_NODE){

	                    Element HomeElement = (Element)HomeNode;
	                    //1.-------
	                    NodeList idList = HomeElement.getElementsByTagName(KEY_ID);
	                    Element firstIdElement = (Element)idList.item(0);
	                    NodeList textIdList = firstIdElement.getChildNodes();
	                    //--id
	                    objSingleName.id=((Node)textIdList.item(0)).getNodeValue().trim();
	                    
	                    
	                    //2.----
	                    NodeList nameList = HomeElement.getElementsByTagName(KEY_NAME);
	                    Element cityElement = (Element)nameList.item(0);
	                    NodeList textCityList = cityElement.getChildNodes();
	                    //Name
	                    objSingleName.linkName=((Node)textCityList.item(0)).getNodeValue().trim();
	               
	                    //Add to the Arraylist
	                    singlenameList.add(objSingleName);	                    
				}		
			}
				return singlenameList;	
			
		}
		catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
      
		return null;
		
	}

}

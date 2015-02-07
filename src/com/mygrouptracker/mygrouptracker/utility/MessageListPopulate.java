package com.mygrouptracker.mygrouptracker.utility;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;

public class MessageListPopulate {

	// XML node keys
    static final String KEY_TAG = "message"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_DATE = "date";
    static final String KEY_TIME = "time";
    static final String KEY_HEADER = "header";
    static final String KEY_TEXT = "text";
    
    List<MessageEntity> messageList;
    
    public List<MessageEntity> populateListView(Document doc, String rawxmlString, String KEY_TAG)
	{

      try {			
    	    

    	  messageList = new ArrayList<MessageEntity>();
	        
	        // normalize text representation
            doc.getDocumentElement ().normalize ();
	                    
            NodeList stationList = doc.getElementsByTagName(KEY_TAG);
            
            MessageEntity objMessageEntity;
			
			for (int i = 0; i < stationList.getLength(); i++) {
				 
				objMessageEntity=new MessageEntity();
				   
				   Node HomeNode = stationList.item(i);
				   
	                if(HomeNode.getNodeType() == Node.ELEMENT_NODE)
	                {

	                    Element HomeElement = (Element)HomeNode;
	                    //1.-------
	                    NodeList idList = HomeElement.getElementsByTagName(KEY_ID);
	                    Element firstIdElement = (Element)idList.item(0);
	                    NodeList textIdList = firstIdElement.getChildNodes();
	                    //--id
	                    objMessageEntity.id=((Node)textIdList.item(0)).getNodeValue().trim();
	                    
	                    
	                    //2.----
	                    NodeList dateList = HomeElement.getElementsByTagName(KEY_DATE);
	                    Element dateElement = (Element)dateList.item(0);
	                    NodeList textDATEList = dateElement.getChildNodes();
	                    //DATE
	                    objMessageEntity.date=((Node)textDATEList.item(0)).getNodeValue().trim();
	                    
//	                  //3.
//	                    NodeList timeList = HomeElement.getElementsByTagName(KEY_TIME);
//	                    Element timeElement = (Element)timeList.item(0);
//	                    NodeList textTimeList = timeElement.getChildNodes();
//	                    //TIME
//	                    objMessageEntity.time=((Node)textTimeList.item(0)).getNodeValue().trim();
	                    
//	                    //4.
//	                    NodeList headerList = HomeElement.getElementsByTagName(KEY_HEADER);
//	                    Element headerElement = (Element)headerList.item(0);
//	                    NodeList textHeaderList = headerElement.getChildNodes();
//	                    //HEADER
//	                    objMessageEntity.header=((Node)textHeaderList.item(0)).getNodeValue().trim();
	                    
	                    
//	                    //5.
//	                    NodeList txtList = HomeElement.getElementsByTagName(KEY_TEXT);
//	                    Element txtElement = (Element)txtList.item(0);
//	                    NodeList textList = txtElement.getChildNodes();
//	                    //TEXT
//	                    objMessageEntity.text=((Node)textList.item(0)).getNodeValue().trim();
	                    
	               
	                    //Add to the Arraylist
	                    messageList.add(objMessageEntity);
	                    
				    }//End of if	
	                
			  }//End of For
				return messageList;	
			
		}//End of try
		catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
      
		return null;
		
	}

}
    


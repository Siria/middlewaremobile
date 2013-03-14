package valutatori;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class XMLBuilder {
        
    @SuppressWarnings("empty-statement")
    public Document parseStringToXML(String message){
        Document document = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(new ByteArrayInputStream(message.getBytes("UTF-8")));
            document.getDocumentElement().normalize();
        } catch (SAXException | IOException | ParserConfigurationException ex) {
        }
        return document;
    }
    
    public String getElement(Document document, String tagElement){
        String element = null;
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();  
            element = (xpath.evaluate(tagElement, document.getDocumentElement())).trim();          
        } catch (XPathExpressionException ex) {
            
        }
        return element;
    }
    
    public String makeXMLString(String fromName, String fromIP, String toName, String toIP,
            String toDestParam1, String toDestParam2, String communicationChannel, 
            String objectType, String objectContext, Map<String, String> bodyParams){
        String message = "<message><header><from>";
        if(fromName != null){
            message += "<name>" + fromName + "</name>";
        }
        if(fromIP != null){
            message += "<ip>" + fromIP + "</ip>";
        }
        message += "</from><to>";
        if(toName != null){
           message += "<name>" + toName + "</name>";
        }
        if(toIP != null){
            message += "<ip>" + toIP + "</ip>";
        }
        if(toDestParam1 != null){
            message += "<dest_param1>" + toDestParam1 + "</dest_param1>";
        }
        if(toDestParam2 != null){
            message += "<dest_param2>" + toDestParam2 + "</dest_param2>";
        }
        message += "</to><communication>";
        if(!communicationChannel.equals("") || communicationChannel != null){
            message += "<channel>" + communicationChannel +
                    "</channel>";
        }
        message += "</communication><object>";
        if(objectType != null){
            message += "<type>" + objectType + "</type>";
        }
        if(objectContext != null){
            message += "<context>" + objectContext + "</context>";
        }
        message += "</object></header><body>";
        if(bodyParams !=null){
            for(Iterator<String> it = bodyParams.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                String value = bodyParams.get(key);
                message += "<" + key +">" + value + "</" + key + ">";
            }
        }
        message += "</body></message>";
        return message;  
    }
    
}

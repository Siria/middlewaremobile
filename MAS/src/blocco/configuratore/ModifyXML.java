/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.configuratore;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author alessandra
 */
public class ModifyXML /*implements Runnable*/{

    public static void main(String args[]){
    //@Override
    //public void run(){

	    try {
		 
                 String filepath = "c:\\file.xml" ;
                 
		 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();                   
		 DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                 
		 Document doc = docBuilder.parse("file:///home/alessandra/NetBeansProjects/MAS/c:%5Cfile.xml");

                 System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                 NodeList nodeLst = doc.getElementsByTagName("channel");
                 System.out.println("Modifica in corso....");

		 // Get the root element
		 Node configurazioni = doc.getFirstChild();
		 // getElementsByTagName() per prenderlo direttamente.
		 // Node socket = channel.getFirstChild();
		 Node channel = doc.getElementsByTagName("channel").item(0);

		 // update attribute


                 // append a new node 
                 Element channel2 = doc.createElement("channel");
                 channel2.setAttribute("id", "002");
                 configurazioni.appendChild(channel2);
		 
		 Element soap = doc. createElement ( "type" ) ;                 
                 soap.appendChild(doc.createTextNode("soap"));
		 channel2.appendChild(soap);             
                 
                 Element port = doc.createElement("port");
                 port.appendChild(doc.createTextNode("5050"));
                 channel2.appendChild(port);
                 
		 Element host = doc.createElement("host");
		 host.appendChild(doc.createTextNode("http://localhost:"));
		 channel2.appendChild(host);

		 // loop the staff child node
		 NodeList list = channel.getChildNodes ( ) ;

		 for ( int i = 0 ; i < list. getLength ( ) ; i ++ ) {

                    Node node = list.item( i ) ;

		    // cambia id del canale di comunicazione
		    if ( "socket" . equals ( node. getNodeName ( ) ) ) {
                        NamedNodeMap old = node.getAttributes () ;
                        Node nattr = old.getNamedItem ("id") ;
                        nattr.setTextContent("" ) ;
			 
		    }

		 }

		 // write the content into xml file
		 TransformerFactory transformerFactory = TransformerFactory. newInstance ( ) ;
		 Transformer transformer = transformerFactory. newTransformer ( ) ;
		 DOMSource source = new DOMSource ( doc ) ;
		 StreamResult result = new StreamResult ( new File ( filepath ) ) ;
		 transformer. transform ( source, result ) ;

		 System . out . println ( "Modifica effettuata" ) ;

	    } catch ( ParserConfigurationException | TransformerException | IOException | SAXException pce ) {
	    }
	 }
    
      public void updateAttribute(Node channel,String value){
          
          NamedNodeMap attr = channel.getAttributes( ) ;
	  Node nodeAttr = attr.getNamedItem("id");
	  nodeAttr.setTextContent (value);
          
    }     
    
      public void removeNode(Node rootnode,String namenode){
           NodeList list = rootnode. getChildNodes ( ) ;
		 for ( int i = 0 ; i < list. getLength ( ) ; i ++ ) {
                    Node node = list. item ( i ) ;
		    if ( "namenode" . equals ( node. getNodeName ( ) ) ) {
			 rootnode. removeChild ( node ) ;
		    }

		 } 
      
      }
      
       public void updateNode(Node rootnode,String namenode, String value){
           NodeList list = rootnode. getChildNodes ( ) ;
		 for ( int i = 0 ; i < list. getLength ( ) ; i ++ ) {
                    Node node = list. item ( i ) ;
		    if ( "namenode" . equals ( node. getNodeName ( ) ) ) {
			  node. setTextContent ( "value" ) ;;
		    }

		 } 
      
      }
 } 
    


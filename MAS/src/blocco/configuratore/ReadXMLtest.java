/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.configuratore;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author alessandra
 */
public class ReadXMLtest {
    
     public static void main(String argv[]) {
 
    try {
 
	//File fXmlFile = new File("file:///home/alessandra/NetBeansProjects/MAS/c:%5Cfile.xml");
        File fXmlFile = new File("c:\\file.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
 
	NodeList nList = doc.getElementsByTagName("channel");
 
	System.out.println("----------------------------");
 
	for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
 
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
                                // non riesco a vedere l'attribute
 
			System.out.println("Id channel : " + eElement.getAttribute("id"));
                        System.out.println("Tipo : " + eElement.getElementsByTagName("type").item(0).getTextContent());
			System.out.println("Porta : " + eElement.getElementsByTagName("port").item(0).getTextContent());
			System.out.println("Host : " + eElement.getElementsByTagName("host").item(0).getTextContent());
			
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
 
}
    


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.configuratore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import temp.proxy.AlgoritmoProxy;
/**
 *
 * @author alessandra
 */
public class AlgoConfSoglie implements AlgoritmoProxy {

    HashMap<Object, Object> hmap = new HashMap<>();
    static Variabile var = new Variabile();
    private String min;
    private String max;
    private String variabile;

    @Override
    public Object valuta(Object messaggio) {
      //  HashMap receive = (HashMap) messaggio;

       // if (!receive.equals(hmap)) {
            try {

                //File fXmlFile = new File("file:///home/alessandra/NetBeansProjects/MAS/c:%5Cfile.xml");
                File fXmlFile = new File("c:\\file_soglie.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("soglia");

                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    System.out.println("\nCurrent Element :" + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        
                        
                        min = eElement.getElementsByTagName("min").item(0).getTextContent();
                        max = eElement.getElementsByTagName("max").item(0).getTextContent();
                        variabile = eElement.getElementsByTagName("variabile").item(0).getTextContent();                              
                        var.setMin(min);
                        var.setMax(max);
                        hmap.put(variabile, var);
                        return hmap;

                    }
                }
            } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
                e.printStackTrace();
           // }
        }
        return null;
    }
}

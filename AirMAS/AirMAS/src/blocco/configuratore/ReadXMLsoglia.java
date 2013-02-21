/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.configuratore;
import java.io.File;
import java.util.HashMap;
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
public class ReadXMLsoglia implements Runnable{
    
    public static HashMap<Object, Object> hmap = new HashMap<>();
    static Variabile var = new Variabile();
    
    @Override
    public void run() {

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
                    // non riesco a vedere l'attribute
                    
                    var.min = eElement.getElementsByTagName("min").item(0).getTextContent();
                    var.max = eElement.getElementsByTagName("max").item(0).getTextContent();
                    hmap.put(eElement.getElementsByTagName("variabile").item(0).getTextContent(), var);

                    

                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.configuratore;

import java.io.File;
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

/**
 *
 * @author alessandra
 */
public class WriteXMLsoglia implements Runnable{
     
 //public WriteXML(String name){

    @Override
    public void run(){
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder(); 		
		Document doc = docBuilder.newDocument();
		//Element rootElement = doc.createElement("configurazioni");
		//doc.appendChild(rootElement);               
                  
                
                Element sogliafiltro = doc.createElement("sogliafiltro");
               //sogliafiltro.setAttribute("id", "0000");
                doc.appendChild(sogliafiltro);
                        
                //inizio creazione valori soglia
                Element soglia1 = doc.createElement("soglia");                             
		soglia1.setAttribute("id", "01"); 
                sogliafiltro.appendChild(soglia1);
                                
                
                Element x = doc.createElement("variabile");
                x.appendChild(doc.createTextNode("x"));
                soglia1.appendChild(x);
                
                Element min1 = doc.createElement("min");
                min1.appendChild(doc.createTextNode("0"));
                soglia1.appendChild(min1);
                
                Element max1 = doc.createElement("max");
                max1.appendChild(doc.createTextNode("3"));
                soglia1.appendChild(max1);
                
                              
                Element soglia2 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia2);                              
		soglia2.setAttribute("id", "02");
                
                Element y = doc.createElement("variabile");
                y.setTextContent("y");
                soglia2.appendChild(y);
                
                Element min2 = doc.createElement("min");
                min2.setTextContent("0");
                soglia2.appendChild(min2);
                
                Element max2 = doc.createElement("max");
                max2.setTextContent("3");
                soglia2.appendChild(max2);
                
                
                Element soglia3 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia3);                              
		soglia3.setAttribute("id", "03");
                
                Element z = doc.createElement("variabile");
                z.setTextContent("z");
                soglia3.appendChild(z);
                
                Element min3 = doc.createElement("min");
                min3.setTextContent("0");
                soglia3.appendChild(min3);
                
                Element max3 = doc.createElement("max");
                max3.setTextContent("3");
                soglia3.appendChild(max3);
                
                Element soglia4 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia4);                              
		soglia4.setAttribute("id", "04");
                
                Element v1 = doc.createElement("variabile");
                v1.setTextContent("v1");
                soglia4.appendChild(v1);
                
                Element min4 = doc.createElement("min");
                min4.setTextContent("0");
                soglia4.appendChild(min4);
                
                Element max4 = doc.createElement("max");
                max4.setTextContent("195");
                soglia4.appendChild(max4);
                
                
                Element soglia5 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia5);                              
		soglia5.setAttribute("id", "05");
                
                Element v2 = doc.createElement("variabile");
                v2.setTextContent("v2");
                soglia5.appendChild(v2);
                
                Element min5 = doc.createElement("min");
                min5.setTextContent("0");
                soglia5.appendChild(min5);
                
                Element max5 = doc.createElement("max");
                max5.setTextContent("195");
                soglia5.appendChild(max5);
                
                
                Element soglia6 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia6);                              
		soglia6.setAttribute("id", "06");
                
                Element v3 = doc.createElement("variabile");
                v3.setTextContent("v3");
                soglia6.appendChild(v3);
                
                Element min6 = doc.createElement("min");
                min6.setTextContent("0");
                soglia6.appendChild(min6);
                
                Element max6 = doc.createElement("max");
                max6.setTextContent("195");
                soglia6.appendChild(max6);
                
                
                Element soglia7 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia7);                              
		soglia7.setAttribute("id", "07");
                
                Element a1 = doc.createElement("variabile");
                a1.setTextContent("a1");
                soglia7.appendChild(a1);
                
                Element min7 = doc.createElement("min");
                min7.setTextContent("2,5");
                soglia7.appendChild(min7);
                
                Element max7 = doc.createElement("max");
                max7.setTextContent("7,5");
                soglia7.appendChild(max7);
                
                
                Element soglia8 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia8);                              
		soglia8.setAttribute("id", "08");
                
                Element a2 = doc.createElement("variabile");
                a2.setTextContent("a2");
                soglia8.appendChild(a2);
                
                Element min8 = doc.createElement("min");
                min8.setTextContent("2,5");
                soglia8.appendChild(min8);
                
                Element max8 = doc.createElement("max");
                max8.setTextContent("7,5");
                soglia8.appendChild(max8);
                
                Element soglia9 = doc.createElement("soglia");
                sogliafiltro.appendChild(soglia9);                              
		soglia9.setAttribute("id", "09");
                
                Element a3 = doc.createElement("variabile");
                a3.setTextContent("a3");
                soglia9.appendChild(a3);
                
                Element min9 = doc.createElement("min");
                min9.setTextContent("2,5");
                soglia9.appendChild(min9);
                
                Element max9 = doc.createElement("max");
                max9.setTextContent("7,5");
                soglia9.appendChild(max9);
                

    
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("c:\\file_soglie.xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File salvato");
 
	  } catch (ParserConfigurationException | TransformerException pce) {
	  }
	
 }

  /*  public static void main(String argv[]) {
    
        WriteXML crea = new WriteXML(argv[0]);
}*/
    



}
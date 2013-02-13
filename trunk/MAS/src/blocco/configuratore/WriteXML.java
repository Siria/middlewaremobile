/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.configuratore;

import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
/**
 *
 * @author alessandra
 */
public class WriteXML /*implements Runnable*/{
    
    
 //public WriteXML(String name){
    public static void main(String args[]){
    //@Override
    //public void run(){
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder(); 		
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("configurazioni");
		doc.appendChild(rootElement);               
                
                // contiene i canali attivi in numero, se scrivo ALL significa TUTTI-ATTIVI              
                Element channel = doc.createElement("channel");
                // channel.setTextContent("3");
                rootElement.appendChild(channel);                              
                // setto un attributo dell'elemento, numero dei canali attivi, ripetizione
		channel.setAttribute("id", "001");
 
		// elementi del canale di comunicazione,
                //il sontenuto indica id del canale di comunicazione 
		Element socket = doc.createElement("type");    
                socket.appendChild(doc.createTextNode("socket"));
		channel.appendChild(socket);
                
                // porta
		Element port = doc.createElement("port");
		port.appendChild(doc.createTextNode("4848"));
		channel.appendChild(port);
                
		// indirizzo host
		Element host = doc.createElement("host");
		host.appendChild(doc.createTextNode("http://localhost:"));
		channel.appendChild(host);

                // trasformazione in xml 
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("c:\\file.xml"));
 
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
/*
 * StreamResult result =  new StreamResult(System.out);
    transformer.transform(source, result);
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;

import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.ProxyTarget;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Seby
 */
public class XMLStarter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
    try {
 
	File file = new File("config.xml");
	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document doc = dBuilder.parse(file);
	
	if (doc.hasChildNodes()) {
		parse(doc.getChildNodes().item(0).getChildNodes());
	}
        
        
            Thread.sleep(6000);
            
            System.out.println("\t ----------------------------------------");
            System.out.println("\t --- Middleware for Autonomic Systems ---");
            System.out.println("\t ----------------------------------------");
            System.out.println("\n\nTutto OK: avviare un Client...");
        
            
        
    } catch (Exception e) {
	System.out.println(e.getMessage());
    }
 
  }
 
  private static void parse(NodeList nodeList) {
 try{
    for (int count = 0; count < nodeList.getLength(); count++) {
	Node tempNode = nodeList.item(count);
	if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.getNodeName().equals("blocco")){
                    
                    Blocco blocco = new Blocco();
                    
                    if (tempNode.hasChildNodes()){   
                        NodeList listaTrasmettitori = tempNode.getChildNodes().item(1).getChildNodes(); // Trasmettitori
                            for (int c = 0; c < listaTrasmettitori.getLength(); c++){
                                Node trasmettitore = listaTrasmettitori.item(c);
                                if (trasmettitore.getNodeType() == Node.ELEMENT_NODE) {
        
                                //System.out.println(trasmettitore.getNodeName());
                                NodeList valoriTrasmettitore = trasmettitore.getChildNodes();
                                    for (int d = 0; d < valoriTrasmettitore.getLength(); d++){
                                        Node valoreTrasmettitore = valoriTrasmettitore.item(d);
                                        if (valoreTrasmettitore.getNodeType() == Node.ELEMENT_NODE) {
         
                                        //System.out.println(valoreTrasmettitore.getNodeName());
                                
                                        switch (valoreTrasmettitore.getNodeName()){
                                            case("class"):
                                                //System.out.println(valoreTrasmettitore.getNodeValue()+"+"+valoreTrasmettitore.getTextContent());
                                                blocco.getTrasmettitori().add(new AdapterTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(Class.forName(valoreTrasmettitore.getTextContent()).newInstance())));        
                                                break;
                                                
                                            case("config"):
                                                //System.out.println(valoreTrasmettitore.getNodeValue()+"+"+valoreTrasmettitore.getTextContent()+"+"+valoreTrasmettitore.getAttributes().item(0).getNodeValue());
                                                blocco.getConf().put(valoreTrasmettitore.getAttributes().item(0).getNodeValue(), valoreTrasmettitore.getTextContent());
                                            break;     
                                        }
                                    }
                            }
                        }
                        }
                        NodeList listaRicevitori = tempNode.getChildNodes().item(3).getChildNodes(); // Ricevitori 
                            for (int c = 0; c < listaRicevitori.getLength(); c++){
                                
                                Node ricevitore = listaRicevitori.item(c);
                                if (ricevitore.getNodeType() == Node.ELEMENT_NODE) {
        
                                //System.out.println(ricevitore.getNodeName());
                                
                                NodeList valoriRicevitore = ricevitore.getChildNodes();
                                    for (int d = 0; d < valoriRicevitore.getLength(); d++){
                                        Node valoreRicevitore = valoriRicevitore.item(d);
                                        if (valoreRicevitore.getNodeType() == Node.ELEMENT_NODE) {
        
                                        //System.out.println(valoreRicevitore.getNodeName());
                                
                                        switch (valoreRicevitore.getNodeName()){
                                            case("class"):
                                                //System.out.println(valoreRicevitore.getNodeValue()+"+"+valoreRicevitore.getTextContent());
                                                blocco.getRicevitori().add(new AdapterRicevitore((RicevitoreProxy)ProxyTarget.createProxy(Class.forName(valoreRicevitore.getTextContent()).newInstance())));        
                                                break;
                                                
                                            case("config"):
                                                //System.out.println(valoreRicevitore.getNodeValue()+"+"+valoreRicevitore.getTextContent()+"+"+valoreRicevitore.getAttributes().item(0).getNodeValue());
                                                blocco.getConf().put(valoreRicevitore.getAttributes().item(0).getNodeValue(), valoreRicevitore.getTextContent());
                                            break;     
                                        }
                                    }
                            }
                                }}
                        NodeList valoriAlgoritmo = tempNode.getChildNodes().item(5).getChildNodes(); // Algoritmo
                                    for (int d = 0; d < valoriAlgoritmo.getLength(); d++){
                                        Node valoreAlgoritmo = valoriAlgoritmo.item(d);
                                        if (valoreAlgoritmo.getNodeType() == Node.ELEMENT_NODE) {
        
                                        switch (valoreAlgoritmo.getNodeName()){
                                            case("class"):
                                                //System.out.println(valoreAlgoritmo.getNodeValue()+"+"+valoreAlgoritmo.getTextContent());
                                                blocco.setAlgoritmo(new AdapterAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(Class.forName(valoreAlgoritmo.getTextContent()).newInstance())));        
                                                break;
                                                
                                            case("config"):
                                                //System.out.println(valoreAlgoritmo.getNodeValue()+"+"+valoreAlgoritmo.getTextContent()+"+"+valoreAlgoritmo.getAttributes().item(0).getNodeValue());
                                                blocco.getConf().put(valoreAlgoritmo.getAttributes().item(0).getNodeValue(), valoreAlgoritmo.getTextContent());
                                            break;     
                                        }
                                    }
                                    } 
                    }
                    Thread th = new Thread (blocco);
                    th.start();

                    Thread.sleep(1000);
                }
        }
 
  }
    } catch(Exception e){
        e.printStackTrace();
    }
}
    
}
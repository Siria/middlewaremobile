/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;



import blocco.Evento;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import ricezione.SOAP;


public class ClientSOAP {

    public static void main(String[] args) throws Exception {
        
        HashMap prop = new HashMap();
        prop.put("type", "posizione");
        prop.put("context", "start");
        prop.put("content", new int[]{2,2,2});
        Evento e = new Evento(prop);
        
        for (int i = 0; i<1; i++){
            invia(e.toString());
        }
        
    }

    public static void invia(Object messaggio){
        try {
            URL url = new URL("http://localhost:17780/WS/SOAP/?wsdl");
            QName qname = new QName("http://ricezione/", "RicevitoreSOAPService");
            Service service = Service.create(url, qname);
            
            SOAP calc = (SOAP)service.getPort(SOAP.class);
            calc.ricevi(messaggio);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
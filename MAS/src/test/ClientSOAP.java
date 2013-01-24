/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import temp.ricevitori.SOAP.SOAP;


public class ClientSOAP {

    public static void main(String[] args) throws Exception {
        
        invia("Inizio SOAP... ");

        
    }

    public static void invia(Object messaggio){
        try {
            URL url = new URL("http://localhost:17780/WS/SOAP?wsdl");
            QName qname = new QName("http://SOAP.ricevitori.temp/", "RicevitoreSOAPService");
            Service service = Service.create(url, qname);
            
            SOAP calc = (SOAP)service.getPort(SOAP.class);
            calc.ricevi(messaggio);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
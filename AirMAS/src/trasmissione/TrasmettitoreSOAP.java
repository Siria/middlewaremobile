/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trasmissione;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;
import ricezione.SOAP;


/**
 *
 * @author alessandra
 */
public class TrasmettitoreSOAP implements TrasmettitoreProxy{

    private URL url;
    
    @Override
    public void invia(Object messaggio){
        try {
            QName qname = new QName("http://ricezione/", "RicevitoreSOAPService");
            Service service = Service.create(url, qname);
            
            SOAP calc = (SOAP)service.getPort(SOAP.class);
            calc.ricevi(messaggio);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        try {
            this.url = new URL((String)conf.get("soapUscita"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(TrasmettitoreSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

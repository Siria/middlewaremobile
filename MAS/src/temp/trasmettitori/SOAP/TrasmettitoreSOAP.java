/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.SOAP;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import temp.ricevitori.SOAP.SOAP;
import temp.trasmettitori.AbstractTrasmettitore;


/**
 *
 * @author Seby
 */
public class TrasmettitoreSOAP extends AbstractTrasmettitore{

    private URL url;
    
    public TrasmettitoreSOAP(URL url) {
        this.url = url;
    }

    
    
    @Override
    public void invia(Object messaggio){
        try {
            QName qname = new QName("http://SOAP.ricevitori.temp/", "RicevitoreSOAPService");
            Service service = Service.create(url, qname);
            
            SOAP calc = (SOAP)service.getPort(SOAP.class);
            calc.ricevi(messaggio);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}

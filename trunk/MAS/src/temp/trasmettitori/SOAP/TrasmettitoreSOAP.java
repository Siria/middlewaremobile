/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.SOAP;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import temp.proxy.TrasmettitoreProxy;
import temp.ricevitori.SOAP.SOAP;


/**
 *
 * @author Seby
 */
public class TrasmettitoreSOAP implements TrasmettitoreProxy{

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

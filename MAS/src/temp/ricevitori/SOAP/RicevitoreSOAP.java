/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.SOAP;

/**
 *
 * @author Seby
 */
import java.util.HashMap;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.http.HTTPBinding;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

@WebService(endpointInterface = "temp.ricevitori.SOAP.SOAP")
public class RicevitoreSOAP implements SOAP,RicevitoreProxy{
    
    Monitor monitor;
    public HashMap conf = null;
	@Override
	public void ricevi(Object messaggio) {
		System.out.println("Ricevo tramite SOAP...");
                this.enqueue(messaggio);                
        }
        
        @Override
        public void ricevi() {
            Endpoint.publish((String)conf.get("soapIngresso"),this);
        }
  
    
    public void enqueue(Object messaggio){
        monitor.accodaMessaggio(messaggio);
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        
        this.conf = conf;
        this.monitor = monitor;
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    ricevi();
                }
            });
            t.start();
    }

    
}
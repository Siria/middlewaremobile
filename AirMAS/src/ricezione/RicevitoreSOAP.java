/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ricezione;

/**
 *
 * @author Seby
 */
import blocco.Configurazione;
import java.util.HashMap;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.http.HTTPBinding;
import blocco.proxy.RicevitoreProxy;
import blocco.queue.Monitor;

@WebService(endpointInterface = "ricezione.SOAP")
public class RicevitoreSOAP implements SOAP,RicevitoreProxy{
    
    Monitor monitor;
    public Configurazione conf = null;
	@Override
	public void ricevi(Object messaggio) {
		System.out.println("\nRicevo tramite SOAP...");
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
    public void configura(Monitor monitor, Configurazione conf) {
        
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
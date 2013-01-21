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
import javax.xml.ws.Endpoint;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

@WebService(endpointInterface = "temp.ricevitori.SOAP.SOAP")
public class RicevitoreSOAP implements SOAP,RicevitoreProxy{
    
    Monitor monitor;
    public HashMap conf = null;
	@Override
	public void ricevi(Object messaggio) {
		System.out.println("Ricevo tramite SOAP");
                this.enqueue(messaggio);                
        }
        

        

        @Override
        public void ricevi() {
            Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"/WS/SOAP",this);
        }
        
        
    
    
    
    public RicevitoreSOAP(Monitor monitor, HashMap conf){
        this.conf = conf;
        this.monitor = monitor;
    }

    public final void mettitiInAscolto(){
        
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    ricevi();
                }
            });
            t.start();
    }
    
    public void enqueue(Object messaggio){
        monitor.accodaRichiesta(messaggio);
    }

    
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;


import com.sun.jersey.api.client.WebResource;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.ws.Endpoint;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

// nel ricevitore devo configurare la porta ascolto


public class RicevitoreREST  implements REST,RicevitoreProxy {
    Monitor monitor;
    public HashMap conf = null;

    @Override
    @GET
    @Path("/Risorsa")
    public void ricevi(@PathParam("evento") Object messaggio){      
        System.out.println("Ricevo tramite REST..");

        System.out.println();
        this.inviaPerValutazione(messaggio);

        this.enqueue(messaggio);

    //per ricevere il messaggio    
    }
    
    
    
    @Override 
    public void ricevi(){
    // configurazione porta
         Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"WebAppEvent/webresources",this);
   
    }
   
    
    @Override
    public void enqueue(Object messaggio){
    	monitor.accodaRichiesta(messaggio);
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        
        Thread t = new Thread(new Runnable() {
            
            @Override
            public void run() {
                    ricevi();
                }
            });
            t.start();
    }

    private void inviaPerValutazione(Object messaggio) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



}







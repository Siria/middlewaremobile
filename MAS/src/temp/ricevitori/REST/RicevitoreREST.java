/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

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
    @Path("temp.trasmettitori.REST/Invio")
    public void ricevi(@PathParam("messaggio") Object messaggio){
        System.out.println("Ricevo tramite REST..");
        this.enqueue(messaggio);
    //per ricevere il messaggio    
    }
    
    
    
    @Override 
    public void ricevi(){
    // configurazione porta
         Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"/WS/REST",this);
   
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







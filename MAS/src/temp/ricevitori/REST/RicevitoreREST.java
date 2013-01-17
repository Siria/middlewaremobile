/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.ws.Endpoint;
import temp.proxy.RicevitoreProxy;

// nel ricevitore devo configurare la porta ascolto


public class RicevitoreREST  implements REST,RicevitoreProxy {
    ObjectOutputStream OOS = null;
    public HashMap conf = null;

    @Override
    @GET
    @Path("temp.trasmettitori.REST/Invio")
    public void ricevi(@PathParam("messaggio") Object messaggio){
        System.out.println("Ricevo tramite REST..");
        this.inviaPerValutazione(messaggio);
    //per ricevere il messaggio    
    }
    
    
    
    @Override 
    public void ricevi(){
    // configurazione porta
         Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"/WS/REST",this);
   
    }

    
    public final void mettitiInAscolto(){
        try{
            Socket SK = new Socket("localhost",(int)conf.get("portaAscoltoInterna"));
            OOS = new ObjectOutputStream(SK.getOutputStream());	
  	}catch(Exception e){
            e.printStackTrace();
	}
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    ricevi();
                }
            });
            t.start();
    }
    
    public void inviaPerValutazione(Object messaggio){
    	try{
            OOS.writeObject(messaggio);
            OOS.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}







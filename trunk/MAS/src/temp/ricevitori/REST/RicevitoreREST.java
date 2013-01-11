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
import temp.ricevitori.AbstractRicevitore;



public class RicevitoreREST extends AbstractRicevitore implements REST {
    
    @Override
    @GET
    @Path("temp.TrasmettitoriREST/TrasmettitoreREST/{messaggio}")
    public void ricevi(@PathParam("messaggio") Object messaggio){
        System.out.println("Ricevo tramite REST..");
        this.inviaPerValutazione(messaggio);
    //per ricevere il messaggio    
    }
    
    public RicevitoreREST(HashMap conf){
    super(conf);
    }
    
    @Override 
    public void ricevi(){
    // configurazione porta
         Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"/WS/REST",this);
   
    }


}







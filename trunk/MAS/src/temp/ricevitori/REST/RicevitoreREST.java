/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebService;






import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import temp.ricevitori.AbstractRicevitore;



public class RicevitoreREST extends AbstractRicevitore implements REST {
    
    @Override 
    public void ricevi(Object messaggio){
        System.out.println("Ricevo tramite REST..");
        this.inviaPerValutazione(getSString(messaggio));
    //per ricevere il messaggio    
    }
    
    public RicevitoreREST(HashMap conf){
    super(conf);
    }
    
    @Override 
    public void ricevi(){
    // configurazione porta
   
    }
  

    @GET
    @Path("temp.TrasmettitoriREST/TrasmettitoreREST/{messaggio}")
    public String getSString(@PathParam("messaggio") Object messaggio ) {
    return messaggio.toString();
    

        }

}






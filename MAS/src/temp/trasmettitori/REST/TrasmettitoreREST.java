/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.REST;

import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import temp.ricevitori.REST.REST;
import temp.proxy.TrasmettitoreProxy;

/**
 *
 * @author alessandra
 */




@Path("temp.trasmettitori.REST")
public class TrasmettitoreREST implements TrasmettitoreProxy{
    
    private String BASE_URI;
    private WebResource webResource;

    public TrasmettitoreREST(String BASE_URI) {
        this.BASE_URI = BASE_URI;
    }

    @Override
    @Path("/Invia")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void invia(Object messaggio) {    
        try{
            WebResource res = webResource; // messaggiooo sottooo
            REST rest = null;
            rest.ricevi(res.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class)) ;
    }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void dequeue(Object messaggio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    }



    

    


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.REST;

import com.sun.jersey.api.client.WebResource;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import temp.ricevitori.REST.REST;
import temp.trasmettitori.AbstractTrasmettitore;

/**
 *
 * @author Seby
 */
@Path("Trasmettitore")
public class TrasmettitoreREST extends AbstractTrasmettitore{
    
    private String BASE_URI;
    private WebResource webResource;

    public TrasmettitoreREST(String BASE_URI) {
        this.BASE_URI = BASE_URI;
    }

    @Override
    @Path("/Invio")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes("application/xml")
    public void invia(Object messaggio) {           
            System.out.println("Saluto aggiunto in post\n");
            WebResource res = webResource; // messaggiooo sottooo
            REST rest = null;
            rest.ricevi(res.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class)) ;
    }

    }



    

    


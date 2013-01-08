/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.REST;

import java.net.URL;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import temp.trasmettitori.AbstractTrasmettitore;

/**
 *
 * @author Seby
 */
@Path("temp.ricevitori.REST/REST")
public class TrasmettitoreREST extends AbstractTrasmettitore{

    public TrasmettitoreREST() {
    }

    @Override
    public void invia(Object messaggio) {           
        inviaPost("Trasmettitore Rest..");
  }
        /**
     *
     * @param message
     * @return
     */
        @POST
        @Path("/temp.ricevitori.REST/REST")
        @Produces("text/plain")
        @Consumes("application/xml")
        public ResponseBuilder inviaPost(@PathParam("REST") Object message) {
           // REST rest.invia(message);    
            return Response.status(200).entity(message);

    }

    
}

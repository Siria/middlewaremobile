/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mas.event;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author alessandra
 */
@Path("generic")
@RequestScoped
public class Evento {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Evento
     */
    public Evento() {
    }

    /**
     * Retrieves representation of an instance of mas.event.Evento
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Evento
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}

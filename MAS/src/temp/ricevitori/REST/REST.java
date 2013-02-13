package temp.ricevitori.REST;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import temp.queue.Monitor;

 
@Path("/")
public class REST {
 
    
    @MyResource
    Monitor monitor;
    
    
    
    @GET
    @Path("/ricevi/{a}")
    @Produces(MediaType.TEXT_PLAIN)
    
    
    public Response ricevi(@PathParam("a") String a) {
        System.out.println("Ricevo tramite REST...");
        try {
            monitor.accodaMessaggio(URLDecoder.decode(a, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(REST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).entity(a).build();
    }   
}

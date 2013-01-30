package temp.ricevitori.REST;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import temp.queue.Monitor;

 
@Path("/")
public class REST {
 
    
    @MyResource
    Monitor monitor;
    
    
    
    @GET
    @Path("/ricevi/{a}")
    @Produces(MediaType.TEXT_PLAIN)
    
    
    public String ricevi(@PathParam("a") String a) {
        monitor.accodaRichiesta(a);
        return null;
    }   
}

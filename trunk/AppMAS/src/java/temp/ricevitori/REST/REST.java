package temp.ricevitori.REST;


import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import temp.queue.Monitor;

@Resource 
@Path("/")
public class REST {
 
    
    @MyResource
    Monitor monitor;
    
    @GET
    @Path("/ricevi/{a}")
    @Produces(MediaType.TEXT_PLAIN)
    public String ricevi(@PathParam("a") String a) {
        
        // qui dovrei trovare un modo per richiamarmi il monitor e accodare ciò che mi arriva...
        // ma questa classe non so quando e dove viene inizializzata perchè viene richiamata dalle librerie jersey
        //System.out.println("Sono la classe REST: il messaggio è arrivato... e mo che faccio?" + a + monitor);
        monitor.accodaRichiesta(a);
        return "ok...";
    }
    
    

   
}

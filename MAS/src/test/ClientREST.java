/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * GET articles/3 recupera una rappresentazione dell'articolo desiderato POST
 * articles/crea un nuovo articolo, la cui rappresentazione e nel body della
 * richiesta PUT articles/54 modifica l'articolo esistente DELETE articles/
 * rimuove l'intera collezione di articoli
 * 
*
 */
/**
 *
 * @author alessandra
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.Path;
import temp.ricevitori.REST.REST;

@Path("/serviceREST")
public class ClientREST {

    private WebResource webResource;
    private Client client;
    private String BASE_URI = "http://localhost:8080/WS/REST";

    public void NewJerseyClient() {
        com.sun.jersey.api.client.config.ClientConfig config;
        config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("");
    }

    public String invia() throws UniformInterfaceException {
        WebResource res = webResource;
        return res.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public static void main(String args[]) {
        ClientREST client = new ClientREST();
        System.out.print("Inizio client Rest...");
        System.out.print(client.invia());
    }
}
/*   private static void invia(Object messaggio) {
            
 @Path("/test/Client")
 class MessageRestService {
 
 @GET
 @Path("/{messaggio}")
 public Response printMessage(@PathParam("messaggio") Object messaggio) {
 String result = "Risultato restful: " + messaggio;
 return Response.status(200).entity(result).build();
 }
        
 @GET
 @Path("temp.ricevitori.REST/{messaggio}")
 public String getString(@PathParam("messaggio") Object messaggio ) {
 return messaggio.toString();
 }
        

 }
        
 }
 }
 */

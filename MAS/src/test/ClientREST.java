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


public class ClientREST {

      private WebResource webResource;
      private Client client;
      private String BASE_URI = "http://localhost:17781/WS/REST";
      
     
      
/* deve avere un costruttore che accetti in ingresso un URI o un URL
 * scheme://host:port/path?queryString#fragment
 * Scheme: http/https
 * Host: nome logico o indirizzo IP
 * Port: porta/opzionale
 * Path: stringa(con /) per identificare la risorsa in uno schema gerarchico
 * Query string: Lista di parametri (coppie nome=valore delimitate da &).
 * */
      
      
      public void NewJerseyClient() {
          
           com.sun.jersey.api.client.config.ClientConfig config;
           config = new com.sun.jersey.api.client.config.DefaultClientConfig();
           client = Client.create(config);
           webResource = client.resource(BASE_URI).path("temp.trasmettitori.REST");
    }
     
       public void invia() throws UniformInterfaceException{
       
          WebResource res = webResource;
          String accept = res.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
          System.out.println(accept);

    }

      public static void main(String args[]) {

          ClientREST restClient = new ClientREST();          
          System.out.println("Inizio trasmissione...");
          restClient.invia();
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
 public void invia(@PathParam("messaggio") Object messaggio ) {
 return messaggio.toString();
 }
        

 }
        
 }
 }
 */

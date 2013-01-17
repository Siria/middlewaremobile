/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.util.logging.Logger;

/**
 * Jersey REST client generated for REST resource:Evento [generic]<br>
 *  USAGE:
 * <pre>
 *        ClientJerseyRest client = new ClientJerseyRest();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author alessandra
 */
public class ClientJerseyRest {
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:17781/WebAppEvent/webresources";

    public ClientJerseyRest() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("generic");
    }

    public String getText() throws UniformInterfaceException {
        WebResource resource = webResource;
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void putText(Object requestEntity) throws UniformInterfaceException {
        webResource.type(javax.ws.rs.core.MediaType.TEXT_PLAIN).put(requestEntity);
    }

    public void close() {
        client.destroy();
    }
    private static final Logger LOG = Logger.getLogger(ClientJerseyRest.class.getName());
    
    public void invia() throws UniformInterfaceException{
       
          WebResource res = webResource;
          String accept = res.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
          System.out.println(accept);

    }

     public static void main(String args[]) {

          ClientJerseyRest restClient = new ClientJerseyRest();          
          System.out.println("Inizio trasmissione...");
          restClient.invia();
    }
    
    
}

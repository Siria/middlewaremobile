/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.REST;

import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.HashMap;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
 
public class TrasmettitoreREST implements TrasmettitoreProxy {
    
    Monitor monitor;
    HashMap conf;
 
    
    
    

    @Override
    public void invia(Object messaggio) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource((String)conf.get("restUscita")).path("ricevi").path(messaggio.toString());
        service.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString();
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        this.conf = conf;
    }
}
    
    

    

    


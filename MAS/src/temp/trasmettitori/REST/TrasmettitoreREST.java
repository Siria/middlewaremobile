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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
 
public class TrasmettitoreREST implements TrasmettitoreProxy {
    
    Monitor monitor;
    HashMap conf;
 
    
    
    

    @Override
    public void invia(Object messaggio) {
        try {
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource((String)conf.get("restUscita")).path("ricevi").path(URLEncoder.encode(messaggio.toString(),"UTF-8"));
            service.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TrasmettitoreREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        this.conf = conf;
    }
}
    
    

    

    


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ricezione;

import ricezione.MyResource;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.PerRequestTypeInjectableProvider;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.util.HashMap;
import javax.ws.rs.ext.Provider;
import blocco.proxy.RicevitoreProxy;
import blocco.queue.Monitor;
// nel ricevitore devo configurare la porta ascolto
@Provider
public class RicevitoreREST extends PerRequestTypeInjectableProvider<MyResource, Monitor> implements RicevitoreProxy{


    
    static Monitor monitor;
    public HashMap conf = null;
    
    @Override
    public void enqueue(Object messaggio){
    	monitor.accodaMessaggio(messaggio);
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
                
        this.monitor = monitor;
        this.conf = conf;
        
        Thread t = new Thread(new Runnable() {
            
            @Override
            public void run() {
                ricevi();
            }});
            t.start();
    }

    @Override
    public void ricevi() {
        try {
            
                HttpServer server = HttpServerFactory.create((String)conf.get("restIngresso"));
                    server.start();
                    //System.out.println("Press Enter to stop the server. ");
                    //System.in.read();
                    //server.stop(0);
                } catch (IllegalArgumentException | IOException e) {
                    e.printStackTrace();
                }
    }
    
    public RicevitoreREST() {
        super(Monitor.class);   
        
    }

    @Override
    public Injectable<Monitor> getInjectable(ComponentContext ic, MyResource a) {
        return new Injectable<Monitor>() {
            @Override
            public Monitor getValue() {
                return monitor;
            }
        };
    }
    
}




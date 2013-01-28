/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.sun.jersey.spi.inject.PerRequestTypeInjectableProvider;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;
import javax.ws.rs.ext.Provider;
// nel ricevitore devo configurare la porta ascolto
@Provider
public class RicevitoreREST extends PerRequestTypeInjectableProvider<MyResource, Monitor> implements RicevitoreProxy{


    
    Monitor monitor;
    public HashMap conf = null;
    
    @Override
    public void enqueue(Object messaggio){
    	monitor.accodaRichiesta(messaggio);
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
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    
    public RicevitoreREST() {
        
        super(Monitor.class);
        System.out.println("istanzio");
    }

    @Override
    public Injectable<Monitor> getInjectable(ComponentContext ic, MyResource a) {
        System.out.println("associo");
        return new injMonitor(monitor);
    }
    
}




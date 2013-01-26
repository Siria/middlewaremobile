/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.util.HashMap;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

// nel ricevitore devo configurare la porta ascolto

public class RicevitoreREST implements RicevitoreProxy {
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
}







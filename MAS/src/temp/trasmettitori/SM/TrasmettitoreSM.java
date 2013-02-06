/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.SM;

import java.util.HashMap;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
import test.ClientSM;

/**
 *
 * @author alessandra
 */
public class TrasmettitoreSM extends Thread implements TrasmettitoreProxy{
    private Monitor monitor;
    private HashMap conf;


    @Override
    public void invia(Object messaggio) {
        while (true) {
            
            synchronized (ClientSM.synchObject) {
                System.out.println("Trasmettitore");
                ClientSM.coda.add(messaggio);
		System.out.println("Sveglio il ricevitore");
                ClientSM.synchObject.notifyAll();
                
                try {
                    System.out.println("Il trasmettitore aspetta");
                    ClientSM.synchObject.wait();
                } catch (InterruptedException e) {
                }
            }
        } 
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        this.conf = conf;
        run();
    }
}

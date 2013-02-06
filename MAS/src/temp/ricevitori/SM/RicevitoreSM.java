/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.SM;

import java.util.HashMap;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;
import test.ClientSM;


/**
 *
 * @author alessandra
 */
public class RicevitoreSM extends Thread implements RicevitoreProxy{
    private HashMap conf;
    private Monitor monitor;

    @Override
    public void ricevi() {
        while (true) {
            synchronized (ClientSM.synchObject) {
                try {
                    System.out.println("Ricevitore aspetta");
                    ClientSM.synchObject.wait();
                } catch (InterruptedException e) {
                }

                if (ClientSM.coda.isEmpty()) {
                    ClientSM.synchObject.notifyAll();
                    break;
                }
                System.out.println("Altrimenti riceve gli eventi in coda");
                Object last = ClientSM.coda.getLast();
                //Object removeLast = ClientSM.coda.removeLast();
                enqueue(last);
                ClientSM.synchObject.notifyAll();
            }
        }
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.conf = conf;
        this.monitor = monitor;
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ricevi();
            }
        });
        t.start();
    }
    
    @Override
    public void enqueue(Object messaggio) {
        monitor.accodaRichiesta(messaggio);
    }
}

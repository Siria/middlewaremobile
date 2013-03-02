/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import blocco.proxy.RicevitoreProxy;
import blocco.queue.Monitor;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public class AdapterRicevitore implements TargetRicevitore, RicevitoreProxy {

    RicevitoreProxy ricevitore = null;
    
    public AdapterRicevitore(RicevitoreProxy ricevitore){
        this.ricevitore = ricevitore;
    }
    
    @Override
    public void receive() {
        ricevitore.ricevi();
    }

    @Override
    public void config(Monitor monitor, HashMap conf) {
        ricevitore.configura(monitor, conf);
    }

    @Override
    public void enqueue(Object messaggio) {
        ricevitore.enqueue(messaggio);
    }

    @Override
    public void ricevi() {
        ricevitore.ricevi();
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        ricevitore.configura(monitor, conf);
    }
    
}

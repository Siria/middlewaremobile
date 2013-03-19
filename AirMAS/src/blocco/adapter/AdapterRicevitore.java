/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import blocco.Configurazione;
import blocco.proxy.RicevitoreProxy;
import blocco.queue.Monitor;
import java.util.HashMap;
import java.util.LinkedList;

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
    public void config(Monitor monitor, Configurazione conf) {
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
    public void configura(Monitor monitor, Configurazione conf) {
        ricevitore.configura(monitor, conf);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public class AdapterTrasmettitore implements TargetTrasmettitore, TrasmettitoreProxy{

    TrasmettitoreProxy trasmettitore = null;
    
    public AdapterTrasmettitore(TrasmettitoreProxy trasmettitore){
        this.trasmettitore = trasmettitore;
    }
    
    @Override
    public void send(Object messaggio) {
        trasmettitore.invia(messaggio);
    }

    @Override
    public void config(Monitor monitor, HashMap conf) {
        trasmettitore.configura(monitor, conf);
    }

    @Override
    public void invia(Object messaggio) {
        trasmettitore.invia(messaggio);
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        trasmettitore.configura(monitor, conf);
    }
    
}

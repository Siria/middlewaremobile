/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import blocco.filtro.VectorClock;
import java.util.HashMap;
import temp.Evento;
import temp.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 */
public class AlgoTime implements AlgoritmoProxy{

    @Override
    public Object valuta(Object messaggio) {
        Evento tmp =  (Evento) messaggio;
        // funziona questo cast?
        VectorClock vc = (VectorClock) tmp.get("Vector");
        int [] timestamp = (int[])tmp.get("timestamp");
        vc.receiveAction(timestamp);      
        // dopo aver effettuato le operazioni in ricezione 
        // incremento il vc con sendAction()                                    
        vc.sendAction();
        tmp.put("timestamp", vc.getV());
        //serve il cast ad Object?
        return tmp;
    }
    
}

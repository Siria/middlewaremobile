/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import filtro.VectorClock;

/**
 *
 * @author alessandra
 */
public class AlgoTime implements AlgoritmoProxy{

    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco Filtro Time");
        
        
        Evento e =  new Evento(messaggio.toString());

        if (e.get("Vector")!= null){
        VectorClock vc = (VectorClock) e.get("Vector");
        int [] timestamp = (int[])e.get("timestamp");
        vc.receiveAction(timestamp);      
        // dopo aver effettuato le operazioni in ricezione 
        // incremento il vc con sendAction()                                    
        vc.sendAction();
        e.put("timestamp", vc.getV());
        }
        System.out.println("Il valore del VectorClock è COERENTE");
        
        return e;
    }
    
}

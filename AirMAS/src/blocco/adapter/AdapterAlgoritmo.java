/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author Seby
 */
public class AdapterAlgoritmo implements TargetAlgoritmo,AlgoritmoProxy {
    
    AlgoritmoProxy algo = null;
    
    public AdapterAlgoritmo (AlgoritmoProxy algo){
        this.algo = algo;
    };
   
    
    @Override
    public Object valuta(Object messaggio){
        return algo.valuta(messaggio);
    }

    @Override
    public Object valute(Object messaggio) {
        return algo.valuta(messaggio);
    }
}

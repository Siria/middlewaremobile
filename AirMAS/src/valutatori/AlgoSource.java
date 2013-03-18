/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import configuratore.ConfFiltri;

/**
 *
 * @author alessandra
 */
public class AlgoSource implements AlgoritmoProxy{

    ConfFiltri e = new ConfFiltri();
    
    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco Filtro Source");
        
        Evento e =  new Evento(messaggio.toString());
                
                    if (e.get("sourceType").toString().startsWith("aereo") || e.get("sourceType").toString().equals("torre")) {
                    return e;    
                    } else {
                    return null;
                    }
                    
                    
                    
        }     
    }

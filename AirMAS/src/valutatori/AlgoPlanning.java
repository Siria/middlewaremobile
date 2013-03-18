/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 */
public class AlgoPlanning implements AlgoritmoProxy{

        // in questo algoritmo dovranno essere gestiti i conflitti ed 
        // dovranno essere prese decisioni sulla strategia
        @Override
    public Object valuta(Object messaggio) {
        
        Evento e = (Evento) messaggio;
       switch (e.get("analisi").toString()){
                
                                case "true":
                                    e.put("planning", true);
                                    return next_execution(e);
                                case "false":
                                    return planning_move(e);
                    }

        return null;     

    }

    private Evento planning_move(Evento e) {
        
        // devo spostarmi perchè le cose vanno male
        return e;
     
    }

    private Object next_execution(Evento e) {
        
        return e;
    }

    }


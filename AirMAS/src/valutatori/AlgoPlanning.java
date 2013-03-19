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
       // solo per la torre di controllo
        @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco Planning");
        
        Evento e =  (Evento) messaggio;
        System.out.println("prima dello switch");
                    switch (e.get("analisi").toString()){
                        case "start":
                            // prepara l'invio di messaggi per i 5 aerei
                             startMessage(e);
                            break;
                        case "true":
                             next_execution(e);
                            break;
                        case "false" : case "conflict":
                             planning_move(e); 
                            break;
                    } 
                    return e;               
    }

    private Object planning_move(Evento e) {
        e.put("planning", false);
        return e;
        
     
    }

    private Object next_execution(Evento e) {
        e.put("planning", true);
        return e;
        
    }

    private Object startMessage(Evento e) {
        e.put("planning", "start");
        return e;
    }

    }


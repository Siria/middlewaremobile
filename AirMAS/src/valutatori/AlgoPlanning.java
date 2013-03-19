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
        
        Evento e =  new Evento(messaggio.toString());
        System.out.println(e.toString());
                    switch (e.get("analisi").toString()){
                        case "start":
                            // prepara l'invio di messaggi per i 5 aerei
                            return e;
                        case "true":
                            e.put("planning", true);
                            return next_execution(e);
                        case "false" : case "conglict":
                            return planning_move(e);

                            
                    }

        return null;     

    }

    private Evento planning_move(Evento e) {
        e.put("exec", false);
        // devo spostarmi perchè le cose vanno come dovrebbero
        return e;
     
    }

    private Object next_execution(Evento e) {
        e.put("exec", true);
        return e;
    }

    }


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
       switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "data":
                            switch(e.get("analisi").toString()){
                                case "true":
                                    return e;
                                case "false":
                                    return planning_move(e);
                            }

                    }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "my":
                            switch(e.get("analisi").toString()){
                             case "true":
                                    return e;
                                case "false":
                                    return planning_move(e);
                            }

                         case "other":                                               

                        }
                break;
            }
        return null;     

    }

    private Object planning_move(Evento e) {
        
        // da implementare dopo aver verificato che i vicini sono
        // troppo vicini e quindi
        // devo creare un ALARM OTHER
        return null;
     
    }

    }


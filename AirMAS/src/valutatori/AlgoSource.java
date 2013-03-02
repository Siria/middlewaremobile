/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 */
public class AlgoSource implements AlgoritmoProxy{

    @Override
    public Object valuta(Object messaggio) {
        Evento e =  (Evento) messaggio;
        switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start": case "control" :
                            if (e.get("sourceType").toString().equals("tower")) {
            System.out.println("Ho ricevuto " + e.get("content").toString() + " da " + e.get("sourceType".toString()));
        }
                            return e;

                        case "data":
                            if (e.get("sourceType").toString().startsWith("aereo")) {
            System.out.println("Ho ricevuto " + e.get("content").toString() + " da " + e.get("sourceType".toString()));
        }
                            return e;
                        }
                break;                   
                    
                case "alarm":
                    switch (e.get("context").toString()){
                        case "modify" :
                            if (e.get("sourceType").toString().equals("tower")) {
            System.out.println("Ho ricevuto " + e.get("content").toString() + " da " + e.get("sourceType".toString()));
        }
                            return e;

                        case "my": case "other" :
                            if (e.get("sourceType").toString().startsWith("aereo")) {
            System.out.println("Ho ricevuto " + e.get("content").toString() + " da " + e.get("sourceType".toString()));
        }
                            return e;
                        }
                break;
                    
             
                    
        
        
    }
        return null;
    
}}

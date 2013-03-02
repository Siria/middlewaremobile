/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author 
 */

public class AlgoRicevitore implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        Evento e = new Evento(messaggio.toString());
        return ricevi_disp(e).toString();
    }
    
   public static Object ricevi_disp(Evento e) {
            switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                            System.out.println("Ho ricevuto " + e.get("content").toString()); //bisogna fare i cast espliciti tipo ((String)e.get("content")).toString()
                            return e;
                            
                        case "control":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                            
                        case "data":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){
                        case "my":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                            
                        case "other":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                            
                        case "modify":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                        }
                break;
            }
        return null;     
        }
        
            
    }
	

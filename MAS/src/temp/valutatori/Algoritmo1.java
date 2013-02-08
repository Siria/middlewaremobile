/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import temp.Evento;
import temp.proxy.AlgoritmoProxy;

/**
 *
 * @author 
 */

public class Algoritmo1 implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        System.out.println(messaggio);
        Evento e = new Evento(messaggio.toString());
        return ricevi_disp(e).toString();
    }
    
   public static Object ricevi_disp(Evento e) {
            switch (e.get("type")){
                
                case "posizione":
                    switch (e.get("context")){
                        case "start":
                            System.out.println("Ho ricevuto " + e.get("content"));
                            return e;
                            
                        case "control":
                            System.out.println("Ho ricevuto " + e.get("content"));
                            return e;
                            
                        case "data":
                            System.out.println("Ho ricevuto " + e.get("content"));
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context")){
                        case "my":
                            System.out.println("Ho ricevuto " + e.get("content"));
                            return e;
                            
                        case "other":
                            System.out.println("Ho ricevuto " + e.get("content"));
                            return e;
                            
                        case "modify":
                            System.out.println("Ho ricevuto " + e.get("content"));
                            return e;
                        }
                break;
            }
        return null;     
        }
        
            
    }
	

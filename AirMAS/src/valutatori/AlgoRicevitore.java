/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import com.sun.enterprise.config.serverbeans.Module;
import configuratore.Configuratore;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import logger.LogFile;

/**
 *
 * @author alessandra
 */

public class AlgoRicevitore implements AlgoritmoProxy{
    
    
    @Override
    public Object valuta(Object messaggio){
        System.out.println("Sono nel Blocco Ricevitore");
        Evento e = new Evento(messaggio.toString());
        return ricevi_disp(e).toString();
    }
    
    
        
    
   public static Object ricevi_disp(Evento e) {
            switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                        case "control":
                        case "data":
                            System.out.println("Ho ricevuto un messaggio di tipo: POSIZIONE");
                            System.out.println("Contenuto del messaggio: " + e.get("content").toString());
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){
                        case "my":
                        case "other":
                        case "modify":
                            System.out.println("Ho ricevuto un messaggio di tipo: ALARM");
                            System.out.println("Contenuto del messaggio: " + e.get("content").toString());
                            return e;
                        }
                break;
                    
                    
                    default:
                    System.out.println("Ho ricevuto un messaggio di tipo: SCONOSCIUTO");            
            }
        return null;     
        
        }

}


	

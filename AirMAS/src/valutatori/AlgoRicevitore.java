/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import com.sun.enterprise.config.serverbeans.Module;
import configuratore.Configuratore;
import org.glassfish.api.web.Constants;

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
                    
                    case "primary_backup" :
                        switch (e.get("context").toString()){
                            case "update" :
                                if(e.get("content").equals("ok")) {
                                    AlgoReplicatore.initialIndex = AlgoReplicatore.endIndex;
                                    AlgoReplicatore.oldTransactionStatus = true;
                                    System.out.println("Replicazione realizzata con successo");
                                }
                                else if(e.get("content").equals("fail")) {
                                    AlgoReplicatore.oldTransactionStatus = false;
                                    System.err.println("Replicazione fallita");
                                }
                                AlgoReplicatore.transaction = false;
                                AlgoReplicatore.timeout = Configuratore.getTimeout();
                                break;
                        }
            }
        return null;     
        }
        
            
    }
	

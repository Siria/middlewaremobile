/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.analisi;

import java.util.HashMap;
import temp.Evento;
import temp.proxy.AlgoritmoProxy;


/**
 *
 * @author alessandra
 */
public class AlgoGlobal implements AlgoritmoProxy{
    // devo memorizzare lo stato di tutti i position-data
    // di tutti gli aerei in una struttura 
    // creo un hashMap<ip_aereo , oggetto_aereo>
    // in cui memorizzo tutti e 5 gli aerei
    
    @Override
    public Object valuta(Object messaggio) {
        
        Evento e = (Evento) messaggio;
       switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "data":
                            addAereo(e);
                            return e;
                    }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "my":
                                                       
                            return e;
                         case "other":
                                                      
                            return e;
                        }
                break;
            }
        return null;     
        //
        //String ip = (String) hmap.get("ip");
        
    }

    private void addAereo(Evento e) {
        
    }
    
}
 

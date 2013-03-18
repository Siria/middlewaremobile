
package valutatori;

import analisi.Aereo;
import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;


/**
 *
 * @author alessandra
 */
public class AlgoGlobal implements AlgoritmoProxy{
    
    HashMap<Object, Object> map = new HashMap<>();
    
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

    
    private boolean addAereo(Evento e) {
        boolean flag= true;
        Aereo aereo = (Aereo) e.get("aereo");
        int aereoID = aereo.getId();
        map.put(aereoID,aereo);

        for (int id = 0; id < map.size() || id != aereoID; id++)  {         
            if (map.containsKey(id)){
                Aereo a = (Aereo) map.get(id);
                if (a.getPosition().equals(aereo.getPosition()))
                    // errore!
                    flag = false;
           
                else
                    flag = true;
                    // tutto ok
            }
 }   
        return flag;
}
    
    public void init(){
    
    
    }
 
    
}



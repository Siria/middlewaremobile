
package valutatori;

import analisi.Aereo;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import java.util.HashMap;


/**
 *
 * @author alessandra
 */
public class AlgoGlobal implements AlgoritmoProxy{
    
    HashMap<Object, Object> map = new HashMap<>();
    
   
    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco AlgoGlobal");
        
        Evento e =  new Evento(messaggio.toString());
        switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "data":
                            return addAereo(e);
                    }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "my":                   
                            return verifyAlarm(e);
                         case "other":                                               
                            return verifyAlarm(e);
                        }
                break;
            }
        return null;     

    }

    
    private Object addAereo(Evento e) {
        
        String aereoID = (String) e.get("id_dest");
        String pos = (String) e.get("data");
        Aereo aereo = new Aereo(aereoID, pos);       
        map.put(aereoID,aereo);
        
        for (int id = 0; id < map.size() || id != Integer.getInteger(aereoID); id++)  {         
            if (map.containsKey(id)){
                Aereo a = (Aereo) map.get(id);
                if (a.getPosition().equals(aereo.getPosition())){
                    // errore!
                    e.put("analisi", false);
                }

                else{
                    // devo verificare che sia nella sua traettoria
                    // se lo è proseguo..
                    e.put("analisi", true);
                }
            }
 }   
        return e;
}

    private Object verifyAlarm(Evento e) {
        String aereoID = (String) e.get("id");
        String pos = (String) e.get("data");
        Aereo aereo = new Aereo(aereoID, pos); 
        int id_a = Integer.getInteger(aereoID);
        
        for (int id = 0; id < map.size() || id != id_a; id++)  {         
            if (map.containsKey(id)){
                Aereo a = (Aereo) map.get(id);
                if (a.getPosition().equals(aereo.getPosition())){
                    e.put("analisi", false);
                }
       
                else{
                    // devo verificare che sia nella sua traettoria
                    // se lo è proseguo..
                    e.put("analisi", true);
                }
            }
 }   
        return e;
        
        
        
    }
    
    
    
    public Evento createStartEvent(Evento e){
        // in base all'id posizione iniziale
         String aereoID = (String) e.get("id");

         if (map.containsKey("01")) 
            e.put("data", "0:0:0;10:0:0;2:0:0;");
            
            if (map.containsKey("02")) 
            e.put("data", "50:0:0;10:0:0;2:0:0;");
                        
            if (map.containsKey("03")) 
            e.put("data", "100:0:0;10:0:0;2:0:0;");
                        
            if (map.containsKey("04")) 
            e.put("data", "200:0:0;10:0:0;2:0:0;");
                        
            if (map.containsKey("05")) 
            e.put("data", "250:0:0;10:0:0;2:0:0;");
            
            
            
            
         
             
         
         
             
        
        return null;
}
    
    
    }



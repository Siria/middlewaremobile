
package valutatori;

import analisi.Aereo;
import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 */
public class AlgoLocal implements AlgoritmoProxy{
    
     
    Aereo me;
    
    String myPosition;
    String previous;
    String follower;
    

    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco AlgoLocal");
        
        Evento e = (Evento) messaggio;
       switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                            setStart(e);
                            eventStart(e);
                            return e;
                            
                        case "control":
                            String pos = e.get("content").toString();
                            if(!verify(pos))
                                e.put("analisi", false);
                            else
                                e.put("analisi", true);
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "modify":
                            modify(e.get("content").toString());
                            e.put("analisi", true);
                            return e;
                        }
                break;
            }
        return null;     
        }

    private void setStart(Evento e) {
        
        
        
        myPosition = "0:0:0;0:0:0;0:0:0;";    
        previous = "0:0:0;0:0:0;0:0:0;";
        follower = "0:0:0;0:0:0;0:0:0;";
    
    
    }

    private boolean verify(String pos) {
        if (!myPosition.equals(pos)) {
            return false;
        }else {
            return true;
        }
        
    }

    private void modify(String pos) {
        myPosition = pos;
    }
        
            
//        public boolean neighbors(Evento e){
//        Aereo aereo, aereo_prev, aereo_follow;
//        int aereoID, aereoID_prev, aereoID_follow;
//        aereo = (Aereo) e.get("aereo");
//        aereoID = aereo.getId();
//        aereoID_prev = aereo.getPrevID();
//        aereoID_follow = aereo.getFollowID();
//
//        return false;
//        }

    private void eventStart(Evento e) {
        e.put("analisi", "start");
    }
    
}

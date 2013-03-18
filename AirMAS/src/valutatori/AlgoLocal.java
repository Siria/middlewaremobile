
package valutatori;

import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 */
public class AlgoLocal implements AlgoritmoProxy{
      
    String myPosition;
    String previous;
    String follower;

    @Override
    public Object valuta(Object messaggio) {
        Evento e = (Evento) messaggio;
       switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                            setStart();
                            return e;
                            
                        case "control":
                            String pos = e.get("content").toString();
                            if(verify(pos))
                                e.put("control", false);
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "modify":
                            modify(e.get("content").toString());                           
                            return e;
                        }
                break;
            }
        return null;     
        }

    private void setStart() {
        myPosition = "0:0:0;0:0:0;0:0:0;";    
        previous = "0:0:0;0:0:0;0:0:0;";
        follower = "0:0:0;0:0:0;0:0:0;";
    
    
    }

    private boolean verify(String pos) {
        if (!myPosition.equals(pos)) {
            return true;
        }else {
            return false;
        }
        
    }

    private void modify(String pos) {
        myPosition = pos;
    }
        
            
    
}

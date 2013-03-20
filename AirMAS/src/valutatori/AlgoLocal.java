
package valutatori;

import analisi.Aereo;
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
        
        Evento e =  new Evento(messaggio.toString());
       switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                            return setStart(e);
                                                         
                        case "control":  
                           return control (e);
                                                      
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "modify":
                            return modify(e);
                        }
                break;
            }
        return e;     
        }

    private Object setStart(Evento e) {
        String startPos = e.get("content").toString();
        myPosition = startPos;
        return e;
    }


    private Object modify(Evento e) {
        String pos = e.get("content").toString();
        myPosition = pos;
        e.put("analisi", "true");
        return e;
    }


    private Object control(Evento e) {
        String pos = e.get("content").toString();
        if (!verify(pos)) {
            e.put("analisi", "false");
        } else {
            e.put("analisi", "true");
            e.put("myPosition", myPosition);
        }
        return e;
    }
    
    
    private boolean verify(String pos) {
        return (myPosition.equals(pos));
    }
}

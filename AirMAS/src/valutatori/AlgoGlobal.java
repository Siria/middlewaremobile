
package valutatori;

import analisi.Aereo;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * @author alessandra
 */
public class AlgoGlobal implements AlgoritmoProxy{
    
    HashMap<Object, Object> map = new HashMap<>();
    private static final int AEREI = 5;
    private static final int OFFSET = 5;
    
   
    @Override
    public Object valuta(Object messaggio) {
        init();
        System.out.println("Sono nel blocco AlgoGlobal");
        
        Evento e =  new Evento(messaggio.toString());
        switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "data":
                            tryUpdateAir(e);
                            break;
                    }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){                            
                        case "my":                   
                            verifyAlarm(e);
                            break;
                        }
                break;
            }
        return e;     

    }

    
    private Object tryUpdateAir(Evento e) {
        String aereoID = (String) e.get("id_dest");
        String pos = (String) e.get("data");

        if (verifyPos(e)) {
            if (!isEnd(e)) {

                updatePos(aereoID, pos);
                e.put("analisi", true);
            } else {
                createStartEvent();
                setStart(e);
            }
        }
        else e.put("analisi", false);
        return e;
    }

    private boolean verifyPos(Evento e) {
        String aereoID = (String) e.get("id_dest");
        String pos = (String) e.get("data");
        Aereo aereo = new Aereo(aereoID, pos);
        int id_a = Integer.getInteger(aereoID);

        for (int id = 0; id < map.size() || id != id_a; id++) {
            if (map.containsKey(id)) {
                Aereo a = (Aereo) map.get(id);
                if (!a.getPosition().equals(aereo.getPosition())) {
                    return true;
                }

            }
        }
        return false;


    }
    
        private void init() {
        if(map.isEmpty()){
            System.out.println("All'interno dell'init");
            createStartEvent();
                   
        }
    }
    
    public void createStartEvent(){

        Aereo a1 = new Aereo("01" ,"0:0:0;10:0:0;2:0:0;");
        Aereo a2 = new Aereo("02" ,"50:0:0;10:0:0;2:0:0;");
        Aereo a3 = new Aereo("03" ,"100:0:0;10:0:0;2:0:0;");
        Aereo a4 = new Aereo("04" ,"200:0:0;10:0:0;2:0:0;");
        Aereo a5 = new Aereo("05" ,"250:0:0;10:0:0;2:0:0;");

            map.put(a1.getId(), a1);
            map.put(a2.getId(), a2);
            map.put(a3.getId(), a3);
            map.put(a4.getId(), a4);    
            map.put(a5.getId(), a5);

}
            
            
    public boolean neighbors(Evento e) {
        
        String id = (String) e.get("id_dest");
        Aereo a1 = (Aereo) map.get(id);

        Collection<Object> collection = map.values();
        Iterator<Object> iterator = collection.iterator();
        //Aereo a1 = new Aereo();
        Aereo a2 = new Aereo();
        while ( iterator.hasNext() ) {
            //a1 = (Aereo) iterator.next();
            a2 = (Aereo) iterator.next();
            if ( id != a2.getId() ){

            String[] parametri = a1.getPosition().toString().toLowerCase().split(";");
            String p[] = parametri[0].split(":"); //posizione  

            String[] parametri2 = a2.getPosition().toString().toLowerCase().split(";");
            String p2[] = parametri2[0].split(":");

            for (int i = 0; i < 3; i++) {
                int offs = Integer.compare(Integer.getInteger(p[i]), Integer.getInteger(p2[i]));

                if (Math.abs(offs) <= OFFSET) {

                    return true;
                    
                }
            }
        }

        
    }
        e.put("analisi", "conflict");
        return false;
    }
    private void updatePos(String aereoID, String pos) {
        Aereo aereo = (Aereo) map.get(aereoID);
        aereo.setPosition(pos);      
        map.put(aereoID,aereo);
    }

    private boolean isEnd(Evento e) {
        boolean flag = false;
        String end = (String) e.get("max_p");
        String[] parametri = e.get("content").toString().toLowerCase().split(";");
        String p[] = parametri[0].split(":"); //posizione  

        for (int i = 0; i < 3; i++) {
            if (p[i] + 1 == end) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;

    }

    private void setStart(Evento e) {
        e.put("analisi", "start");
    }

    private Object verifyAlarm(Evento e) {
        if (verifyPos(e)) {
            e.put("analisi", true);
        }
        e.put("analisi", false);
        return e;
    }

   
    
    }



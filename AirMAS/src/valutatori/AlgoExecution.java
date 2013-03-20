/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import analisi.Aereo;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author alessandra
 */
public class AlgoExecution implements AlgoritmoProxy{

    @Override
    public Object valuta(Object messaggio) {
        
        
        System.out.println("Sono nel blocco Execution");
        
        
        // qui arriverà un messaggio che conterrà le info riguardanti il disegno che si deve realizzare
        // e verrà quindi assegnata una traiettoria ad ogni aereo
        
        Evento e =  new Evento(messaggio.toString());
        
        /*
        if(e.get("sourceType").toString().equalsIgnoreCase("torre"))
         {
             if(e.get("planning").toString().equals(true)){
                 return createDataEvent(e);

             }else{ 
                 return createMyAlarmEvent(e); 
                 
             } }
         else*/
        
        
         if (e.get("sourceType").toString().equals("aereo")){
            
             if (e.get("planning").toString().equals("true")) {
                 return createControlEvent(e);
             }
             if (e.get("planning").toString().equals("false")){ 
             // devo spostarmi di 5 metri
                return createAlarmModifyEvent(e); 

             } 
                return createStartEvent(e);
        }       
        return e;
}
            /* nel messaggio ho 
         * id_source
         * id_dest
         * type
         * content
         * context
         * sourceType
         * max_p
         * */
    
    public Evento createStartEvent(Evento e){

        String type = "posizione";
        String context = "start";
        String sourceType = "torre";
        String content = getContentFromID(e.get("id_dest"));
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("id_dest", e.get("id_source"));
        newEvento.put("id_source", e.get("id_dest"));
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
    
    }
    

    
    public Evento createDataEvent(Evento e){
        // procedi con la creazione di un messaggio
                 // per la torre
        
        String type = "posizione";
        String context = "data";
        String sourceType = "aereo";
        String content = (String) e.get("myPosition");
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("id_dest", e.get("id_source"));
        newEvento.put("id_source", e.get("id_dest"));
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
}
    
    public Evento createControlEvent(Evento e){

        
        String type = "posizione";
        String context = "control";
        String sourceType = "torre";
        String content = getNextPosition(e.get("content"));
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("id_dest", e.get("id_source"));
        newEvento.put("id_source", e.get("id_dest"));
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;

}
    
    public Evento createAlarmModifyEvent(Evento e){
        
        String type = "alarm";
        String context = "modify";
        String sourceType = "torre";
        String content = getSafePosition(e.get("content").toString());
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("id_dest", e.get("id_source"));
        newEvento.put("id_source", e.get("id_dest"));
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
}
    
    public Evento createMyAlarmEvent(Evento e){
        
        String type = "alarm";
        String context = "my";
        String sourceType = "aereo";
        String content = (String) e.get("myPosition");
        String max_p = "300";
                
        Evento newEvento = new Evento();
        newEvento.put("id_dest", e.get("id_source"));
        newEvento.put("id_source", e.get("id_dest"));
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
}
    

    private String getContentFromID(Object idObject) {
        // devo dare la posizione iniziale in base all'id di destinazione del messaggio
        String id = (String) idObject;
        String pos = null;
        switch(id){
                
            case "1":
            pos = "0:0:0;10:0:0;2:0:0;" ; 
                break;
                
            case "2":
            pos = "50:0:0;10:0:0;2:0:0;";  
                break;
           
            case"3":
            pos = "100:0:0;10:0:0;2:0:0;";  
                break;
                  
            case "4":
            pos = "200:0:0;10:0:0;2:0:0;";     
                break;
                       
            case "5":
            pos = "250:0:0;10:0:0;2:0:0;";
                break;
        }
        return pos;

    }

    private String getNextPosition(Object position) {
        // Y+1

        String pos = (String) position;

            String[] parametri = pos.toString().toLowerCase().split(";");
            String p[] = parametri[0].split(":"); //posizione  

                int y = Integer.getInteger(p[1]);
                y++;
                return String.valueOf(y);
    }

    private String getSafePosition(String position) {
        // Z - 5
         String pos = (String) position;
         StringBuilder build = new StringBuilder();
            String[] parametri = pos.toString().toLowerCase().split(";");
            String p[] = parametri[0].split(":"); //posizione  

                int z = Integer.parseInt(p[2]);
                z = z-5;
                
                p[2] = z+"";
                
                String ps = p[0]+":"+p[1]+":"+p[2];
                
                parametri[0] = ps;
            
            for (String s : parametri){
                build.append(s).append(";");
            }
                return build.toString();
    }
    
}

    

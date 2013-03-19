/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;

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
         if(e.get("sourceType").toString().equalsIgnoreCase("torre"))
         {
             if(e.get("planning").toString().equals(true)){
                 return createDataEvent(e);

             }else{ 
                 return createMyAlarmEvent(e); 
                 
             } }
         else if (e.get("sourceType").toString().equals("aereo")){
            
             if (e.get("planning").toString().equals(true)) {
                 return createControlEvent(e);
                 
             }else if (e.get("planning").toString().equals(false)){ 
             // devo spostarmi di 5 metri
             return createAlarmModifyEvent(e); 

             }else
                 return createStartEvent(e);
         
   
    
}       return e;
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

        swap (e.get("id_source"), e.get("id_dest"));
        String type = "posizione";
        String context = "start";
        String sourceType = "torre";
        String content = getContentFromID(e.get("id_dest"));
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
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
        swap (e.get("id_source"), e.get("id_dest"));
        String type = "posizione";
        String context = "data";
        String sourceType = "aereo";
        String content = (String) e.get("myPosition");
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
}
    
    public Evento createControlEvent(Evento e){

        swap (e.get("id_source"), e.get("id_dest"));
        String type = "posizione";
        String context = "control";
        String sourceType = "torre";
        String content = getNextPosition(e.get("content"));
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;

}
    
    public Evento createAlarmModifyEvent(Evento e){
        swap (e.get("id_source"), e.get("id_dest"));
        String type = "alarm";
        String context = "modify";
        String sourceType = "torre";
        String content = getSafePosition(e.get("content"));
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
}
    
    public Evento createMyAlarmEvent(Evento e){
        swap (e.get("id_source"), e.get("id_dest"));
        String type = "alarm";
        String context = "my";
        String sourceType = "aereo";
        String content = (String) e.get("myPosition");
        String max_p = "300";
                
        Evento newEvento = new Evento();
        
        newEvento.put("type", type);
        newEvento.put("context", context);
        newEvento.put("content", content);
        newEvento.put("sourceType", sourceType);
        newEvento.put("max_p", max_p);
        return newEvento;
}
    public  void swap(Object s1, Object s2) {
        Object temp = s1;
        s1 = s2;
        s2 = temp;
    }

    private String getContentFromID(Object get) {
        // devo dare la posizione iniziale in base all'id di destinazione del messaggio
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getNextPosition(Object get) {
        // Y+1
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getSafePosition(Object get) {
        // Z - 5
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}

    

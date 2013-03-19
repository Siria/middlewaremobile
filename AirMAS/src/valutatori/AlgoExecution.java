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
    
    public Evento createStartEvent(Evento e){
        return null;
    
    }
    

    
    public Evento createDataEvent(Evento e){
        // procedi con la creazione di un messaggio
                 // per la torre
        return null;
}
    
    public Evento createControlEvent(Evento e){
                         // procedi con l'esecuzione e crea il messaggio 
                 // per l'aereo per continuare senza problemi
        return null;
}
    
    public Evento createAlarmModifyEvent(Evento e){
        return null;
}
    
    public Evento createMyAlarmEvent(Evento e){
        return null;
}
    
    
}

    

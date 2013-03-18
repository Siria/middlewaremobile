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
        
        
        // qui arriverà un messaggio che conterrà le info riguardanti il disegno che si deve realizzare
        // e verrà quindi assegnata una traiettoria ad ogni aereo
        
        Evento e = (Evento) messaggio;
         if(e.get("sourceType").toString().startsWith("aereo"))
         {
             if(e.get("planning").toString().equals(true)){
                 // procedi con la creazione di un messaggio
                 // per la torre
                 
             System.out.println("exec....");
             }else{ 
                 //evidentemente sarà false 
                 
             } }
         else if (e.get("sourceType").toString().equals("torre"))
            
             if (e.get("planning").toString().equals(true)) {
                 // procedi con l'esecuzione e crea il messaggio 
                 // per l'aereo per continuare senza problemi
             }else{ 
                 // evidentemente sarà false 
             }
        return null;

    
    
}
}

    

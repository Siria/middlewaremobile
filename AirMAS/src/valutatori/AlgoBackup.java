/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import backup.*;
import blocco.proxy.AlgoritmoProxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seby
 */
public class AlgoBackup implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        // TODO qua va la funzione che scrive su file
        System.out.println("Ho ricevuto: " + messaggio );
            if (messaggio.toString().equals("prepareToCommit")){
                return "ack";
            } else {
                writeLog(messaggio.toString());
                return "ack";
            }
        
    }
    
    public void writeLog(String messaggio){
        
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import blocco.proxy.AlgoritmoProxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seby
 */
public class Primary implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        return "ack";
    }
	
}
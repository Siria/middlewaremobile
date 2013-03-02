/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import blocco.queue.Monitor;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public interface TargetTrasmettitore{ 
    public abstract void send(Object messaggio);
    public void config(Monitor monitor, HashMap conf);    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import blocco.Configurazione;
import blocco.queue.Monitor;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Seby
 */
public interface TargetTrasmettitore{ 
    public abstract void send(Object messaggio);
    public void config(Monitor monitor, Configurazione conf);    
}

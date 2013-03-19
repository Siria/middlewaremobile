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
public interface TargetRicevitore{
    
    public void receive();
    public void config(Monitor monitor, Configurazione conf);    
    public void enqueue(Object messaggio);
}

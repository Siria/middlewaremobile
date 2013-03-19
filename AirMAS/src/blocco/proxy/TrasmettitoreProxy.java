package blocco.proxy;

import blocco.Configurazione;
import blocco.queue.Monitor;
import java.util.HashMap;
import java.util.LinkedList;


public interface TrasmettitoreProxy{
    
    public abstract void invia(Object messaggio);
    public void configura(Monitor monitor, Configurazione conf);    
    
}


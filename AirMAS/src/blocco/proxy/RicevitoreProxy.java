package blocco.proxy;

import blocco.Configurazione;
import blocco.queue.Monitor;
import java.util.HashMap;
import java.util.LinkedList;

public interface RicevitoreProxy{
    
    public void ricevi();
    public void configura(Monitor monitor, Configurazione conf);    
    public void enqueue(Object messaggio);
}


package blocco.proxy;

import blocco.queue.Monitor;
import java.util.HashMap;


public interface TrasmettitoreProxy{
    
    public abstract void invia(Object messaggio);
    public void configura(Monitor monitor, HashMap conf);    
    
}


package blocco.proxy;

import java.util.HashMap;
import blocco.queue.Monitor;



public interface RicevitoreProxy{
    
    public void ricevi();
    public void configura(Monitor monitor, HashMap conf);    
    public void enqueue(Object messaggio);
}


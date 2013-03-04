package blocco.proxy;

import blocco.queue.Monitor;
import java.util.HashMap;

public interface RicevitoreProxy{
    
    public void ricevi();
    public void configura(Monitor monitor, HashMap conf);    
    public void enqueue(Object messaggio);
}


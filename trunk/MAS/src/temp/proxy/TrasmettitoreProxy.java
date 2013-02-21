package temp.proxy;


import java.util.HashMap;
import temp.queue.Monitor;


public interface TrasmettitoreProxy{
    
    public abstract void invia(Object messaggio);
    public void configura(Monitor monitor, HashMap conf);    
    
}


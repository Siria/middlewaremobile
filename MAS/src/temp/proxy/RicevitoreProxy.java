package temp.proxy;

//TargetInterface.java

import java.util.HashMap;
import temp.queue.Monitor;



public interface RicevitoreProxy{
    public void ricevi();
    public void configura(Monitor monitor, HashMap conf);    
    public void enqueue(Object messaggio);
}


package temp.proxy;

//TargetInterface.java

import java.util.HashMap;
import temp.queue.Monitor;

//Define the interface the target class implements
public interface TrasmettitoreProxy{
    
    public abstract void invia(Object messaggio);
    public void configura(Monitor monitor, HashMap conf);    
    
}


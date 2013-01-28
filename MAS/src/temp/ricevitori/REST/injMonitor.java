/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

import com.sun.jersey.spi.inject.Injectable;
import temp.queue.Monitor;

/**
 *
 * @author Seby
 */
public class injMonitor implements Injectable<Monitor>  {

    Monitor monitor;
    
    injMonitor(Monitor monitor){
        System.out.println("Assegno " + monitor + " a " + this.monitor );
        this.monitor = monitor;
    }
    
    @Override
    public Monitor getValue() {
        System.out.println("passo dal ritorno");
        return monitor;
    }
    
}

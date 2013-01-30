/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.Socket;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

/**
 *
 * @author Seby
 */
public class RicevitoreSocket implements RicevitoreProxy{

    private Monitor monitor;
    public HashMap conf = null;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
   
    
    @Override
    public void ricevi() {
        
        
        try{
        ServerSocket SS = (ServerSocket)conf.get("socketIngresso");
		
		while(true){
			final Socket SK = SS.accept();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    ObjectInputStream OIS = new ObjectInputStream(SK.getInputStream());
                                    while(true){
                                    Object tmp = OIS.readObject();
                                    System.out.println("Ricevo tramite Socket...");
                                    enqueue(tmp);
                                    }
                                } catch (EOFException exf){
                                    System.out.println("Un client si è scollegato...");   
                                } catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }                            
                        });
                        t.start();	
		}
	
	}catch(Exception e){
		e.printStackTrace();
	}
    }
    
    public void enqueue(Object messaggio){
    	monitor.accodaRichiesta(messaggio);
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        
        this.conf = conf;
        this.monitor = monitor;
        
        Thread t = new Thread(new Runnable() {
    @Override
    public void run() {
                    ricevi();
                }
            });
            t.start();
    }
    
    
    
}

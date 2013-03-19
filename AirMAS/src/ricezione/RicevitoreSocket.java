/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ricezione;

import blocco.Configurazione;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import blocco.proxy.RicevitoreProxy;
import blocco.queue.Monitor;

/**
 *
 * @author Seby
 */
public class RicevitoreSocket implements RicevitoreProxy{

    private Monitor monitor;
    public Configurazione conf = null;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
   
    
    @Override
    public void ricevi() {
        
        
        try{
        String port = (String)conf.get("socketIngresso");
        ServerSocket SS = new ServerSocket(Integer.parseInt(port));
		
		while(true){
			final Socket SK = SS.accept();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    ObjectInputStream OIS = new ObjectInputStream(SK.getInputStream());
                                    while(true){
                                    Object tmp = OIS.readObject();
                                    System.out.println("\nRicevo tramite Socket...");
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
    	monitor.accodaMessaggio(messaggio);
    }

    @Override
    public void configura(Monitor monitor, Configurazione conf) {
        
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

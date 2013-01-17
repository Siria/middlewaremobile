/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.Socket;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import temp.proxy.RicevitoreProxy;

/**
 *
 * @author Seby
 */
public class RicevitoreSocket implements RicevitoreProxy{

    ObjectOutputStream OOS = null;
    public HashMap conf = null;
    
   
    
    
    @Override
    public void ricevi() {
        
        
        try{
        ServerSocket SS = new ServerSocket((int)conf.get("portaAscoltoEsterna"));
		
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
                                    inviaPerValutazione(tmp);
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
    
    
    public RicevitoreSocket(HashMap conf){
        this.conf = conf;       	
    }

    public final void mettitiInAscolto(){
        try{
            Socket SK = new Socket("localhost",(int)conf.get("portaAscoltoInterna"));
            OOS = new ObjectOutputStream(SK.getOutputStream());	
  	}catch(Exception e){
            e.printStackTrace();
	}
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    ricevi();
                }
            });
            t.start();
    }
    
    public void inviaPerValutazione(Object messaggio){
    	try{
            OOS.writeObject(messaggio);
            OOS.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}

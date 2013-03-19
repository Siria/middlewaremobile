/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trasmissione;

import blocco.Configurazione;
import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public class TrasmettitoreSocket implements TrasmettitoreProxy{

    Socket SK = null;
    ObjectOutputStream OOS = null;
    ObjectInputStream OIS = null;
    Monitor monitor;
    
    @Override
    public void invia(Object messaggio){
		try{
		OOS.writeObject(messaggio);
		OOS.flush();
		}catch (Exception e){e.printStackTrace();}
	}

   

    @Override
    public void configura(Monitor monitor, Configurazione conf) {
         String addr = (String)conf.get("socketUscita");
         String[] param = addr.split(":");
         this.monitor = monitor;
            try{
                this.SK = new Socket(param[0],Integer.parseInt(param[1]));
        	OOS = new ObjectOutputStream(SK.getOutputStream());
            }catch(Exception e){e.printStackTrace();}
    }
    
    }
    
    


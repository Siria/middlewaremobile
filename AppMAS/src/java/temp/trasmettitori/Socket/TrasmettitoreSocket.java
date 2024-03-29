/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;

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
    public void configura(Monitor monitor, HashMap conf) {
         this.SK = (Socket)conf.get("socketUscita");
        this.monitor = monitor;
		try{
		OOS = new ObjectOutputStream(SK.getOutputStream());
		}catch(Exception e){e.printStackTrace();}
    }
    
    }
    
    


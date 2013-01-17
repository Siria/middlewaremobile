/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import temp.proxy.TrasmettitoreProxy;

/**
 *
 * @author Seby
 */
public class TrasmettitoreSocket implements TrasmettitoreProxy{

    Socket SK = null;
    ObjectOutputStream OOS = null;
    ObjectInputStream OIS = null;

    public TrasmettitoreSocket() {
        }

    public TrasmettitoreSocket(Socket SK) {
        this.SK = SK;
		try{
		OOS = new ObjectOutputStream(SK.getOutputStream());
		}catch(Exception e){e.printStackTrace();}
	}
    
    @Override
    public void invia(Object messaggio){
		try{
		OOS.writeObject(messaggio);
		OOS.flush();
		}catch (Exception e){e.printStackTrace();}
	}
    
    }
    
    


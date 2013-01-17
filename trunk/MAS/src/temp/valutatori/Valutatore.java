/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import temp.proxy.TrasmettitoreProxy;

/**
 *
 * @author Seby
 */
public class Valutatore implements Runnable{
        Socket SK = null;
	ObjectOutputStream OOS = null;
	ObjectInputStream OIS = null;
	TrasmettitoreProxy TX; 
        InterfaceAlgoritmo algoritmo;
    
        public Valutatore(){}
	public Valutatore(Socket SK, TrasmettitoreProxy TX, InterfaceAlgoritmo algoritmo){
                this.TX = TX;
		this.SK = SK;
                this.algoritmo = algoritmo;
		try{
		OIS = new ObjectInputStream(SK.getInputStream());
		}catch(Exception e){e.printStackTrace();}
	}
        
    @Override
	public void run(){
		try{
		boolean continua=true;
			while (continua){
				Object tmp = OIS.readObject();
                                Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    TX.invia(risp);
                                }
                        }
			OIS.close();
			SK.close();
		}catch (Exception e){e.printStackTrace();}
	}
        
        
}

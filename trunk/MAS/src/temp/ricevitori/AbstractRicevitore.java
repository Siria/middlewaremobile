/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public abstract class AbstractRicevitore {
    ObjectOutputStream OOS = null;
    public final HashMap conf;
    
    public AbstractRicevitore() {
        conf = null;
    }
    
    public AbstractRicevitore(HashMap conf){
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
    
    /* 
     *   questo metodo dipende dal tipo di ricevitore e una volta
     *   che riceve un oggetto usa il metodo inviaPerValutazione
     *   per far eseguire la valutazione sull'oggetto ricevuto
     *   p.s. la ricezione deve avvenire su 3d separati
     */        
    public abstract void ricevi();
}


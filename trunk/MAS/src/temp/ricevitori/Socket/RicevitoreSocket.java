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
import temp.ricevitori.AbstractRicevitore;

/**
 *
 * @author Seby
 */
public class RicevitoreSocket extends AbstractRicevitore{

    public RicevitoreSocket(HashMap conf) {
        super(conf);
    }
    
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
    
}

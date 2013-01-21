/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

/**
 *
 * @author Seby
 */
import java.net.*;
import java.util.LinkedList;
import temp.proxy.RicevitoreProxy;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
import temp.valutatori.InterfaceAlgoritmo;

public class Blocco implements Runnable{
    
    private TrasmettitoreProxy trasmettitore;
    private InterfaceAlgoritmo algoritmo; 
    private Monitor monitor;

    public Blocco(Monitor monitor) {
        this.monitor = monitor;
    }
    
    public LinkedList<RicevitoreProxy> ricevitori = new LinkedList<>();

    public void setAlgoritmo(InterfaceAlgoritmo algoritmo) {
        this.algoritmo = algoritmo;
    }
    
    public void setTrasmettitore(TrasmettitoreProxy trasmettitore) {
        this.trasmettitore = trasmettitore;
    }
	
    @Override
	public void run(){
		for (RicevitoreProxy ricevitore : ricevitori){
                    ricevitore.mettitiInAscolto();
                    
                }
            
            try{    
    		boolean continua=true;
			while (continua){
                                Object tmp = monitor.prelevaRichiesta();
				Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    trasmettitore.invia(risp);
                                }
                        }
		}catch (Exception e){e.printStackTrace();}
            }
        
        
	}

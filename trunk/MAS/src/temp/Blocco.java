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
import temp.valutatori.InterfaceAlgoritmo;
import temp.valutatori.Valutatore;

public class Blocco implements Runnable{
    
    private int portaAscolto;
    private TrasmettitoreProxy trasmettitore;
    private InterfaceAlgoritmo algoritmo; 
    public LinkedList<RicevitoreProxy> ricevitori = new LinkedList<>();

    public void setAlgoritmo(InterfaceAlgoritmo algoritmo) {
        this.algoritmo = algoritmo;
    }
    
    public void setPortaAscolto(int portaAscolto) {
        this.portaAscolto = portaAscolto;
    }

    public void setTrasmettitore(TrasmettitoreProxy trasmettitore) {
        this.trasmettitore = trasmettitore;
    }
	
    @Override
	public void run(){
	
		boolean continua = true;
              
		try{
		ServerSocket SS = new ServerSocket(portaAscolto);
		
                for (RicevitoreProxy ricevitore : ricevitori){
                    ricevitore.mettitiInAscolto();
                }
                
		while(continua){
			Socket SK = SS.accept();
                        Valutatore valutatore = new Valutatore(SK,trasmettitore,algoritmo);
			Thread tAsc = new Thread(valutatore);
			tAsc.start();	
		}
	
	}catch(Exception e){
		e.printStackTrace();
	}
	
	}
	
}
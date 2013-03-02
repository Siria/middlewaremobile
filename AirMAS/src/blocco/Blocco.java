/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;

/**
 *
 * @author author
 */
import java.util.HashMap;
import java.util.LinkedList;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;


public class Blocco implements Runnable{
    
    private Monitor monitor = new Monitor();
    private LinkedList<RicevitoreProxy> ricevitori = new LinkedList<>();
    private LinkedList<TrasmettitoreProxy> trasmettitori = new LinkedList<>();

    private AlgoritmoProxy algoritmo; 
    private HashMap conf = new HashMap();


    public HashMap getConf() {
        return conf;
    }

    public void setConf(HashMap conf) {
        this.conf = conf;
    }
    
    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public LinkedList<RicevitoreProxy> getRicevitori() {
        return ricevitori;
    }

    public void setRicevitori(LinkedList<RicevitoreProxy> ricevitori) {
        this.ricevitori = ricevitori;
    }

    public AlgoritmoProxy getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(AlgoritmoProxy algoritmo) {
        this.algoritmo = algoritmo;
    }

    public LinkedList<TrasmettitoreProxy> getTrasmettitori() {
        return trasmettitori;
    }

    public void setTrasmettitori(LinkedList<TrasmettitoreProxy> trasmettitori) {
        this.trasmettitori = trasmettitori;
    }

    
    
	
    @Override
	public void run(){
		for (RicevitoreProxy ricevitore : ricevitori){
                    ricevitore.configura(monitor,conf); 

                }
                
                for (TrasmettitoreProxy trasmettitore : trasmettitori){
                    trasmettitore.configura(monitor,conf); 

                    
                }
                
            
            try{    
    		boolean continua=true;
			while (continua){
                            
                            Object tmp = monitor.prelevaMessaggio(); //questi lasciamoli object
                            Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    for (TrasmettitoreProxy trasmettitore : trasmettitori){
                                       trasmettitore.invia(risp);              
                                    }
                                }
                        }
		}catch (Exception e){}
            }    
	}



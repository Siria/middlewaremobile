/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;

/**
 *
 * @author author
 */
import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.queue.Monitor;
import java.util.HashMap;
import java.util.LinkedList;


public class Blocco implements Runnable{
    
    private Monitor monitor = new Monitor();
    private LinkedList<AdapterRicevitore> ricevitori = new LinkedList<>();
    private LinkedList<AdapterTrasmettitore> trasmettitori = new LinkedList<>();

    private AdapterAlgoritmo algoritmo; 
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

    public LinkedList<AdapterRicevitore> getRicevitori() {
        return ricevitori;
    }

    public void setRicevitori(LinkedList<AdapterRicevitore> ricevitori) {
        this.ricevitori = ricevitori;
    }

    public AdapterAlgoritmo getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(AdapterAlgoritmo algoritmo) {
        this.algoritmo = algoritmo;
    }

    public LinkedList<AdapterTrasmettitore> getTrasmettitori() {
        return trasmettitori;
    }

    public void setTrasmettitori(LinkedList<AdapterTrasmettitore> trasmettitori) {
        this.trasmettitori = trasmettitori;
    }

    
    
	
    @Override
	public void run(){
		for (AdapterRicevitore ricevitore : ricevitori){
                    ricevitore.configura(monitor,conf); 
                }
                
                for (AdapterTrasmettitore trasmettitore : trasmettitori){
                    trasmettitore.configura(monitor,conf); 
                }
                
                
            try{    
    		boolean continua=true;
			while (continua){
                            Object tmp = monitor.prelevaMessaggio(); //questi lasciamoli object
                            Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    for (AdapterTrasmettitore trasmettitore : trasmettitori){
                                       trasmettitore.invia(risp);              
                                    }
                                }
                        }
		}catch (Exception e){}
            }    
	}



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
import java.util.Map.Entry;


public class Blocco implements Runnable{
    
     public Monitor monitor = new Monitor();
     public LinkedList<AdapterRicevitore> ricevitori = new LinkedList<>();
     public LinkedList<AdapterTrasmettitore> trasmettitori = new LinkedList<>();

     public AdapterAlgoritmo algoritmo; 
     public Configurazione conf = new Configurazione();

    public Configurazione getConf() {
        return conf;
    }

    public void setConf(Configurazione conf) {
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
		}catch (Exception e){
                    e.printStackTrace();
                }
            }    
	}



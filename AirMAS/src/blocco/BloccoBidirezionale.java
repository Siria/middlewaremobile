/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;


import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.queue.Monitor;

/**
 *
 * @author Seby
 */
public class BloccoBidirezionale implements Runnable{
    
    AdapterRicevitore ricevitore;
    AdapterTrasmettitore trasmettitore;
    public Monitor monitor = new Monitor();
    public Configurazione conf = new Configurazione();
    public AdapterAlgoritmo algoritmo; 
     
    Boolean isTrasmettitoriConf = false;
    public Boolean isPrimary = false;
    
    public void configuraRicevitori(){
            ricevitore.configura(monitor,conf); 
        
    
    if (!isPrimary){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    
                	while (true){
                            Object tmp = monitor.prelevaMessaggio(); //questi lasciamoli object
                            Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    invia(risp);
                                }
                        
                    }   
		}catch (Exception e){e.printStackTrace();}
            }  
               
            });
            t.start();
        }
    }
    
    public void configuraTrasmettitori(){
        trasmettitore.configura(monitor,conf); 
        isTrasmettitoriConf = true;
    }
    
    public void invia(Object messaggio){
       if (!isTrasmettitoriConf){
           configuraTrasmettitori();
       } 
        
            trasmettitore.invia(messaggio);              
       
    }   
    
    public int inviaConAck(Object messaggio, int Timeout){
       if (isPrimary){
        
       if (!isTrasmettitoriConf){
           configuraTrasmettitori();
       } 
        
            trasmettitore.invia(messaggio);              
       Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Object tmp = monitor.prelevaMessaggio();
               }
            });
            t.start();
        
        try {
            t.join(Timeout);
            if (t.isAlive()){
                t.interrupt();
                t=null;
                return 0;
            }
            
            
        } catch (Exception ex) {
            
            System.out.println("Timeout!!!");
            
        }
        //questi lasciamoli object
        return 1;
    }
        return 0;
    }
   
    
        @Override
	public void run(){
		    ricevitore.configura(monitor,conf); 
                    trasmettitore.configura(monitor,conf); 
                
            try{    
    		boolean continua=true;
			while (continua){
                            Object tmp = monitor.prelevaMessaggio(); //questi lasciamoli object
                            Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                       trasmettitore.invia(risp);              
  
                                }
                        }
		}catch (Exception e){
                    e.printStackTrace();
                }
            }
        
       public AdapterRicevitore getRicevitore() {
        return ricevitore;
    }

    public void setRicevitore(AdapterRicevitore ricevitore) {
        this.ricevitore = ricevitore;
    }

    public AdapterTrasmettitore getTrasmettitore() {
        return trasmettitore;
    }

    public void setTrasmettitore(AdapterTrasmettitore trasmettitore) {
        this.trasmettitore = trasmettitore;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Configurazione getConf() {
        return conf;
    }

    public void setConf(Configurazione conf) {
        this.conf = conf;
    }

    public AdapterAlgoritmo getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(AdapterAlgoritmo algoritmo) {
        this.algoritmo = algoritmo;
    }
        
           
        
}

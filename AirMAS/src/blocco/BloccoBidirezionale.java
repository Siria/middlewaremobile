/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;


import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;

/**
 *
 * @author Seby
 */
public class BloccoBidirezionale extends Blocco{
    
    Boolean isTrasmettitoriConf = false;
    public Boolean isPrimary = false;
    
    public void configuraRicevitori(){
        for (AdapterRicevitore ricevitore : ricevitori){
            ricevitore.configura(monitor,conf); 
        }
    
    
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
        for (AdapterTrasmettitore trasmettitore : trasmettitori){
            trasmettitore.configura(monitor,conf); 
        }
        isTrasmettitoriConf = true;
    }
    
    public void invia(Object messaggio){
       if (!isTrasmettitoriConf){
           configuraTrasmettitori();
       } 
        
       for (AdapterTrasmettitore trasmettitore : trasmettitori){
            trasmettitore.invia(messaggio);              
       }
    }   
    
    public boolean inviaConAck(Object messaggio, int Timeout){
       if (isPrimary){
        
       if (!isTrasmettitoriConf){
           configuraTrasmettitori();
       } 
        
       for (AdapterTrasmettitore trasmettitore : trasmettitori){
            trasmettitore.invia(messaggio);              
       }
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
                return false;
            }
            
            
        } catch (Exception ex) {
            
            System.out.println("Timeout!!!");
            
        }
        //questi lasciamoli object
        return true;
    }
        return false;
    }
   
}

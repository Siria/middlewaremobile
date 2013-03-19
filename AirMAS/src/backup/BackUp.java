/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import blocco.Blocco;
import blocco.BloccoBidirezionale;
import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.ProxyTarget;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Seby
 */
public class BackUp implements Runnable{
    
     BloccoBidirezionale asc1;   
     
     LinkedList<BloccoBidirezionale> blocchiB = new LinkedList<>();
     BloccoBidirezionale bloccoC;
     
     
    public LinkedList<BloccoBidirezionale> getBlocchiB() {
        return blocchiB;
    }

    public void setBlocchiB(LinkedList<BloccoBidirezionale> blocchiB) {
        this.blocchiB = blocchiB;
    }
     
     
     @Override
	public void run(){
         try {
            for (BloccoBidirezionale asc : blocchiB){
                asc.configuraRicevitori(); 
            }
            
            bloccoC = blocchiB.poll();
            
            while(true){
                Object messaggio = bloccoC.monitor.prelevaMessaggio();
                int backupAttivi = 0;
                System.out.println("--> Sono il primary ho un messaggio");
                for (BloccoBidirezionale asc : blocchiB){
                    asc.isPrimary = true;
                }
                
                System.out.println("--> Sono il primary: invio il prepareToCommit");
                
                for (BloccoBidirezionale asc : blocchiB){
                    backupAttivi = backupAttivi + asc.inviaConAck("prepareToCommit", 2000);
                }
                
                if (blocchiB.size() == backupAttivi){
                    System.out.println("--> Sono il primary: ACK-OK, invio il messaggio");
                    int confermati = 0;
                    for (BloccoBidirezionale asc : blocchiB){
                        confermati = confermati + asc.inviaConAck(messaggio, 2000);
                    }
                    
                    if (confermati == backupAttivi){
                        System.out.println("--> Sono il primary: ACK-OK, confermo il backup");
                        bloccoC.invia("ack");
                    }
                    
                }
            }
            
            
         } catch(Exception e){
             e.printStackTrace();
         }  
     }
       
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import blocco.Blocco;
import blocco.BloccoBidirezionale;
import blocco.Evento;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import valutatori.AlgoBackup;

/**
 *
 * @author Seby
 */
public class BackUp extends Blocco{
    
     AdapterTrasmettitore trasmettitore;
     AdapterRicevitore ricevitore;
     
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
             
             
             
             if (this.trasmettitore==null && this.ricevitore== null && this.blocchiB.size()>1){
                 // Blocco BackUp Primary/Secondary
             try {
                 
             bloccoC = blocchiB.poll();
             
             for (BloccoBidirezionale asc : blocchiB){
                 Thread th = new Thread(asc);
                 th.start(); 
             }
             
             
             bloccoC.configuraRicevitori();
             
             
            while(true){
                Object messaggio = bloccoC.monitor.prelevaMessaggio();
                int backupAttivi = 0;
                System.out.println("--> Sono il primary: richiesta backup\n--> Sono il primary: invio il prepareToCommit");
                
                backupAttivi = inviaATuttiConAck("prepareToCommit",20000);
                
                if (blocchiB.size() == backupAttivi){
                    System.out.println("--> Sono il primary: ACK-OK, invio il messaggio");
                    int confermati = 0;
                    int i = 0;
                    
                    
                    for (BloccoBidirezionale asc : blocchiB){
                        i++;
                        Evento e = new Evento(messaggio.toString());
                        e.put("id_backup",i+"");
                        confermati = confermati + asc.inviaConAck(e.toString(), 20000);
                    }
                    
                    if (confermati == backupAttivi){
                        System.out.println("--> Sono il primary: ACK-OK, confermo il backup");
                        AlgoBackup al = new AlgoBackup();
                        Evento e = new Evento(messaggio.toString());
                        e.put("id_backup",0+"");
                        al.writeLog(messaggio.toString(), 0+"");
                        bloccoC.invia("ack");
                    }
                    
                }
            }
            
            
         } catch(Exception e){
             e.printStackTrace();
         }  
             
        }else{
                 // comportamento da client
        ricevitore.configura(monitor,conf); 
        trasmettitore.configura(monitor,conf); 
         
        for (BloccoBidirezionale blocco : blocchiB){
            Thread th = new Thread(blocco);
            th.start();
        }
        
         try{    
    		boolean continua=true;
			while (continua){
                            Object tmp = monitor.prelevaMessaggio(); //questi lasciamoli object
                            final Object risp = tmp;
                                if (risp != null){
                                       trasmettitore.invia(risp);              
                                    
                                    Thread t = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                           
                                                  for (BloccoBidirezionale blocco : blocchiB){
                                                    int res = blocco.inviaConAck(risp, 20000);
                                                    if (res == 0){
                                                        System.out.println("--- Impossibile completare il backup ---");
                                                    } else {
                                                        System.out.println("--- Backup effettuato con successo ---");
                                                    }
                                                 }
                                            }
                                        });
                                    t.start();
                                    
                                    
                                }
                        }
		}catch (Exception e){
                    e.printStackTrace();
                }        
                 
             
        }
     }

    public int inviaATuttiConAck(final String messaggio, final int timeout){
        int i = 0;
        LinkedList<Thread> threads = new LinkedList();
        final LinkedList<Integer> value = new LinkedList();
        
        for (final BloccoBidirezionale asc : blocchiB){ 
            Thread t = new Thread(new Runnable() {
            @Override
                public void run() {
                    value.add(asc.inviaConAck(messaggio, timeout));
                }
                });

            threads.add(t);
            t.start();
        }
        
        for (Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (Integer intero : value){
            i = i + intero;
        }
        
        return i;
    } 
     
    public AdapterTrasmettitore getTrasmettitore() {
        return trasmettitore;
    }

    public void setTrasmettitore(AdapterTrasmettitore trasmettitore) {
        this.trasmettitore = trasmettitore;
    }

    public AdapterRicevitore getRicevitore() {
        return ricevitore;
    }

    public void setRicevitore(AdapterRicevitore ricevitore) {
        this.ricevitore = ricevitore;
    }
     
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import blocco.Blocco;
import blocco.Evento;
import blocco.adapter.AdapterTrasmettitore;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.ProxyTarget;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;

/**
 *
 * @author alessandra
 */
public class FiltroTime extends Blocco{
    
    
    // tanti ricevitori (max3) e tanti trasmettitori (max3) a seconda che siano socket, shared e/o file
    private int PID = ricevitori.size()+trasmettitori.size()+1;
    // id del mio processo
    private int id = 0;
    public int [] timestamp;
    private VectorClock vc = new VectorClock(PID, id);


  
   
    @Override
	public void run(){
		for (RicevitoreProxy ricevitore : ricevitori){
                    ricevitore.configura(monitor,conf);
                    vc.myId = id++;
                    vc.doAct();
                }
                
                for (TrasmettitoreProxy trasmettitore : trasmettitori){
                    trasmettitore.configura(monitor,conf);
                    vc.myId = id++;
                    vc.doAct();

                }
                
            
            try{    
    		boolean continua=true;
			while (continua){
                            Object messaggio = monitor.prelevaMessaggio();
                            Evento e =  new Evento(messaggio.toString());
                            e.put("Vector", vc);
                            Object risp = algoritmo.valuta(e.toString());
                                if (risp != null){
                                    for (AdapterTrasmettitore trasmettitore : trasmettitori){
                                       trasmettitore.invia(risp);              
                                    }
                                }
                        }
		}catch (Exception e){
                    e.printStackTrace();}
            }    
	}



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.filtro;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import temp.proxy.AlgoritmoProxy;
import temp.proxy.ProxyTarget;
import temp.proxy.RicevitoreProxy;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;

/**
 *
 * @author alessandra
 */
public class FiltroTime implements Runnable{
    
    private Monitor monitor = new Monitor();
    private LinkedList<RicevitoreProxy> ricevitori = new LinkedList<>();
    private LinkedList<TrasmettitoreProxy> trasmettitori = new LinkedList<>();
    private AlgoritmoProxy algoritmo; 
    private HashMap conf = new HashMap();
    
    // tanti ricevitori (max3) e tanti trasmettitori (max3) a seconda che siano socket, shared e/o file
    private int PID = ricevitori.size()+trasmettitori.size()+1;
    // id del mio processo
    private int id = 0;
    public int [] timestamp;
    private VectorClock vc = new VectorClock(PID, id);



    public HashMap getConf() {
        return conf;
    }

    public void setConf(HashMap conf) {
        this.conf = conf;
    }

    

    public void configura(String configuration){
        String[] parametri = configuration.toLowerCase().split(";");
        for (String parametro : parametri){
            String nome = parametro.split(":")[0];          
            
            System.out.println("Nome: " + nome);
            
            if (nome.equals("trasmettitore")){
                parametro = parametro.substring(nome.length()+1);
                String[] valori = parametro.split(",");
                for (String valore : valori){
                    try {  
                        try {
                            File root = new File(".");
                            URLClassLoader classLoader;
                           
                                classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
                                Class<?> cls = Class.forName(valore, true, classLoader);
                                trasmettitori.add((TrasmettitoreProxy)ProxyTarget.createProxy(cls));
                                
                         } catch (MalformedURLException ex) {
                            Logger.getLogger(blocco.filtro.FiltroTime.class.getName()).log(Level.SEVERE, null, ex);
                        
                         }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(blocco.filtro.FiltroTime.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            }
            
            if (nome.equals("ricevitore")){
                parametro = parametro.substring(nome.length()+1);
                String[] valori = parametro.split(",");
                for (String valore : valori){
                    switch (valore){
                        case("socket"):
                            break;
                        case("shared"):
                            break;
                        case("file"):
                            break;
                    }
                }
            }         
            
        }
    
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
                            
                            //HashMap tmp = (HashMap)
                           Object risp = monitor.prelevaMessaggio();
                            System.out.println("Mi trovo nel blocco filtro Time");
                            HashMap tmp = (HashMap) risp;
                            tmp.put("Vector", vc);
                            risp = algoritmo.valuta(tmp);
                            for (TrasmettitoreProxy trasmettitore : trasmettitori){
                                       trasmettitore.invia(risp);              
                                    
                                }
                        }
		}catch (Exception e){
                    e.printStackTrace();}
            }    
	}



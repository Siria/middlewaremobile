/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuratore;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
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
public class Configuratore implements Runnable{
    
    private Monitor monitor = new Monitor();
    private RicevitoreProxy ricevitore;
    private TrasmettitoreProxy trasmettitore;
    private AlgoritmoProxy algoritmo; 
    private HashMap conf = new HashMap();
    
   
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
                                trasmettitore = (TrasmettitoreProxy)ProxyTarget.createProxy(cls);
                                
                         } catch (MalformedURLException ex) {
                            Logger.getLogger(configuratore.Configuratore.class.getName()).log(Level.SEVERE, null, ex);
                        
                         }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(configuratore.Configuratore.class.getName()).log(Level.SEVERE, null, ex);
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

  
    public AlgoritmoProxy getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(AlgoritmoProxy algoritmo) {
        this.algoritmo = algoritmo;
    }
   
    @Override
	public void run(){
		
                    ricevitore.configura(monitor,conf);                   
                    trasmettitore.configura(monitor,conf);
                       
            try{    
    		boolean continua=true;
			while (continua){
                           // prende un messaggio da stream reader
                            // per adesso il file xml è scritto tramite WriteXMLsoglia
                           Object risp = monitor.prelevaMessaggio();
                            System.out.println("Mi trovo nel blocco configuratore");
                            // algoritmo invierà comandi
                            // risp è di tipo hashMap<variabile, min e max>
                            risp = algoritmo.valuta(risp);   
                                if (risp != null){
                                   trasmettitore.invia(risp);              
                                    
                                }
                        }
		}catch (Exception e){
                    e.printStackTrace();}
            }    
	}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

/**
 *
 * @author Seby
 */
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


public class Blocco implements Runnable{
    
    private Monitor monitor = new Monitor();
    private LinkedList<RicevitoreProxy> ricevitori = new LinkedList<>();
    private LinkedList<TrasmettitoreProxy> trasmettitori = new LinkedList<>();

    private AlgoritmoProxy algoritmo; 
    private HashMap conf = new HashMap();

    private int PID;
    private int id;
    public int [] timestamp;
    private VectorClock vc;



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
                            // per caricare in maniera dinamica della classe
                            // in base al nome pssato nella variabile valore
                            // valore= percorso per il file .class della classe java
                            // ma nebeans mette i .class in una cartella e i .java in un'altra
                            // che percorso devo mettere
                            URLClassLoader classLoader;
                           
                                classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
                                Class<?> cls = Class.forName(valore, true, classLoader);
                                trasmettitori.add((TrasmettitoreProxy)ProxyTarget.createProxy(cls));
                                
                         } catch (MalformedURLException ex) {
                            Logger.getLogger(Blocco.class.getName()).log(Level.SEVERE, null, ex);
                        
                         }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Blocco.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            }
            
            if (nome.equals("ricevitore")){
                parametro = parametro.substring(nome.length()+1);
                String[] valori = parametro.split(",");
                for (String valore : valori){
                    switch (valore){
                        case("rest"):
                            break;
                        case("jms"):
                            break;
                        case("socket"):
                            break;
                        case("soap"):
                            break;
                    }
                }
            }
            
            if (nome.equals("algoritmo")){
                parametro = parametro.substring(nome.length()+1);
                String[] valori = parametro.split(",");
                for (String valore : valori){
                    switch (valore){
                        
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

                    this.vc.doAct();

                    //vc.doAct();

                }
                
                for (TrasmettitoreProxy trasmettitore : trasmettitori){
                    trasmettitore.configura(monitor,conf); 

                    this.vc.doAct();

                    //vc.doAct();

                }
                
            
            try{    
    		boolean continua=true;
			while (continua){
                            Object tmp = monitor.prelevaRichiesta();

                            try {
                                
                                timestamp[1] = Integer.parseInt(tmp.toString().split("--")[1]);

                            } catch (Exception k) {
                                System.out.println("Non tutti sono numeri timestamp");

                            }

                            vc.receiveAction(timestamp);

                            Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    for (TrasmettitoreProxy trasmettitore : trasmettitori){
                                        vc.sendAction();

                                        risp = risp.toString() +"_"+ vc.getValue(id) +","+ id;
                                        trasmettitore.invia(risp);              
                                    }
                                }
                        }
		}catch (Exception e){e.printStackTrace();}
            }    
	}



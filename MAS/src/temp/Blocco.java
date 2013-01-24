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
import temp.ricevitori.SOAP.RicevitoreSOAP;


public class Blocco implements Runnable{
    
    private Monitor monitor = new Monitor();
    private LinkedList<RicevitoreProxy> ricevitori = new LinkedList<>();
    private AlgoritmoProxy algoritmo; 
    private TrasmettitoreProxy trasmettitore;
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

    public TrasmettitoreProxy getTrasmettitore() {
        return trasmettitore;
    }

    public void setTrasmettitore(TrasmettitoreProxy trasmettitore) {
        this.trasmettitore = trasmettitore;
    }
	
    @Override
	public void run(){
		for (RicevitoreProxy ricevitore : ricevitori){
                    ricevitore.configura(monitor,conf);                   
                }
                
                if (trasmettitore != null){
                    trasmettitore.configura(monitor,conf);                   
                }
            
            try{    
    		boolean continua=true;
			while (continua){
                                Object tmp = monitor.prelevaRichiesta();
				Object risp = algoritmo.valuta(tmp);
                                if (risp != null){
                                    trasmettitore.invia(risp);
                                }
                        }
		}catch (Exception e){e.printStackTrace();}
            }    
	}



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuratore;

import blocco.Configurazione;
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
import java.util.LinkedList;

/**
 *
 * @author alessandra
 */
public class Configuratore implements Runnable{


    private Monitor monitor = new Monitor();
    private RicevitoreProxy ricevitore;
    private TrasmettitoreProxy trasmettitore;
    private AlgoritmoProxy algoritmo; 
    private Configurazione conf = new Configurazione();
    private static int timeout = 10;
    private static String NAME = "backup.name";
    private static String IP = "backup.ip";
    public static String DIR_PATH = "backup.dir_path";
    public static String FILE_NAME = "backup.file_name";
    private static String BACKUP_IP = "backup.ip";
    private static String NEXT_NAME = "nextbackup.name";
    private static String NEXT_IP = "nextbackup.ip";
    private static String FORMAT= "backup.format";
    private static String IN_DIR_PATH = "backup.dir_path";
    private static String IN_FILE_NAME = "backup.file_name";
    private static String NEXT_DIR_PATH = "nextbackup.dir_path";
    private static String NEXT_FILE_NAME = "nextbackup.file_name";   

    public static String getBACKUP_IP() {
        return BACKUP_IP;
    }

    public static String getFORMAT() {
        return FORMAT;
    }

    public static void setFORMAT(String FORMAT) {
        Configuratore.FORMAT = FORMAT;
    }

    public static void setBACKUP_IP(String BACKUP_IP) {
        Configuratore.BACKUP_IP = BACKUP_IP;
    }

    public static String getNEXT_NAME() {
        return NEXT_NAME;
    }

    public static void setNEXT_NAME(String NEXT_NAME) {
        Configuratore.NEXT_NAME = NEXT_NAME;
    }

    public static String getNEXT_IP() {
        return NEXT_IP;
    }

    public static void setNEXT_IP(String NEXT_IP) {
        Configuratore.NEXT_IP = NEXT_IP;
    }

    public static String getIN_DIR_PATH() {
        return IN_DIR_PATH;
    }

    public static void setIN_DIR_PATH(String IN_DIR_PATH) {
        Configuratore.IN_DIR_PATH = IN_DIR_PATH;
    }

    public static String getIN_FILE_NAME() {
        return IN_FILE_NAME;
    }

    public static void setIN_FILE_NAME(String IN_FILE_NAME) {
        Configuratore.IN_FILE_NAME = IN_FILE_NAME;
    }

    public static String getNEXT_DIR_PATH() {
        return NEXT_DIR_PATH;
    }

    public static void setNEXT_DIR_PATH(String NEXT_DIR_PATH) {
        Configuratore.NEXT_DIR_PATH = NEXT_DIR_PATH;
    }

    public static String getNEXT_FILE_NAME() {
        return NEXT_FILE_NAME;
    }

    public static void setNEXT_FILE_NAME(String NEXT_FILE_NAME) {
        Configuratore.NEXT_FILE_NAME = NEXT_FILE_NAME;
    }
    
        
    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        Configuratore.IP = IP;
    }

    public static String getDIR_PATH() {
        return DIR_PATH;
    }

    public static void setDIR_PATH(String DIR_PATH) {
        Configuratore.DIR_PATH = DIR_PATH;
    }

    public static String getFILE_NAME() {
        return FILE_NAME;
    }

    public static void setFILE_NAME(String FILE_NAME) {
        Configuratore.FILE_NAME = FILE_NAME;
    }
    
    

    public static String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        Configuratore.NAME = NAME;
    }


    public static int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        Configuratore.timeout = timeout;
    }
    
   
    public Configurazione getConf() {
        return conf;
    }

    public void setConf(Configurazione conf) {
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


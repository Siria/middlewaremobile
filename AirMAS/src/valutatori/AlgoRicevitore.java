/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import com.sun.enterprise.config.serverbeans.Module;
import configuratore.Configuratore;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import logger.FileLogger;
import org.glassfish.api.web.Constants;

/**
 *
 * @author alessandra
 */

public class AlgoRicevitore implements AlgoritmoProxy{
    

    public static FileLogger logfile;
    public static Proxy proxy;
    private LogManager logManager;


    private XMLBuilder xmlBuilder = new XMLBuilder();
   

    
    @Override
    public Object valuta(Object messaggio){
        logfile = new FileLogger();
        logfile.init();
  
        Evento e = new Evento(messaggio.toString());
        return ricevi_disp(e).toString();
    }
    
    
        public void init() {


        logfile = new FileLogger();
        logfile.init();
        logManager = new LogManager();
        logManager.initBackup();
        logManager.initLogListener();
        logfile.getLogger().log(Level.INFO, "[{0}] Local configuration loaded", Module.class.getSimpleName());
        logfile.getLogger().log(Level.INFO, "[{0}] Logger loaded", Module.class.getSimpleName());

        logfile.getLogger().log(Level.INFO, "[{0}] Proxy instanced", Module.class.getSimpleName());

        logManager.initReplicator();

    }
    
   public static Object ricevi_disp(Evento e) {
            switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                            System.out.println("Ho ricevuto " + e.get("content").toString()); //bisogna fare i cast espliciti tipo ((String)e.get("content")).toString()
                            return e;
                            
                        case "control":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                            
                        case "data":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){
                        case "my":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                            
                        case "other":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                            
                        case "modify":
                            System.out.println("Ho ricevuto " + e.get("content").toString());
                            return e;
                        }
                break;
                    
                    case "primary_backup" :
                        switch (e.get("context").toString()){
                            case "update" :
                                if(e.get("content").equals("ok")) {
                                    LogManager.initialIndex = LogManager.endIndex;
                                    LogManager.oldTransactionStatus = true;
                                    System.out.println("Replicazione realizzata con successo");
                                }
                                else if(e.get("content").equals("fail")) {
                                    LogManager.oldTransactionStatus = false;
                                    System.err.println("Replicazione fallita");
                                }
                                LogManager.transaction = false;
                                LogManager.timeout = Configuratore.getTimeout();
                                break;
                        }
            }
        return null;     
        
        }
   
        
            
    }


	

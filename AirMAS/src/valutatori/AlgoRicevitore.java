/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import com.sun.enterprise.config.serverbeans.Module;
import configuratore.Configuratore;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import logger.LogFile;

/**
 *
 * @author alessandra
 */

public class AlgoRicevitore implements AlgoritmoProxy{
    

    public static LogFile logfile;
    public static Proxy proxy;
    private LogManager logManager;


    private XMLBuilder xmlBuilder = new XMLBuilder();
   

    
    @Override
    public Object valuta(Object messaggio){
        System.out.println("Sono nel Blocco Ricevitore");
        //logfile = new LogFile();
        //logHistory();
        //logfile.init();
  
        Evento e = new Evento(messaggio.toString());
        return ricevi_disp(e).toString();
    }
    
    
        public void init() {

        logfile = new LogFile();
        logfile.init();
        logManager = new LogManager();
        logManager.initBackup();
        logManager.initLogManager();
        logfile.getLogger().log(Level.INFO, "[{0}] Ho Instanziato il FileLogger", blocco.Blocco.class.getSimpleName());
        logfile.getLogger().log(Level.INFO, "[{0}] Ho instanziato il LogManager", blocco.Blocco.class.getSimpleName());

        logfile.getLogger().log(Level.INFO, "[{0}] Proxy instanced", blocco.Blocco.class.getSimpleName());

        logManager.init();

    }
    
   public static Object ricevi_disp(Evento e) {
            switch (e.get("type").toString()){
                
                case "posizione":
                    switch (e.get("context").toString()){
                        case "start":
                        case "control":
                        case "data":
                            System.out.println("Ho ricevuto un messaggio di tipo: POSIZIONE");
                            System.out.println("Contenuto del messaggio: " + e.get("content").toString());
                            return e;
                        }
                break;
                
                case "alarm":
                    switch (e.get("context").toString()){
                        case "my":
                        case "other":
                        case "modify":
                            System.out.println("Ho ricevuto un messaggio di tipo: ALARM");
                            System.out.println("Contenuto del messaggio: " + e.get("content").toString());
                            return e;
                        }
                break;
                    
                    case "primary_backup" :
                        switch (e.get("context").toString()){
                            case "update" :
                                if(e.get("content").equals("ok")) {
                                    LogManager.indexFirst = LogManager.end;
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
                    default:
                    System.out.println("Ho ricevuto un messaggio di tipo: SCONOSCIUTO");            
            }
        return null;     
        
        }
   
    private static void logHistory() {
        File lLogs = new File("logs");
        File dirOldLogs = new File("logs/oldLogs");
        if (!dirOldLogs.exists()) {
            dirOldLogs.mkdir();
        }        
        File newFile;
        if (lLogs.exists()) {
            if (lLogs.listFiles().length > 1) {
                File[] logs = lLogs.listFiles();
                for (int i = 0 ; i < logs.length; i++) {
                    if(!logs[i].isDirectory()) {
                        String fileName = logs[i].getName();
                        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
                        int counter = dirOldLogs.listFiles().length; 
                        newFile = new File(dirOldLogs, tokens[0] + "." + counter + "." + tokens[1]);
                        logs[i].renameTo(newFile);
                        break;
                    }
                }
            }
        }
        
            
    }

}


	

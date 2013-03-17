/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

/**
 *
 * @author alessandra
 */


import blocco.proxy.AlgoritmoProxy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import valutatori.XMLBuilder;


import org.w3c.dom.Document;



    
public class AlgoPrimary implements AlgoritmoProxy {
    

    private File dir;
    private boolean startTimeout = false;
    private int timeout = 10;
    private Map<String, Backup> backupMap = new TreeMap<>();
    private List<String> ack = new CopyOnWriteArrayList<>();
    private String updateMessage;
    private int idLastTransaction;
    private int countActiveBackup;
    private XMLBuilder xmlBuilder;

    
    public AlgoPrimary(){
        dir = new File("backup");
        if(!dir.exists()) {
            dir.mkdir();
        }
        logHistory();
        initBackup();
        Thread t;
        t = new Thread(){
     @Override
     public void run(){
         while(true){
             if(startTimeout && timeout > 0){
                 timeout --;
                 if(timeout == 0){
                     //try {
                         if(ack.isEmpty()){
                             for (Map.Entry<String, Backup> entry : backupMap.entrySet()){
                                 Backup backup = entry.getValue();
                                 backup.setState("fault");
                                 countActiveBackup--;
                             }
                         }
                         else{
                             for (Map.Entry<String, Backup> entry : backupMap.entrySet()){
                                 String name = entry.getKey();
                                 Backup backup = entry.getValue();
                                 if(!ack.contains(name)){
                                     backup.setState("fault");
                                     countActiveBackup--;
                                 }
                             }
                         }
                         String messageToNextModule = createAckUpdateMessage(updateMessage, "fail");
                         valuta(messageToNextModule);
                         startTimeout = false;
                         timeout = configuratore.Configuratore.getTimeout();
                         System.err.println("Transaction timeout!");
                     
                     
                 }
             }
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(AlgoPrimary.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             }

 };
        t.start();
    }
    
    private void initBackup(){
        String[] name = configuratore.Configuratore.getNAME().split(",");
        String[] ipArray = configuratore.Configuratore.getIP().split(",");
        String[] dir_Path = configuratore.Configuratore.getDIR_PATH().split(",");
        String[] file_Name = configuratore.Configuratore.getFILE_NAME().split(",");
        int i = 0;
        if(name.length > 0){
            String nextIp;
            String nextDirPath;
            String nextFileName;
            
            for (String s : name) {
                nextIp = ipArray[i];
                nextDirPath = dir_Path[i];
                nextFileName = file_Name[i];
                Backup backup = new Backup(s, nextIp, nextDirPath, nextFileName, "active");
                backupMap.put(s, backup);
                i++;
                countActiveBackup++;
            }
        }
    }
        @Override   
        public Object valuta(Object messaggio) {  
        String message = (String)messaggio;
        if(message!=null){

                Document document = xmlBuilder.parseStringToXML(message);
                String sender = xmlBuilder.getElement(document, "/message/header/from/name");
                String messaggioNextBlock = null;
                String objectType;
                switch(sender){
                    case "receiver" :
                        objectType = xmlBuilder.getElement(document, "/message/header/object/type");
                        switch(objectType){
                            case "update" :
                                updateMessage = (String) message;
                                if(countActiveBackup > 0){
                                    for (Map.Entry<String, Backup> entry : backupMap.entrySet()){
                                        Backup backup = entry.getValue();
                                        if(backup.getState().equals("active")){
                                            messaggioNextBlock = createPrepareCommitMessage(backup, document);
                                            if(messaggioNextBlock != null){
                                                valuta(messaggioNextBlock);
                                            }
                                        }
                                        startTimeout = true;
                                    }
                                }
                                else{
                                    int idTransaction = Integer.parseInt(xmlBuilder.getElement(document, "/message/body/id_transaction"));
                                    if(idTransaction != idLastTransaction){
                                        appendLogFile(updateMessage);
                                    }
                                    messaggioNextBlock = createAckUpdateMessage(updateMessage, "ok");
                                    if(messaggioNextBlock != null){
                                        
                                        valuta(messaggioNextBlock);
                                    }
                                }
                                break;
                        }
                
                        break;
                    case "primary_backup" :
                        objectType = xmlBuilder.getElement(document, "/message/header/object/type");
                        switch(objectType){
                        case "prepare_commit" :
                                messaggioNextBlock = createAckPrepareCommitMessage(document);                                  
                                if(messaggioNextBlock != null){
                                    valuta(messaggioNextBlock);
                                }
                                break;
                            case "commit" :
                                int idTransaction = Integer.parseInt(xmlBuilder.getElement(document, "/message/body/id_transaction"));
                                if(idTransaction != idLastTransaction){
                                    appendLogFile(message);
                                }
                                messaggioNextBlock = createAckCommitMessage(document);
                                if(messaggioNextBlock != null){
                                    
                                    valuta(messaggioNextBlock);
                                }
                                break;
                        }
                        break;
                    case "backup" :
                        Backup backup;
                        objectType = xmlBuilder.getElement(document, "/message/header/object/type");
                        String backupName;
                        switch(objectType){
                            case "prepared" :
                                backupName = xmlBuilder.getElement(document, "/message/body/backup_name");
                                backup = backupMap.get(backupName);
                                if(backup.getState().equals("active")){
                                    ack.add(backupName);
                                }
                                if(ack.size() == countActiveBackup){
                                    startTimeout = false;
                                    timeout = configuratore.Configuratore.getTimeout();
                                    ack.clear();
                                    for (Map.Entry<String, Backup> entry : backupMap.entrySet()){
                                        backup = entry.getValue();
                                        if(backup.getState().equals("active")){
                                            messaggioNextBlock = createCommitMessage(backup, updateMessage);
                                            if(messaggioNextBlock != null){
                                                
                                                valuta(messaggioNextBlock);
                                            }
                                        }
                                        startTimeout = true;
                                    }
                                }
                                break;
                            case "committed" :
                                int idTransaction = Integer.parseInt(xmlBuilder.getElement(document, "/message/body/id_transaction"));
                                backupName = xmlBuilder.getElement(document, "/message/body/backup_name");
                                backup = backupMap.get(backupName);
                                if(backup.getState().equals("active")){
                                    ack.add(backupName);
                                }
                                if(ack.size() == countActiveBackup){
                                    startTimeout = false;
                                    timeout = configuratore.Configuratore.getTimeout();
                                    ack.clear();
                                    if(idTransaction != idLastTransaction){
                                        appendLogFile(updateMessage);
                                    }
                                    messaggioNextBlock = createAckUpdateMessage(updateMessage, "ok");
                                    if(messaggioNextBlock != null){
                                        
                                        valuta(messaggioNextBlock);
                                    }
                                }
                                break;
                        }
                }                
            
          // }
        }
        return null;
    } 

    private boolean appendLogFile(String message) {
        Document document = xmlBuilder.parseStringToXML(message);
        BufferedWriter tmp = null;
        try {
            String format = xmlBuilder.getElement(document, "/message/body/log_format");
            String directory = dir.getName();
            File file = new File(directory, "ricevitore." + format);
            tmp = new BufferedWriter(new FileWriter(file, true));
            String[] line1 = message.split("<log_message>");
            String[] line2 = line1[1].split("</log_message>");
            String[] lines = line2[0].split("\\|");
            for(String s : lines){
                if(s.equals("")){
                    tmp.newLine();
                }
                else{
                    tmp.write(s);
                    tmp.newLine();
                    tmp.flush();
                }
            }
            tmp.newLine();
            tmp.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoPrimary.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(AlgoPrimary.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
    private static void logHistory() {
        File lLog = new File("backup");
        File dirOldLogs = new File("backup/oldLogs");
        if (!dirOldLogs.exists()) {
            dirOldLogs.mkdir();
        }        
        File newFile;
        if (lLog.exists()) {
            if (lLog.listFiles().length > 1) {
                File[] logs = lLog.listFiles();
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

    private String createAckCommitMessage(Document document) {
        Map<String, String> map = new TreeMap<>();
        String fromName = "backup";
        String fromIP = "";
        String toName = xmlBuilder.getElement(document, "/message/header/from/name");
        String toIP = xmlBuilder.getElement(document, "/message/header/from/ip");
        String toDestParam1 = "";
        String toDestParam2 = "";
        String objectType = "committed";
        String idTransaction = xmlBuilder.getElement(document, "/message/body/id_transaction");
        String backupName = configuratore.Configuratore.getNAME();
        map.put("id_transaction", idTransaction);
        map.put("backup_name", backupName);
        String ackCommitMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2, objectType, null, map);
       return ackCommitMessage;
    }

    private String createPrepareCommitMessage(Backup backup, Document document) {
         Map<String, String> map = new TreeMap<>(); 
        String fromName = "primary_backup";
        String fromIP = configuratore.Configuratore.getIP();
        String toName = backup.getName();
        String toIP = backup.getIp();
        String toDestParam1 = "";
        String toDestParam2 = "";
        String objectType = "prepare_commit";
        String idTransaction = xmlBuilder.getElement(document, "/message/body/id_transaction");
        String ip = configuratore.Configuratore.getIP();
        String dirPath = configuratore.Configuratore.getIN_DIR_PATH();
        String nameFile = configuratore.Configuratore.getIN_FILE_NAME();
        map.put("id_transaction", idTransaction);
        map.put("ip", ip);
        map.put("dir__path", dirPath);
        map.put("file_name", nameFile);
        String prepareCommitMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2,
             objectType, null, map);
        return prepareCommitMessage;
    }

    private String createAckPrepareCommitMessage(Document document) {
        Map<String, String> map = new TreeMap<>();
        String fromName = "backup";
        String fromIP = configuratore.Configuratore.getIP();
        String toName = xmlBuilder.getElement(document, "/message/header/from/name");
        String toIP = xmlBuilder.getElement(document, "/message/header/from/ip");
        String toDestParam1 = "";
        String toDestParam2 = "";
        String objectType = "prepared";
        String idTransaction = xmlBuilder.getElement(document, "/message/body/id_transaction");
        String backupName = configuratore.Configuratore.getNAME();
        String ip = configuratore.Configuratore.getIP();
        String dirPath = configuratore.Configuratore.getDIR_PATH();
        String nameFile = configuratore.Configuratore.FILE_NAME;
        map.put("id_transaction", idTransaction);
        map.put("backup_name", backupName);
        map.put("ip", ip);
        map.put("dir__path", dirPath);
        map.put("file_name", nameFile);
        String ackPrepareCommitMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2,
         objectType, null, map);
        return ackPrepareCommitMessage;
    }
    private String createCommitMessage(Backup backup, String message) {
        Map<String, String> map = new TreeMap<>(); 
        Document document = xmlBuilder.parseStringToXML(message);
        String fromName = "primary_backup";
        String fromIP = configuratore.Configuratore.getIP();
        String toName = backup.getName();
        String toIP = backup.getIp();
        String toDestParam1 = "";
        String toDestParam2 = "";
        
        String objectType = "commit";
        String format = xmlBuilder.getElement(document, "/message/body/log_format");
        String idTransaction = xmlBuilder.getElement(document, "/message/body/id_transaction");
        String ip = configuratore.Configuratore.getBACKUP_IP();
        String dirPath = configuratore.Configuratore.getIN_DIR_PATH();
        String nameFile = configuratore.Configuratore.getIN_FILE_NAME();
        String[] arr1 = message.split("<log_message>");
        String[] arr2 = arr1[1].split("</log_message>");
        String updateLogMessage = arr2[0];
        map.put("id_transaction", idTransaction);
        map.put("log_format", format);
        map.put("log_message", updateLogMessage);
        map.put("id_transaction", idTransaction);
        map.put("ip", ip);
        
        map.put("dir__path", dirPath);
        map.put("file_name", nameFile);
        String commitMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2,
          objectType, null, map);
        return commitMessage;
    }

//
    private String createAckUpdateMessage(String message, String status) {
        Map<String, String> map = new TreeMap<>();
        Document document = xmlBuilder.parseStringToXML(message);
        String fromName = "primary_backup";
        String fromIP = configuratore.Configuratore.getIP();
        String toName = xmlBuilder.getElement(document, "/message/header/from/name");
        String toIP = xmlBuilder.getElement(document, "/message/header/from/ip");
        String toDestParam1 = "";
        String toDestParam2 = "";

        String objectType = "update";
        String objectContext = status;
        String idTransaction = xmlBuilder.getElement(document, "/message/body/id_transaction");
        map.put("id_transaction", idTransaction);
        String ackTransactionMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2,
             objectType, objectContext, map);
        return ackTransactionMessage;
    }


}
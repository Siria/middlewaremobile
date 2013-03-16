package valutatori;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;


public class LogManager {
    
    private WatchService watcher = null;
    private Map<WatchKey,Path> keys = null;
    private boolean trace = false;
    private Path directory;
    private File dir;
    private File file;
    private String format;
    private String fileName;
    private String dirName;
    public static int initialIndex;
    public static int finalIndex;
    public static int startIndex;
    public static int endIndex;
    private XMLBuilder xmlBuilder = new XMLBuilder();
    public static boolean transaction = false;
    public static int timeout = configuratore.Configuratore.getTimeout();
    private int idTransaction;
    public static boolean oldTransactionStatus = true;
    private Map<String, Backup> backupMap = new TreeMap<>();
    private Backup actualPrimaryBackup;
    
    public void initBackup(){
        String[] nameArray = configuratore.Configuratore.getNAME().split(",");
        String[] ipArray = configuratore.Configuratore.getBACKUP_IP().split(",");
        String[] dirPathArray = configuratore.Configuratore.getDIR_PATH().split(",");
        String[] fileNameArray = configuratore.Configuratore.getFILE_NAME().split(",");
        int i = 0;
        if(nameArray.length > 0){
            String nextIp;
           
            String nextDirPath;
            String nextFileName;
            for(String s: nameArray){
                nextIp = ipArray[i];
                
                nextDirPath = dirPathArray[i];
                nextFileName = fileNameArray[i];
                Backup backup = new Backup(s, nextIp,  nextDirPath, nextFileName, "active");
                backupMap.put(s, backup);
                if(i==0){
                    actualPrimaryBackup = backup;
                }
                i++;
            }
        }
    }
    
    public void initReplicator(){

        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while(true){
                        Thread.sleep(10000);
                        String message = "";
                        if(oldTransactionStatus){
                            if(!transaction){
                                startIndex = initialIndex; 
                                endIndex = finalIndex;
                                if(endIndex != startIndex){
                                    transaction = true;
                                    oldTransactionStatus = false;
                                    for(int i = startIndex; i < endIndex; i ++){
                                        String line = FileUtils.readLines(file).get(i);
                                        message += line + "|";
                                    }
                                    idTransaction++;
                                    String messageToCommit = createUpdateMessage(actualPrimaryBackup, message);
                                    valuta(messageToCommit);
                                }
                                else{
                                    transaction = false;
                                }
                                
                            }
                        }
                        else{
                            if(endIndex != startIndex){
                                transaction = true;
                                for(int i = startIndex; i < endIndex; i ++){
                                    String line = FileUtils.readLines(file).get(i);
                                    message += line + "|";
                                }
                                String messageToCommit = createUpdateMessage(actualPrimaryBackup, message);
                                valuta(messageToCommit);
                            }
                            else{
                                transaction = false;
                            }
                        }
                    }
                } catch (InterruptedException ex) { 
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (IOException ex) {
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }            }

            private void valuta(String messageToCommit) {
                System.out.println("Elaborazione message in corso...");
            }
        };
        t.start();
        Thread t1 = new Thread(){
            @Override
            public void run(){
                while(true){
                    if(transaction && timeout > 0){
                        timeout --;
                        if(timeout == 0){
                            System.err.println("Transaction timeout!");
                            actualPrimaryBackup.setState("fault");
                            Backup backup;
                            boolean newBackup = false;
                            for (Map.Entry<String, Backup> entry : backupMap.entrySet()){
                                backup = entry.getValue();
                                if(backup.getState().equals("active")){
                                    actualPrimaryBackup = backup;
                                    newBackup = true;
                                    transaction = false;
                                    timeout = configuratore.Configuratore.getTimeout();
                                    System.out.println("New primary backup selected!");
                                    break;
                                }
                            }
                            if(!newBackup){
                                transaction = true;
                                System.err.println("No more backups are available: replication is impossible!");
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t1.start();
    }
    
    private String createUpdateMessage(Backup backup, String message) {
        Map<String, String> map = new TreeMap<>(); 
        String fromName = "receiver";
        String fromIP = configuratore.Configuratore.getIP();
        String toName = "primary_backup";
        String toIP = backup.getIp();
        String toDestParam1 = "";
        String toDestParam2 = "";
        
        String objectType = "update";
        String transactionNumber = "" + idTransaction;
        String ip = configuratore.Configuratore.getIP();  
        String dirPath = configuratore.Configuratore.getDIR_PATH();
        String nameFile = configuratore.Configuratore.getNEXT_DIR_PATH();
        map.put("id_transaction", transactionNumber);
        map.put("ip", ip);
        map.put("dir_path", dirPath);
        map.put("file_name", nameFile);
        map.put("log_format", format);
        map.put("log_message", message);
        String updateMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2,
             objectType, null, map);
        return updateMessage;
    }
    
    public void initLogListener(){
        dir = new File("logs");
        format = configuratore.Configuratore.getFORMAT();
        dirName = dir.getName();
        fileName = "/module." + format;
        file = new File(dirName, fileName);
        directory = Paths.get(dirName);
        try {
            this.watcher = FileSystems.getDefault().newWatchService();
            this.keys = new HashMap<>();
            register(directory);
        } 
        catch (IOException ex) {
            Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        }        Thread t = new Thread(){
            @Override
            public void run(){
                processEvents();
            }
        };
        t.start();
    }
      
    private void processEvents() {
        while(true) {
            WatchKey key = null;
            
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            Path getDir = keys.get(key);
            if (getDir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        finalIndex = finalIndex + 3;
                }
            }
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
    
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }
}

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
import javax.naming.ServiceUnavailableException;
import javax.xml.ws.Service;
import org.apache.commons.io.FileUtils;

// devo sistemare un mare di cose per integrarlo nel prog
/* i metodi sn richiamati nel seguente ordine
 * LogListener
processEvent
createUpdateMessage
se mente il file si modifica si setta a TRUE il flag transations

elaborateMessage
createPrepareCommitMessage

createAckPrepareCommitMessage

createCommitMessage

writeBackupLog
createAckCommitMessage

writeBackupLog,
createAckMessage

setta initialIndex
setta il flag transaction a false

sia receiver che primary hanno un timer
 */

public class LogListener {
    
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
    private Service remoteService;
   // private XMLBuilder xmlBuilder = new XMLBuilder();
    public static boolean transaction = false;
    public static int timeout = configuratore.Configuratore.getTimeout();
    private int idTransaction;
    public static boolean oldTransactionStatus = true;
    private Map<String, Backup> backupMap = new TreeMap<>();
    private Backup actualPrimaryBackup;
    
    public void initBackup(){
//        String[] nameArray = "";
//        String[] ipArray = "";
//        String[] cfArray = "";
//        String[] queueArray = "";
//        String[] wsPortArray = "";
//        String[] restPortArray = "";
//        String[] dirPathArray ="" ;
//        String[] fileNameArray = ""; 
//        int i = 0;
//        if(nameArray.length > 0){
//            String nextIp;
//            String nextSocketPort;
//            String nextCF;
//            String nextQueue;
//            String nextWsPort;
//            String nextRestPort;
//            String nextDirPath;
//            String nextFileName;
//            for(String s: nameArray){
//                nextIp = ipArray[i];
//                nextSocketPort = socketPortArray[i];
//                nextCF = cfArray[i];
//                nextQueue = queueArray[i];
//                nextWsPort = wsPortArray[i];
//                nextRestPort = restPortArray[i];
//                nextDirPath = dirPathArray[i];
//                nextFileName = fileNameArray[i];
//                Backup backup = new Backup(s, nextIp, nextSocketPort, nextCF, nextQueue, nextWsPort, nextRestPort, nextDirPath, nextFileName, "active");
//                backupMap.put(s, backup);
//                if(i==0){
//                    actualPrimaryBackup = backup;
//                }
//                i++;
//            }
//        }
//    }
//    
//    public void initReplicator(){
//        remoteService = ServiceFactory.getInstance().getService();
//        Thread t = new Thread(){
//            @Override
//            public void run(){
//                try {
//                    while(true){
//                        Thread.sleep(10000);
//                        String message = "";
//                        if(oldTransactionStatus){
//                            if(!transaction){
//                                startIndex = initialIndex; 
//                                endIndex = finalIndex;
//                                if(endIndex != startIndex){
//                                    transaction = true;
//                                    oldTransactionStatus = false;
//                                    for(int i = startIndex; i < endIndex; i ++){
//                                        String line = FileUtils.readLines(file).get(i);
//                                        message += line + "|";
//                                    }
//                                    idTransaction++;
//                                    String messageToCommit = createUpdateMessage(actualPrimaryBackup, message);
//                                    remoteService.valuta(messageToCommit);
//                                }
//                                else{
//                                    transaction = false;
//                                }
//                                
//                            }
//                        }
//                        else{
//                            if(endIndex != startIndex){
//                                transaction = true;
//                                for(int i = startIndex; i < endIndex; i ++){
//                                    String line = FileUtils.readLines(file).get(i);
//                                    message += line + "|";
//                                }
//                                String messageToCommit = createUpdateMessage(actualPrimaryBackup, message);
//                                remoteService.elaborateMessage(messageToCommit);
//                            }
//                            else{
//                                transaction = false;
//                            }
//                        }
//                    }
//                } catch (ServiceUnavailableException ex) {
//                   Logger.getLogger(LogListener.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException | InterruptedException ex) {
//                    Logger.getLogger(LogListener.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        };
//        t.start();
//        Thread t1 = new Thread(){
//            @Override
//            public void run(){
//                while(true){
//                    if(transaction && timeout > 0){
//                        timeout --;
//                        if(timeout == 0){
//                            System.err.println("Transaction timeout!");
//                            actualPrimaryBackup.setState("fault");
//                            Backup backup;
//                            boolean newBackup = false;
//                            for (Map.Entry<String, Backup> entry : backupMap.entrySet()){
//                                backup = entry.getValue();
//                                if(backup.getState().equals("active")){
//                                    actualPrimaryBackup = backup;
//                                    newBackup = true;
//                                    transaction = false;
//                                    timeout = configuratore.Configuratore.getTimeout();
//                                    System.out.println("New primary backup selected!");
//                                    break;
//                                }
//                            }
//                            if(!newBackup){
//                                transaction = true;
//                                System.err.println("No more backups are available: replication is impossible!");
//                            }
//                        }
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(LogListener.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        };
//        t1.start();
//    }
//    
//    private String createUpdateMessage(Backup backup, String message) {
//        Map<String, String> map = new TreeMap<>(); 
//        String fromName = "receiver";
//        String fromIP = Module.localConfig.getProperties().getProperty(Constants.MODULE_IP);
//        String toName = "primary_backup";
//        String toIP = backup.getIp();
//        String toDestParam1 = "";
//        String toDestParam2 = "";
//        String communicationChannel = Module.localConfig.getProperties().getProperty(Constants.OUT_ADAPTER);
//        switch(communicationChannel){
//            case "socket" :
//                    toDestParam1 = toIP;
//                    toDestParam2 = backup.getSocketPort();
//                    break;
//                case "jms" :
//                    toDestParam1 = backup.getConnectionFactory();
//                    toDestParam2 = backup.getQueue();           
//                    break;
//                case "ws" :
//                    toDestParam1 = toIP;
//                    toDestParam2 = backup.getWsPort();            
//                    break;
//                case "rest" :
//                    toDestParam1 = toIP;
//                    toDestParam2 = backup.getRestPort();
//                    break;
//                case "file" :
//                    toDestParam1 = backup.getDirPath();
//                    toDestParam2 = backup.getFileName(); 
//                    break;
//        }
//        String objectType = "update";
//        String transactionNumber = "" + idTransaction;
//        String ip = Module.localConfig.getProperties().getProperty(Constants.MODULE_IP);
//        String socketPort = Module.localConfig.getProperties().getProperty(Constants.IN_SOCKET_PORT);
//        String connectionFactory = Module.localConfig.getProperties().getProperty(Constants.IN_JMS_CF);
//        String queue = Module.localConfig.getProperties().getProperty(Constants.IN_JMS_QUEUE);
//        String wsPort = Module.localConfig.getProperties().getProperty(Constants.IN_WS_PORT);
//        String restPort = Module.localConfig.getProperties().getProperty(Constants.IN_REST_PORT);
//        String dirPath = Module.localConfig.getProperties().getProperty(Constants.IN_DIR_PATH);
//        String nameFile = Module.localConfig.getProperties().getProperty(Constants.IN_FILE_NAME);
//        map.put("id_transaction", transactionNumber);
//        map.put("ip", ip);
//        map.put("socket_port", socketPort);
//        map.put("jms_cf", connectionFactory);
//        map.put("jms_queue", queue);
//        map.put("ws_port", wsPort);
//        map.put("rest_port", restPort);
//        map.put("dir_path", dirPath);
//        map.put("file_name", nameFile);
//        map.put("log_format", format);
//        map.put("log_message", message);
//        String updateMessage = xmlBuilder.makeXMLString(fromName, fromIP, toName, toIP, toDestParam1, toDestParam2,
//            communicationChannel, objectType, null, map);
//        return updateMessage;
//    }
//    
//    public void initLogListener(){
//        dir = new File("logs");
//        format = Module.localConfig.getProperties().getProperty(Constants.LOG_FORMAT);
//        dirName = dir.getName();
//        fileName = "/module." + format;
//        file = new File(dirName, fileName);
//        directory = Paths.get(dirName);
//        try {
//            this.watcher = FileSystems.getDefault().newWatchService();
//            this.keys = new HashMap<>();
//            register(directory);
//        } catch (IOException ex) {
//             Module.logger.getLogger().log(Level.SEVERE, "[{0}] {1}", 
//                                new Object[]{FileCommunication.class.getSimpleName(), ex.getMessage()});
//        }
//        Thread t = new Thread(){
//            @Override
//            public void run(){
//                processEvents();
//            }
//        };
//        t.start();
//    }
//      
//    private void processEvents() {
//        while(true) {
//            WatchKey key;
//            try {
//                key = watcher.take();
//            } catch (InterruptedException ex) {
//                Module.logger.getLogger().log(Level.SEVERE, "[{0}] {1}", 
//                        new Object[]{BusinessLogic.class.getSimpleName(), ex.getMessage()});
//                return;
//            }
//            Path getDir = keys.get(key);
//            if (getDir == null) {
//                System.err.println("WatchKey not recognized!!");
//                continue;
//            }
//            for (WatchEvent<?> event: key.pollEvents()) {
//                WatchEvent.Kind kind = event.kind();
//                if (kind == StandardWatchEventKinds.OVERFLOW) {
//                    continue;
//                }
//                //WatchEvent<Path> ev = cast(event);
//                //Path name = ev.context();
//                //Path child = getDir.resolve(name);
//                //System.out.format("%s: %s\n", event.kind().name(), child);
//                
//                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
//                        finalIndex = finalIndex + 3;
//                }
//            }
//            boolean valid = key.reset();
//            if (!valid) {
//                keys.remove(key);
//                if (keys.isEmpty()) {
//                    break;
//                }
//            }
//        }
//    }
//    
//    @SuppressWarnings("unchecked")
//    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
//        return (WatchEvent<T>)event;
//    }
//    
//    private void register(Path dir) throws IOException {
//        WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
//                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
//        if (trace) {
//            Path prev = keys.get(key);
//            if (prev == null) {
//                System.out.format("register: %s\n", dir);
//            } else {
//                if (!dir.equals(prev)) {
//                    System.out.format("update: %s -> %s\n", prev, dir);
//                }
//            }
//        }
//        keys.put(key, dir);
    }
}

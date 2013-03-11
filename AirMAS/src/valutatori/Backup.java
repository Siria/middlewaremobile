package valutatori;



import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Backup {
    
    private String name;
    private String ip;
    private String socketPort;
    private String connectionFactory;
    private String queue;
    private String wsPort;
    private String restPort;
    private String state;
    private String dirPath;
    private String fileName;
    
    public Backup(String name, String ip, String socketPort, String connectionFactory, String queue, String wsPort, String restPort, String dirPath, String fileName, String state) {
        this.ip = ip;
        this.name = name;
        this.socketPort = socketPort;
        this.connectionFactory = connectionFactory;
        this.queue = queue;
        this.wsPort = wsPort;
        this.restPort = restPort;
        this.dirPath = dirPath;
        this.fileName = fileName;
        this.state = state;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIp() {
        return ip;
    }
    
    public String getSocketPort() {
        return socketPort;
    }
    
    public String getConnectionFactory() {
        return connectionFactory;
    }
    
    public String getQueue() {
        return queue;
    }
    
    public String getWsPort() {
        return wsPort;
    }
    
    public String getRestPort() {
        return restPort;
    }
    
    public String getDirPath() {
        return dirPath;
    }

    public String getFileName() {
        return fileName;
    }
    
    public String getState() {
        return state;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public void setSocketPort(String socketPort) {
        this.socketPort = socketPort;
    }
    
    public void setConnectionFactory(String connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    public void setQueue(String queue) {
        this.queue = queue;
    }
    
    public void setWsPort(String wsPort) {
        this.wsPort = wsPort;
    }
   
    public void setRestPort(String restPort) {
        this.restPort = restPort;
    }
    
    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        list.add("name=" + name);
        list.add("ip=" + ip);
        list.add("socketPort=" + socketPort);
        list.add("connectionFactory=" + connectionFactory);
        list.add("queue=" + queue);
        list.add("wsPort=" + wsPort);
        list.add("restPort=" + restPort);
        list.add("dirPath=" + dirPath);
        list.add("fileName=" + fileName);
        list.add("state=" + state);
        return StringUtils.join(list, " - ");
    }  
  
}

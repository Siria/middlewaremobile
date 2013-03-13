package valutatori;



import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Backup {
    
    private String name;
    private String state;
    private String dirPath;
    private String fileName;
    
    public Backup(String name, String dirPath, String fileName, String state) {
        this.name = name;
        this.dirPath = dirPath;
        this.fileName = fileName;
        this.state = state;
    }
    
    public String getName() {
        return name;
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
        list.add("dirPath=" + dirPath);
        list.add("fileName=" + fileName);
        list.add("state=" + state);
        return StringUtils.join(list, " - ");
    }  
  
}

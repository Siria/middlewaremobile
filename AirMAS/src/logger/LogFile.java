package logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogFile {
    
    static private FileHandler fileHandler;
    static private Formatter formatter;
    
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public void init() {
        try {
            File dir = new File("logs");
            if(!dir.exists()) {
                dir.mkdir();
            }
            logger.setLevel(Level.INFO);
            String format = configuratore.Configuratore.getFORMAT();
            String fileName = dir.getName() + "/module." +  format;
            fileHandler = new FileHandler(fileName);
            switch (format) {
                case "txt":
                    formatter = new TextFormatter();
                    break;
                case "xml":
                    formatter = new XMLFormatter();
                    break;
                case "html":
                    formatter = new HTMLFormatter();
                    break;
                default:
                    break;
            }
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(formatter);
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, "[{0}] {1}", new Object[]{LogFile.class.getSimpleName(), ex.getMessage()});
        }
    }
    
    public Logger getLogger() {
        return logger;
    }
    
}

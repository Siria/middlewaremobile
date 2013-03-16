package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class XMLFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder buf = new StringBuilder(1000);
        buf.append("<record>\n");
        buf.append("  <date>");
        buf.append(calcDate(record.getMillis()));
        buf.append("</date>\n");
        buf.append("  <level>");
        buf.append(record.getLevel());
        buf.append("</level>\n");
        buf.append("  <message>");
        buf.append(record.getMessage());    
        buf.append("</message>\n");
        buf.append("</record>\n");        
        return buf.toString();
    }
    
    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
    
    @Override
    public String getHead(Handler h) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<!DOCTYPE log SYSTEM \"logger.dtd\">\n" + "<log>\n";
    }

    @Override
    public String getTail(Handler h) {
        return "</log>\n";
    }
    
}

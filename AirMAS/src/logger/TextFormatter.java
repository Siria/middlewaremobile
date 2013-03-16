package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TextFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder buf = new StringBuilder(1000);
        buf.append(calcDate(record.getMillis()));
        buf.append("\n");
        buf.append(record.getLevel());
        buf.append(" : ");
        buf.append(formatMessage(record));
        buf.append("\n\n");
        return buf.toString();
      }

    private String calcDate(long millisecs) {
      SimpleDateFormat date_format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
      Date resultdate = new Date(millisecs);
      return date_format.format(resultdate);
    }

    @Override
    public String getHead(Handler h) {
      return "";
    }

    @Override
    public String getTail(Handler h) {
        return "";
    }
    
}

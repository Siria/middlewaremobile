package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


public class HTMLFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder buf = new StringBuilder(1000);
        buf.append("<tr>");
        buf.append("<td>");
        if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
            buf.append("<b>");
            buf.append("<font color=\"red\">");
            buf.append(record.getLevel());
            buf.append("</b>");
        } else {
            buf.append(record.getLevel());
        }
        buf.append("</td>");
        buf.append("<td>");
        if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
            buf.append("<b>");
            buf.append("<font color=\"red\">");
            buf.append(calcDate(record.getMillis()));
            buf.append("</b>"); 
        } else {
            buf.append(calcDate(record.getMillis()));
        }
        buf.append("</td>");
        buf.append("<td>");
        if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
            buf.append("<b>");
            buf.append("<font color=\"red\">");
            buf.append(formatMessage(record).replace("<", "&lt;").replace(">", "&gt"));
            buf.append("</b>"); 
        } else {
            buf.append(formatMessage(record).replace("<", "&lt;").replace(">", "&gt"));
        }
        //buf.append('\n');
        buf.append("<td>");
        buf.append("</tr>\n");
        return buf.toString();
      }

    private String calcDate(long millisecs) {
      SimpleDateFormat date_format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
      Date resultdate = new Date(millisecs);
      return date_format.format(resultdate);
    }

    @Override
    public String getHead(Handler h) {
        return "<HTML>\n<HEAD>\n" + (new Date()) 
                + "\n</HEAD>\n<BODY>\n<PRE>\n"
                + "<table width=\"100%\" border>\n  "
                + "<tr><th>Level</th>" +
                "<th>Time</th>" +
                "<th>Log Message</th>" +
                "</tr>\n";
    }

    @Override
    public String getTail(Handler h) {
        return "</table>\n  </PRE></BODY>\n</HTML>\n";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.proxy.AlgoritmoProxy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author Seby
 */
public class AlgoBackup implements AlgoritmoProxy{
    private XMLBuilder xmlBuilder;
    
    @Override
    public Object valuta(Object messaggio){
        // TODO qua va la funzione che scrive su file
        System.out.println("Ho ricevuto: " + messaggio );
            if (messaggio.toString().equals("prepareToCommit")){
                return "ack";
            } else {
                writeLog(messaggio.toString());
                return "ack";
            }
        
    }
    
    public void writeLog(String messaggio){

        Document document = xmlBuilder.parseStringToXML(messaggio);
        BufferedWriter tmp = null;
        try {
            String format = "LogFormat";
            String directory = "/message/";
            File file = new File(directory, "ricevitore." + format);
            tmp = new BufferedWriter(new FileWriter(file, true));
            String[] lines = messaggio.split("<log_message>");

            for(String s : lines){
                if (!s.contentEquals("ack")){
                if(s.equals("")){
                    tmp.newLine();
                }
                else{
                    tmp.write(s);
                    tmp.newLine();
                    tmp.flush();
                }
            }    }
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
    }
        
}

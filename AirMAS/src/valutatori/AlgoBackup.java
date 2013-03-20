/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author Seby
 */
public class AlgoBackup implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        if (messaggio.toString().length() > 30){
            System.out.println("Ho ricevuto: " + messaggio.toString().substring(0, 20) + "...");
        } else {
            System.out.println("Ho ricevuto: " + messaggio.toString());
        }    
            switch (messaggio.toString()){
                case "ack":
                break;
                case "prepareToCommit":
                    return "ack";
                case "":
                    return null;
                default:
                    Evento e = new Evento(messaggio.toString());
                    String ID = (String)e.get("id_backup");

                    System.out.println("Sono Backup"+ ID +": scrivo nel mio Log");
                    writeLog(messaggio.toString(), ID);
                    return "ack";
                    
            }
            return null;
        
    }
    
    public void writeLog(String messaggio, String ID){

        BufferedWriter tmp = null;
        try {
            File file = new File("ricevitore"+ID+".txt");
            tmp = new BufferedWriter(new FileWriter(file, true));
            
                if (messaggio.equals("ack")){
                }
                else{
                    java.util.Date date= new java.util.Date();
                    
                    tmp.write("--- " + new Timestamp(date.getTime()) + " ---\n\n");
                    tmp.write(messaggio);
                    tmp.newLine();
                    tmp.flush();
                }
                
            tmp.newLine();
            tmp.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoBackup.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                tmp.close();
            } catch (IOException ex) {
                Logger.getLogger(AlgoBackup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
}

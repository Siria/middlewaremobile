/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;
import temp.trasmettitori.File.TrasmettitoreFile;


/**
 *
 * @author alessandra
 */
public class RicevitoreFile extends Thread implements RicevitoreProxy{
    private HashMap conf;
    private Monitor monitor;

    @Override
    public void ricevi() {
        FileInputStream fis = null;        
        
        while (true) {
         try {
            File file = new File((String)conf.get("fileIngresso"));
            fis = new FileInputStream(file);
            FileLock fl = fis.getChannel().tryLock();
            boolean continua = true;
            StringBuilder messaggio = new StringBuilder();
            while(continua) {
              int read = fis.read();
              byte byteread;
              if (read != -1){
                  byteread = (byte)read;
                  messaggio.append((char)(byteread));
              } else {
                  continua = false;
              }
            }
            
            enqueue(messaggio.toString());
            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(TrasmettitoreFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(TrasmettitoreFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        }
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.conf = conf;
        this.monitor = monitor;
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ricevi();
            }
        });
        t.start();
    }
    
    @Override
    public void enqueue(Object messaggio) {
        monitor.accodaRichiesta(messaggio);
    }
}

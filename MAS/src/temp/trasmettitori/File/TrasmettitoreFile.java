/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
import test.ClientSM;

/**
 *
 * @author alessandra
 */
public class TrasmettitoreFile implements TrasmettitoreProxy{
    private Monitor monitor;
    private HashMap conf;


    @Override
    public void invia(Object messaggio) {
        FileOutputStream fos = null;        
        try {
            File file = new File((String)conf.get("fileUscita"));
            file.renameTo(new File ("Locked"+(String)conf.get("fileUscita")));
            fos = new FileOutputStream(file);
            FileLock fl = fos.getChannel().tryLock();
            if(fl != null) {
              fos.write(messaggio.toString().getBytes("UTF-8"));
              fl.release();
            }
            fos.close();
            file.renameTo(new File((String)conf.get("fileUscita")));
            
        } catch (IOException ex) {
            Logger.getLogger(TrasmettitoreFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(TrasmettitoreFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        this.conf = conf;
    }
}

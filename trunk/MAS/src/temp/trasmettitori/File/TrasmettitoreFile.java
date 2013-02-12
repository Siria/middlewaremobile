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
import java.nio.ByteBuffer;
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
                 try {
                     
                     
                    File file = new File((String)conf.get("fileUscita"));
            
                    FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
                    boolean obtained = false;
                    FileLock lock = null;
                    while (!obtained){
                    try{    
                    lock = channel.tryLock();
                    obtained = true;
                    } catch (Exception e){
                        System.out.println("sono il trasmettitore");
		
                        e.printStackTrace();
                        Thread.sleep(10);
                    }
                    }
                    channel.write(ByteBuffer.wrap(messaggio.toString().getBytes()));
                    //System.out.println(messaggio.toString());
                    lock.release();
		    channel.close();		    
		}
		catch (Exception e) {
                    System.out.println("sono il trasmettitore");
			e.printStackTrace();
		}
        
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        this.conf = conf;
    }
}

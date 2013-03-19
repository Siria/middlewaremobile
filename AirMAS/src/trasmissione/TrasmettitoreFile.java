/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trasmissione;

import blocco.Configurazione;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;

/**
 *
 * @author alessandra
 */
public class TrasmettitoreFile implements TrasmettitoreProxy{
    private Monitor monitor;
    private Configurazione conf;


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
    public void configura(Monitor monitor, Configurazione conf) {
        this.monitor = monitor;
        this.conf = conf;
    }
}

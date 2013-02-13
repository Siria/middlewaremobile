/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
            
                    FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
                    boolean obtained = false;
                    FileLock lock = null;
                    while (!obtained){
                    try{    
                    lock = channel.tryLock();
                    obtained = true;
                    } catch (Exception e){
                        Thread.sleep(100);
                    }
                    }
                    
                   
                    ByteBuffer bb = ByteBuffer.allocateDirect(128*1024);
                    StringBuilder messaggio = new StringBuilder();
                    
                    int nLetti = 0;
                    int nRead;
                    while ( (nRead=channel.read( bb )) != -1 )
                    {
                        nLetti++;
                        bb.position( 0 );
                        bb.limit( nRead );
                        while ( bb.hasRemaining() ){
                            messaggio.append((char)bb.get( ));
                        }
                        bb.clear( );
                    }
                    file.renameTo(new File("Rdata.dat"));
                    //file.delete();
                    
                    if (nLetti > 0){
                        System.out.println("Ricevo tramite File...");
                        enqueue(messaggio.toString());                    
                    }
                    lock.release();
		    channel.close();
                    Thread.sleep(150);
		}
		catch (Exception e) {
			System.out.println("I/O Error: " + e.getMessage());
		}
            
            
            
       
    }}

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
        monitor.accodaMessaggio(messaggio);
    }
}

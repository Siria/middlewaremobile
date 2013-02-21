/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.SM;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;

/**
 *
 * @author alessandra
 */
public class TrasmettitoreSM implements TrasmettitoreProxy{
    private Monitor monitor;
    private HashMap conf;


    @Override
    public void invia(Object messaggio) {
                 try {
                     
                    int shmSize = 1024; 
                   
                    RandomAccessFile file = new RandomAccessFile((String)conf.get("sharedUscita"),"rw");
                    
                    if(file.length() < shmSize) {
                        byte[] tmp = new byte[shmSize];
                        file.write(tmp);
                        file.seek(0); 
                      }
                    
                    FileChannel channel = file.getChannel();
                    
                    
                    
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
                    MappedByteBuffer shm = channel.map(FileChannel.MapMode.READ_WRITE, 0, shmSize);
                    lock.release();
                    channel.close(); 
                    
                    shm.load(); 
                    shm.put(messaggio.toString().getBytes("UTF-8"));
                   		    
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

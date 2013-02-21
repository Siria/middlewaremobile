/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.SM;


import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;


/**
 *
 * @author alessandra
 */
public class RicevitoreSM extends Thread implements RicevitoreProxy{
    private HashMap conf;
    private Monitor monitor;

    @Override
    public void ricevi() {
        FileInputStream fis = null;        
        
        while (true) {
         try {
                    File f = new File((String)conf.get("sharedIngresso"));
                    int shmSize = 1024;
                    RandomAccessFile file = new RandomAccessFile(f,"rw");

                    // inialize file size
                    if(file.length() < shmSize) {
                      byte[] tmp = new byte[shmSize];
                      file.write(tmp);
                      file.seek(0); // seek back to start of file.
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
                    shm.load(); // force file into physical memory.
                    lock.release();
                    // now use the ByteBuffer's get/put/position methods to read/write the shared memory
                    
                    
                   
                    ByteBuffer bb = ByteBuffer.allocateDirect(128*1024);
                    StringBuilder messaggio = new StringBuilder();
                    
                    int nLetti = 0;
                    int nRead;
                    boolean continua = false;
                    
                    while(!continua){
                    if (shm.get(0) != 0){
                            continua = true;
                        }
                        
                        Thread.sleep(100);
                    }
                    boolean continua2 = true;
                    while (continua2)
                    {
                        byte letto = shm.get( );
                        if (letto != 0){
                        nLetti++;
                        messaggio.append((char)letto);
                        } else {
                            continua2=false;
                        }
                    }
                    f.renameTo(new File("SMdata.dat"));
                    //file.delete();
                    
                    if (nLetti > 0){
                        System.out.println("Ricevo tramite Shared Memory...");
                        enqueue(messaggio.toString());                    
                    }
                    
		    channel.close();
		}
		catch (Exception e) {
                        e.printStackTrace();
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

package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Prova2 {
	
	public static void main(String[] args) {
		
		try {
			
		    File file = new File("fileToLock.dat");
		    
		    // Creates a random access file stream to read from, and optionally to write to
		    FileChannel channel = new RandomAccessFile(file, "rw").getChannel();

		    // Acquire an exclusive lock on this channel's file ( block until the region can be
		    // locked, this channel is closed, or the invoking thread is interrupted)
		    FileLock lock = channel.lock(0, Long.MAX_VALUE, true);

		    // Attempts to acquire an exclusive lock on this channel's file (does not block, an
		    // invocation always returns immediately, either having acquired a lock on the requested
		    // region or having failed to do so.
                    String str = "Prova2";
		    ByteBuffer bbuf = ByteBuffer.allocate(1024);
                    bbuf.put(str.getBytes());
                    bbuf.flip();
                    channel.write(bbuf);
                    System.out.println("ho scritto prova2");
                    	// tells whether this lock is shared
		    boolean isShared = lock.isShared();
		    
		    // release the lock
		    lock.release();

		    // close the channel
		    channel.close();
		    
		
                } catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
		}
		
	}

}



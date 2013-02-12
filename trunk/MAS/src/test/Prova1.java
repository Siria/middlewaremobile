package test;
 
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Prova1 {
 
    
    private static int count = 1024;
    
    public static void main(String[] args) throws Exception {
 
        RandomAccessFile file = null;
        FileLock fileLock = null;
        try
        {
            
            file = new RandomAccessFile("status", "rw");
            MappedByteBuffer in = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
      
            
            while (((char) in.get(0)) != 'Y'){
                System.out.println(in.get(0));
                Thread.sleep(10);
            }
            
            System.out.println("Finalmente");
            


            System.out.println("\n\nWriting to Memory Mapped File is completed");
 
            
        } catch (Exception e){


        }
    }
}
    
 
    
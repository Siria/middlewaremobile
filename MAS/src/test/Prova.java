package test;
 
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Prova {
 
    
    private static int count = 1024;
    
    public static void main(String[] args) throws Exception {
 
        RandomAccessFile file = null;
        RandomAccessFile file2 = null;
        FileLock fileLock = null;
        try
        {
            
            file = new RandomAccessFile("FileToBeLocked", "rw");
            MappedByteBuffer out = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
            
            file2 = new RandomAccessFile("status", "rw");
            MappedByteBuffer status = file2.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
            
            for (int i = 0; i < count; i++) {
            out.put((byte) 'A');
            }
            
            Thread.sleep(10000);
            
            for (int i = 0; i < count; i++) {
            status.put((byte) 'Y');
            }
            System.out.println("Writing to Memory Mapped File is completed");
 
            
        } catch (Exception e){


        }
    }
}
    
 
    
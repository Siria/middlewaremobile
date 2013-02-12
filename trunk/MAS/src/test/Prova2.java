package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Prova2 extends Thread {
       private final int position = 10;
       private final int size = 20;
       private int number;
       private int pause;
       private boolean flag;
       private String filename;

       public static void main(String args[]) {
            
            for (int i = 0; i < 3; i++) {
                     Prova2 thread = new Prova2(i, i + 1);
                     thread.start();
                }
        }

        public Prova2(int number, int s) {
                this.number = number;
                this.pause = s * 1000;
        }

        public void run() {
               try{
                  this.execute();
               }
               catch (IOException e){
                   e.printStackTrace();
               }
        }

        public void execute() throws IOException {
            //lettore

        
        }
}
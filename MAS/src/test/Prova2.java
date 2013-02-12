import java.io.IOException;
import java.io.RandomAccessFile;
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
            Boolean flag = Boolean.TRUE;
            String path="";
            if ( flag == Boolean.TRUE)
               System.out.println("*** Use of tryLock() ***");
            else 
                System.out.println("*** Use of lock() ***");
            for (int i = 0; i < 3; i++) {
                     Prova2 thread = new Prova2(i, i + 1,
                                        flag.booleanValue(), path );
                     thread.start();
                }
        }

        public Prova2(int number, int s, boolean flag, String path) {
                this.number = number;
                this.pause = s * 1000;
                this.flag = flag;
                this.filename = path + "/uselocks.txt";
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
        FileLock lock = null;
        RandomAccessFile raf=null;
        boolean LockObtained = false;
 
        try {
          raf = new RandomAccessFile(filename, "rw");
          FileChannel fc = raf.getChannel();
          System.out.println("Opening channel " + number);
          if ( flag )
                lock = fc.tryLock(position, size, false);
          else {
                while ( ! LockObtained ){
                   try{
                      lock = fc.lock(position, size, false);
                      LockObtained = true;
                   }
                   catch (IOException e){
                      LockObtained = false;
                   }
                }
          }
          System.out.println("Lock number " + number 
                 + " acquired. lock=" + lock);
          System.out.println("\tPausing " + (pause / 1000)
              + "s on lock number" + number );
          Thread.sleep(pause);
          System.out.println("\tEnd of pause of lock number " 
                                + number );
          if(lock != null) {
              System.out.println("\tFreeing lock number " + number);
              lock.release();
          }
          System.out.println("Closing channel " + number);
          if (raf != null) raf.close();
       }
       catch (InterruptedException ie) {
              if(lock != null) {
                 System.out.println("\tFreeing lock number " + number);
                 lock.release();
              }
              System.out.println("Closing channel " + number);
              if (raf != null) raf.close();
       }
    }
}
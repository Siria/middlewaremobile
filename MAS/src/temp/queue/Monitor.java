/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.queue;

/**
 *
 * @author Seby
 */
import java.util.LinkedList;

public class Monitor {
  
  // Coda delle richieste
  private LinkedList<Object> codaRichieste = new LinkedList<>();
  
  // Preleva la prima richiesta in coda
  public synchronized Object prelevaRichiesta(){
    while (codaRichieste.isEmpty()){
      try {
        wait();
      }
      catch (InterruptedException e){
        e.printStackTrace();
      }
    }
    
    return codaRichieste.remove(0);
  }

  // Accoda una nuova richiesta
  public synchronized void accodaRichiesta(Object richiesta){
    codaRichieste.add(richiesta);
    notifyAll();
  }
  
  public synchronized void test (){
      System.out.println("si sono il monitor e ho" + codaRichieste.size() + " richieste");
  }
} 
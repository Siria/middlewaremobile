/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.queue;

/**
 *
 * @author Seby
 */
import java.util.LinkedList;

public class Monitor {
  
  // Coda delle richieste
  private LinkedList<Object> codaRichieste = new LinkedList<>();
  
  // Preleva la prima richiesta in coda
  public synchronized Object prelevaMessaggio(){
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

  // Accoda una nuova messaggio
  public synchronized void accodaMessaggio(Object messaggio){
    codaRichieste.add(messaggio);
    //System.out.println("\nSono il monitor e ho appena ricevuto qualcosa...\n");//:\n" + messaggio.toString()); // TODO se tolgo questo nn vedo più i messaggi che arrivano
    notifyAll();
  }
  
  public synchronized void test (){
      System.out.println("si sono il monitor e ho" + codaRichieste.size() + " richieste");
  }
  
  public synchronized void empty (){
      codaRichieste.clear();
  }
  
} 
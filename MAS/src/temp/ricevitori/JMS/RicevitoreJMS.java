/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.JMS;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.jms.*;
import temp.Evento;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

@MessageDriven(mappedName="Test")
public class RicevitoreJMS implements MessageListener, RicevitoreProxy{
    
    Logger log;
    Monitor monitor;
    public HashMap conf = null;

    public RicevitoreJMS() {
    }

    public RicevitoreJMS(Logger log, Monitor monitor) {
        this.log = log;
        this.monitor = monitor;
    }
    
    @Override
    public void onMessage(Message message) {
	    System.out.println("In onMessage()!");
 
	    try {
	      if (message instanceof ObjectMessage) {
	        ObjectMessage msg = (ObjectMessage) message;
	        Evento ricevuto = (Evento)msg.getObject();
                enqueue(ricevuto);
                System.out.println("Ricevuto evento: " + ricevuto.getTipo() + " " + ricevuto.getContenuto());
                log.info("on message " + ricevuto.getTipo() + " " + ricevuto.getContenuto());
	      }
	    } catch (Exception e) {
	      System.out.println("Exception raised: " + e.toString());
	    }
	  }

    public void ricevi(Object messaggio) {
        System.out.println("Ricevo tramite JMS");
        onMessage((Message)messaggio);

    }

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
        monitor.accodaRichiesta(messaggio);
    }

    @Override
    public void ricevi() {
    }

	}

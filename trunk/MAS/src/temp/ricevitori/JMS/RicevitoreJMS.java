/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.JMS;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import temp.Evento;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;
import temp.trasmettitori.JMS.MessageConnection;
import temp.trasmettitori.JMS.MessageException;

public class RicevitoreJMS implements MessageListener, RicevitoreProxy {

    Logger log;
    Monitor monitor;
    public HashMap conf = null;
    private MessageConnection messageConnection;

    public RicevitoreJMS() {
    }

    public RicevitoreJMS(Logger log, Monitor monitor) {
        this.log = log;
        this.monitor = monitor;
    }

    private MessageConnection getMessageConnection() throws MessageException {
  if (messageConnection == null) {
    messageConnection = new MessageConnection();
  }
  return messageConnection;
}

/********************************************************************
* Attempts to receive a message.
*
* @throws MessageException If the receive fails.
********************************************************************/
public Message receive() throws MessageException {
    Message mess = getMessageConnection().receive(0);
        try {
            System.out.println("Ricevo tramite JMS...");
            ObjectMessage msg = (ObjectMessage) mess;
            Evento ricevuto = new Evento(msg.getObject().toString());
            enqueue(ricevuto);
            System.out.println("Ricevuto evento - " + ricevuto.toString());
        } catch (JMSException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
  return mess;
}

public void connect() throws MessageException {
  getMessageConnection().getConsumer();
}

public void disconnect() throws MessageException {
  if (messageConnection != null) {
    try {
      messageConnection.disconnect();
      messageConnection = null;
    } catch (MessageException e) {
      messageConnection = null;
      e.printStackTrace();
      throw e;
    }
  }
}

         @Override
         public void onMessage(Message message) {
             System.out.println("In onMessage()!");
      
                 try {
                   if (message instanceof ObjectMessage) {
                     ObjectMessage msg = (ObjectMessage) message;
                     Evento ricevuto = (Evento)msg.getObject();
                     enqueue(ricevuto);
                     System.out.println("Ricevuto evento - " + ricevuto.toString());
                     log.log(Level.INFO, "Ricevuto evento - {0}", ricevuto.toString()); // e' questo il logger che vogliono loro???
                   }
                 } catch (Exception e) {
                   System.out.println("Exception raised: " + e.toString());
                 }
               }


    

         @Override
         public void configura(Monitor monitor, HashMap conf) {
                     
             this.conf = conf;
             this.monitor = monitor;
        try {
            connect();
        } catch (MessageException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
             
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
        
        try {
            while(true)
                receive();
        } catch (MessageException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

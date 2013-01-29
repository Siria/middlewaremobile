/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.JMS;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import temp.Evento;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;

public class RicevitoreJMS implements MessageListener, RicevitoreProxy {

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
    @SuppressWarnings("SleepWhileInLoop")
    public void ricevi() {
        try {
            // try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) ic.lookup("jms/ConnectionFactory");
            Queue queue = (Queue) ic.lookup("jms/Queue");
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer messageConsumer = session.createConsumer(queue);
            connection.start();
            //Message msg = messageConsumer.receive();
            messageConsumer.setMessageListener(this);
            // wait for messages
            System.out.print("waiting for messages");
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.println();

            connection.close();
            {
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
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
                     System.out.println("Ricevuto evento: " + ricevuto.getTipo() + " " + ricevuto.getContenuto());
                     log.info("on message " + ricevuto.getTipo() + " " + ricevuto.getContenuto());
                   }
                 } catch (Exception e) {
                   System.out.println("Exception raised: " + e.toString());
                 }
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
        


	}

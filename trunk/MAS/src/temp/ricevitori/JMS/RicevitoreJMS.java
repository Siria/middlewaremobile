/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.JMS;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import temp.Evento;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import temp.trasmettitori.JMS.TrasmettitoreJMS;

@MessageDriven(mappedName="Test")
public class RicevitoreJMS implements MessageListener, RicevitoreProxy {

    Logger log;
    Monitor monitor;
    public HashMap conf = null;
    InitialContext ic = null;
    @Resource(lookup = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/Queue")
    private static Queue queue;
    private QueueConnection queueconnection;
    private QueueSession queuesession;

    public RicevitoreJMS() {
    }

    public RicevitoreJMS(Logger log, Monitor monitor) {
        this.log = log;
        this.monitor = monitor;
    }

    @Override
    public void ricevi() {
        try {
            ic = new InitialContext();
            queueconnection = (QueueConnection) connectionFactory.createConnection();
            queuesession = (QueueSession) queueconnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer messageConsumer = queuesession.createConsumer(queue);
            queueconnection.start();
            Message msg = messageConsumer.receive();
            onMessage(msg);
            //queueconnection.close();
            {
                if (ic != null) {
                    try {
                        ic.close();
                    } catch (Exception e) {
                        try {
                            throw e;
                        } catch (Exception ex) {
                            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                try {
                    if (queueconnection != null) {
                        queueconnection.close();
                    }
                } catch (JMSException jmse) {
                    System.out.println("Could not close connection " + queueconnection + " exception was " + jmse);
                }

            }
        } catch (NamingException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
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

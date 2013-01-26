/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.JMS;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.ws.rs.core.Context;
import temp.Evento;
import temp.proxy.RicevitoreProxy;
import temp.queue.Monitor;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
    

    /**
     *
     * @throws Exception
     */
    @Override
    public void ricevi() 
    {
        String destinationName = "queue/queueA";

        InitialContext ic = null;
        ConnectionFactory cf = null;
        Connection connection =  null;

        try
        {         
            ic = new InitialContext();

            cf = (ConnectionFactory)ic.lookup("/ConnectionFactory");
            Queue queue = (Queue)ic.lookup(destinationName);

            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);
            MessageConsumer subscriber = session.createConsumer(queue);

            subscriber.setMessageListener(this);
            connection.start();
            System.out.println("JMS Server listening.");

        } catch (JMSException | NamingException ex) {
            Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }        finally
        {         
            if(ic != null)
            {
                try
                {
                    ic.close();
                }
                catch(Exception e)
                {
                    try {
                        throw e;
                    } catch (Exception ex) {
                        Logger.getLogger(RicevitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            // ALWAYS close your connection in a finally block to avoid leaks.
            // Closing connection also takes care of closing its related objects e.g. sessions.
                    try
        {
            if (connection != null)
            {
                connection.close();
            }         
        }
        catch(JMSException jmse)
        {
            System.out.println("Could not close connection " + connection +" exception was " + jmse);
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

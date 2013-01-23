/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.JMS;
import javax.jms.*;
import javax.naming.*;

public class RicevitoreJMS {

    /**
     * Main method.
     *
     * @param args    
     */
    public static void main(String[] args) {
        String                  queueName = null;
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueReceiver           queueReceiver = null;
        TextMessage             message = null;
                
        /*
         * Read queue name from command line and display it.
         */
        if (args.length != 1) {
            System.out.println("Usage: java " +
                "SimpleQueueReceiver <queue-name>");
            System.exit(1);
        }
        queueName = new String(args[0]);
        System.out.println("Queue name is " + queueName);

        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " +
                "context: " + e.toString());
            System.exit(1);
        }
        
        /* 
         * Look up 
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup("QueueConnectionFactory");
            queue = (Queue) jndiContext.lookup(queueName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " +
                e.toString());
            System.exit(1);
        }

        try {
            queueConnection = 
                queueConnectionFactory.createQueueConnection();
            queueSession = 
                queueConnection.createQueueSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            queueReceiver = queueSession.createReceiver(queue);
            queueConnection.start();
            while (true) {
                Message m = queueReceiver.receive(1);
                if (m != null) {
                    if (m instanceof TextMessage) {
                        message = (TextMessage) m;
                        System.out.println("Reading message: " +
                            message.getText());
                    } else {
                        break;
                    }
                }
            }
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + 
                e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            }
        }
    }
}

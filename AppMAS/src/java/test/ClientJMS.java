/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import temp.ricevitori.JMS.RicevitoreJMS;
import temp.trasmettitori.JMS.MessageException;
import temp.trasmettitori.JMS.TrasmettitoreJMS;


/**
 *
 * @author Seby
 */
/**
 * This is a simple example to show some basics of JMS for a
 * publish - subscribe scenario.
 */
public class ClientJMS {
    public static final String TOPIC1 = "Simple.Test.Topic1";
    private static Logger log;
    
    
    

    /**
     * Create a JMS Publisher and Subscriber. Of course in the real world these
     * would be in separate applications. Start these processes and let them run
     * a while before shutting down. Execution comments will be logged.
     */
    public static void main(String[] args) throws Exception {

                log = Logger.getLogger("inizio Client");

 try {
    //--------------------------------
    // Establish sender and receiver
    //--------------------------------
    TrasmettitoreJMS sender = new TrasmettitoreJMS();
    sender.connect();

    RicevitoreJMS receiver = new RicevitoreJMS();
    receiver.connect();

    //--------------------------------
    // Create a text message
    //--------------------------------
    TextMessage message = sender.createTextMessage();
    message.setText("This is a test text message");

    //--------------------------------
    // Send the message
    //--------------------------------
    System.out.println("Sending message: " + message.getText());
    sender.send(message);

    //--------------------------------
    // Sleep for a few seconds
    //--------------------------------
   // try {
    //  Thread.sleep(3 * 1000);
    //} catch (InterruptedException ex) {
    //}

    //--------------------------------
    // Retrieve the message
    //--------------------------------
    TextMessage receivedMessage = (TextMessage) receiver.receive();
    System.out.println("Message received: " + receivedMessage.getText());

    //--------------------------------
    // Terminate connections
    //--------------------------------
    sender.disconnect();
    receiver.disconnect();
  } catch (JMSException jmsEx) {
    System.out.println("JMS Exception: " + jmsEx);
  } catch (MessageException msgEx) {
    System.out.println("Message Exception: " + msgEx);
  }
}
}

 
 
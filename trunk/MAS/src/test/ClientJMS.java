/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.sun.corba.se.pept.broker.Broker;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import temp.ricevitori.JMS.RicevitoreJMS;
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

		RicevitoreJMS receiver = new RicevitoreJMS();
		receiver.start(); // Runs on a seperate thread
		
		//Send one message manually
		String response = TrasmettitoreJMS.invia("Hello World");
		System.out.println("Response: " + response);
		Thread.sleep(1000);
		
		receiver.stopListening();
		
		System.exit(0); // Force exit
	}
    
      

}
        /*log.info("Inizio Client, now=" + System.currentTimeMillis());
        SendRecvClient client = new SendRecvClient();
        client.sendRecvAsync("A text msg");
        client.done.acquire();
        client.stop();
        log.info("End ");
        System.exit(0);
    }*/


 
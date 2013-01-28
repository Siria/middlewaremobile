/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.logging.Logger;
import temp.Evento;
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
        
        System.out.println("Client Jms");

        log = Logger.getLogger("inizio Client");

        RicevitoreJMS receiver = new RicevitoreJMS();
        //	receiver.start(); // Runs on a seperate thread
        receiver.ricevi();
        System.out.println("Ricevitore avviato...");
        //Send one message manually
        TrasmettitoreJMS sender = new TrasmettitoreJMS();
        Evento evento = new Evento();
        sender.invia(evento);
        System.out.println("Response: ");
        Thread.sleep(1000);

		System.exit(0); // Force exit
	}
}

 
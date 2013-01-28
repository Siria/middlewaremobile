/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.JMS;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.*;
import temp.Evento;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;

public class TrasmettitoreJMS implements TrasmettitoreProxy {
    
    Logger log;
    Monitor monitor;
    public HashMap conf = null;
    
    //Context ctx = new InitialContext();

    @Resource(lookup = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/Queue")
    private static Queue queue;
    private QueueConnection queueconnection;
    private QueueSession queuesession;
    

    public void send() {
        try {
            queueconnection = (QueueConnection) connectionFactory.createConnection();
            queuesession = (QueueSession) queueconnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = queuesession.createProducer(queue);
            queueconnection.start();
            Evento evento = new Evento();
            ObjectMessage msg;
            msg = queuesession.createObjectMessage(evento);
            messageProducer.send(msg);
            System.out.println("Dati dell'evento: " + evento.getTipo()
                    + " " + evento.getContenuto());      
            messageProducer.close();
            queueconnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void configura(Monitor monitor, HashMap conf) {
        this.monitor = monitor;
        this.conf = conf;

        
    }

    @Override
    public void invia(Object messaggio) {

        send();

    }
}

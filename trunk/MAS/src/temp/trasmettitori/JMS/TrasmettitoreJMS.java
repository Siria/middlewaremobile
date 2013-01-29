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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import temp.Evento;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;

public class TrasmettitoreJMS implements TrasmettitoreProxy {
    
    Logger log;
    Monitor monitor;
    public HashMap conf = null;
    

    

    public void send(Evento messaggio) {
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) ic.lookup("jms/ConnectionFactory") ;
            Queue queue = (Queue) ic.lookup("jms/Queue");
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);
            connection.start();
            ObjectMessage msg;
            msg = session.createObjectMessage(messaggio);
            messageProducer.send(msg);
            System.out.println("Dati dell'evento: " + messaggio.getTipo()
                    + " " + messaggio.getContenuto());      
            messageProducer.close();
            connection.close();
        } catch (NamingException | JMSException ex) {
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

        send((Evento)messaggio);

    }
}

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
    
    Context ctx;

    //@Resource(lookup = "jms/ConnectionFactory")
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    //@Resource(lookup = "jms/Queue")
    @Resource(mappedName = "jms/Queue")
    private static Queue queue;
    private Connection connection;
    private Session session;
    

    public void send(Evento messaggio) {
        try {
            ctx = new InitialContext();
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);
            connection.start();
            ObjectMessage msg;
            msg = session.createObjectMessage(messaggio);
            messageProducer.send(msg);
            System.out.println("Dati dell'evento: " + messaggio.getTipo()
                    + " " + messaggio.getContenuto());      
            messageProducer.close();
            connection.close();
        } catch (NamingException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
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

        send((Evento)messaggio);

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trasmissione;

import blocco.Configurazione;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import blocco.proxy.TrasmettitoreProxy;
import blocco.queue.Monitor;

public class TrasmettitoreJMS implements TrasmettitoreProxy {

    Logger log;
    Monitor monitor;
    public Configurazione conf = null;
    private MessageConnection messageConnection;

    @Override
    public void configura(Monitor monitor, Configurazione conf) {
        this.monitor = monitor;
        this.conf = conf;
        try {
            connect();
        } catch (MessageException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void invia(Object messaggio) {
        try {
            ObjectMessage message = createObjectMessage();
            
                message.setObject(messaggio.toString());
                if (message != null) {
                getMessageConnection().send(message);
                }
            
            
        } catch (JMSException | MessageException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MessageConnection getMessageConnection() throws MessageException {
        if (messageConnection == null) {
            messageConnection = new MessageConnection();
            messageConnection.destinationName = (String)conf.get("jmsUscita");
        }
        return messageConnection;
    }

    private Session getSession() throws MessageException {
        if (getMessageConnection() != null) {
            return getMessageConnection().getSession();
        }
        return null;
    }

    public void send(Message message) throws MessageException {
        if (message != null) {
            getMessageConnection().send(message);
        }
    }

    public void connect() throws MessageException {
        getMessageConnection().getProducer();
    }

    public void disconnect() throws MessageException {
        if (messageConnection != null) {
            try {
                messageConnection.disconnect();
                messageConnection = null;
            } catch (MessageException e) {
                messageConnection = null;
                e.printStackTrace();
                throw e;
            }
        }
    }

  
    public TextMessage createTextMessage() throws MessageException {
        TextMessage message = null;

        try {
            message = getSession().createTextMessage();
        } catch (JMSException e) {
            throw new MessageException("JMS Create Text Message Exception: " + e);
        }
        return message;
    }

    public BytesMessage createBytesMessage() throws MessageException {
        BytesMessage message = null;

        try {
            message = getSession().createBytesMessage();
        } catch (JMSException e) {
            throw new MessageException("JMS Create Bytes Exception: " + e);
        }
        return message;
    }

    public ObjectMessage createObjectMessage() throws MessageException {
        ObjectMessage message = null;

        try {
            message = getSession().createObjectMessage();
        } catch (JMSException e) {
            throw new MessageException("JMS Create Object Message Exception: " + e);
        }

        return message;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trasmissione;
import java.util.Hashtable;
import java.util.Properties;
import javax.jms.*;
import javax.naming.*;
/**
 *
 * @author alessandra
 */
public class MessageConnection {
public String destinationName = "myDestination"; //cambia i nomi su glassfish perchè a me da errore se metto gli "/"
public String factoryName = "myConnectionFactory";
private Context context;
private ConnectionFactory connectionFactory;
private Connection connection;
private Session session;
private Destination destination;
private MessageConsumer messageConsumer;
private MessageProducer messageProducer;
private boolean connected = false;


public MessageConnection() {
}


public Context getContext() throws MessageException {
  if (context == null) {
    try {
        
            Properties env = new Properties();
            
            env.put("java.naming.provider.url","iiop://127.0.0.1:3700");
            
            context = new InitialContext(env);
    } catch (NamingException e) {
      disconnect();
      e.printStackTrace();
      throw new MessageException("Naming Exception: " + e);
    }
  }
  return context;
}

public MessageConsumer getConsumer() throws MessageException {
  if (messageConsumer == null) {
    try {

      messageConsumer = getSession().createConsumer(getDestination());
      if (!connected) {
        getConnection().start();
        connected = true;
      }
    } catch (JMSException e) {
      disconnect();
      e.printStackTrace();
      throw new MessageException("JMS Create Receiver Exception: " + e);
    }
   }
   return messageConsumer;
}

public MessageProducer getProducer() throws MessageException {
  if (messageProducer == null) {
    try {
 
      messageProducer = getSession().createProducer(getDestination());
      if (!connected) {
        getConnection().start();
        connected = true;
      }
    } catch (JMSException e) {
      disconnect();
      e.printStackTrace();
      throw new MessageException("JMS Create Sender Exception: " + e);
    }
  }
  return messageProducer;
}

public ConnectionFactory getConnectionFactory() throws MessageException {
  if (connectionFactory == null) {
    try {

          connectionFactory = (ConnectionFactory) getContext().lookup(factoryName);
    } catch (NamingException e) {
      disconnect();
      e.printStackTrace();
      throw new MessageException("Naming Lookup Factory Exception: " + e);
    }
  }
  return connectionFactory;
}

public Connection getConnection() throws MessageException {
  if (connection == null) {
    try {

      connection = getConnectionFactory().createConnection();
    } catch (JMSException e) {
      disconnect();
      e.printStackTrace();
      throw new MessageException("JMS Create Queue Connection Exception: " + e);
    }
   }
   return connection;
}


public Session getSession() throws MessageException {
  if (session == null) {
    try {

      session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
    } catch (JMSException e) {
      disconnect();
      e.printStackTrace();
      throw new MessageException("JMS Create Session Exception: " + e);
    }
  }
  return session;
}

public Destination getDestination() throws MessageException {
  if (destination == null) {
    try {

      destination = (Destination) getContext().lookup(destinationName);
     } catch (NamingException e) {
       disconnect();
       e.printStackTrace();
       throw new MessageException("Naming Lookup Queue Exception: " + e);
     }
   }
   return destination;
}

public void connect() throws MessageException {
 
  getSession();
  if (!connected) {
    try {
      getConnection().start();
      connected = true;
    } catch (JMSException e) {
      e.printStackTrace();
      connected = false;
      disconnect();
      throw new MessageException("JMS Connection Exception: " + e);
    }
  }
}

public void disconnect() throws MessageException {
  if (connection != null) {
    try {
      connection.close();
   } catch (JMSException e) {
      e.printStackTrace();
      throw new MessageException("JMS Exception: " + e);
   }
  }

  connection = null;
  session = null;
  messageProducer = null;
  messageConsumer = null;

  connected = false;
}

public boolean isConnected() {
   return connected;
}

public void send(Message message) throws MessageException {
  try {
    getProducer().send(message);
  } catch (JMSException e) {
    e.printStackTrace();
    throw new MessageException("JMS Send Exception: " + e);
  }
}


public Message receive(int n) throws MessageException {
  Message message = null;

  try {
    message = getConsumer().receive(n);
  } catch (JMSException e) {
    e.printStackTrace();
    throw new MessageException("JMS Receive Exception: " + e);
  }
  return message;
} 
}
    

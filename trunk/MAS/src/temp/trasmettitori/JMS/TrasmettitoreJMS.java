/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.JMS;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import temp.Evento;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
import temp.trasmettitori.SOAP.TrasmettitoreSOAP;
import test.ClientJMS;




public class TrasmettitoreJMS extends HttpServlet implements TrasmettitoreProxy {
    
          Logger log;         
 
            
    private QueueConnection conn;
    private QueueSession session;
	 
	  @Resource(mappedName="QueueConnectionFactory")
	  private ConnectionFactory connectionFactory;
	 
	  @Resource(mappedName="Test")
	  private Queue queue;
         	 
	  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	 
	    response.setContentType("text/html;charset=UTF-8");
	 
	    try {
	      conn = (QueueConnection) connectionFactory.createConnection();
	      session = (QueueSession) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	      MessageProducer messageProducer = session.createProducer(queue);
	 
            Evento evento = new Evento();
	 
	      ObjectMessage msg;
                    msg = session.createObjectMessage(evento);
	      messageProducer.send(msg);
	      System.out.println("Dati dell'evento: " + evento.getTipo() +
	           " " + evento.getContenuto());
	 
	      messageProducer.close();
	      conn.close();
	    } catch (Exception e) {
	      System.out.println("Exception raised: " + e.toString());
	    }
	  }



    @Override
    public void configura(Monitor monitor, HashMap conf) 
    {
        try {
            InitialContext iniCtx = new InitialContext();
            Object tmp = iniCtx.lookup("ConnectionFactory");
            QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
            conn = qcf.createQueueConnection();
            queue = (Queue) iniCtx.lookup("queue/testQueue");
            session = conn.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
            conn.start();
        } catch (JMSException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(TrasmettitoreJMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void invia(Object messaggio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}

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
    
    private Connection conn;
    private Session session;
	 
    @Resource(mappedName = "QueueConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "Test")
    private Queue queue;
         	 
	/*  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	 
	    response.setContentType("text/html;charset=UTF-8");
	 
	    try {
            */
    public void send() {
        try {
            conn = (QueueConnection) connectionFactory.createConnection();
            session = (QueueSession) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);
            conn.start();
            Evento evento = new Evento();
            ObjectMessage msg;
            msg = session.createObjectMessage(evento);
            messageProducer.send(msg);
            System.out.println("Dati dell'evento: " + evento.getTipo()
                    + " " + evento.getContenuto());      
            messageProducer.close();
            conn.close();
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

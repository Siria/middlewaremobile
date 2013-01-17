/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.SOAP;

/**
 *
 * @author Seby
 */
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import temp.proxy.RicevitoreProxy;

@WebService(endpointInterface = "temp.ricevitori.SOAP.SOAP")
public class RicevitoreSOAP implements SOAP,RicevitoreProxy{
    ObjectOutputStream OOS = null;
    public HashMap conf = null;
	@Override
	public void ricevi(Object messaggio) {
		System.out.println("Ricevo tramite SOAP");
                this.inviaPerValutazione(messaggio);                
        }
        

        

        @Override
        public void ricevi() {
            Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"/WS/SOAP",this);
        }
        
        
    
    
    
    public RicevitoreSOAP(HashMap conf){
        this.conf = conf;       	
    }

    public final void mettitiInAscolto(){
        try{
            Socket SK = new Socket("localhost",(int)conf.get("portaAscoltoInterna"));
            OOS = new ObjectOutputStream(SK.getOutputStream());	
  	}catch(Exception e){
            e.printStackTrace();
	}
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    ricevi();
                }
            });
            t.start();
    }
    
    public void inviaPerValutazione(Object messaggio){
    	try{
            OOS.writeObject(messaggio);
            OOS.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
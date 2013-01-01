/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.SOAP;

/**
 *
 * @author Seby
 */
import java.util.HashMap;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import temp.ricevitori.AbstractRicevitore;

@WebService(endpointInterface = "temp.ricevitori.SOAP.SOAP")
public class RicevitoreSOAP extends AbstractRicevitore implements SOAP {

	@Override
	public void ricevi(Object messaggio) {
		System.out.println("Ricevo tramite SOAP");
                this.inviaPerValutazione(messaggio);                
        }

        

        public RicevitoreSOAP(HashMap conf) {
            super(conf);           
        }

        @Override
        public void ricevi() {
            Endpoint.publish("http://localhost:"+conf.get("portaAscoltoEsterna")+"/WS/SOAP",this);
        }
        
        

}
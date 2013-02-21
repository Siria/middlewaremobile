/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.SOAP;

/**
 *
 * @author Seby
 */
import javax.jws.WebMethod; 
import javax.jws.WebService; 

@WebService
public interface SOAP {
    @WebMethod void ricevi (Object messaggio);
}

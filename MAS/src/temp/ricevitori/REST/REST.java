/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.ricevitori.REST;

import javax.jws.WebMethod; 
import javax.jws.WebService; 

/**
 *
 * @author alessandra
 */
@WebService
public interface REST { 
        @WebMethod void ricevi (Object messaggio);
}

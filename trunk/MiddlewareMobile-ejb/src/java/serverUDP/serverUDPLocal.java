/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverUDP;

import javax.ejb.Local;

/**
 *
 * @author alessandra
 */
@Local
public interface serverUDPLocal {

    void businessMethod(String[] args);
    
}

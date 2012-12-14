/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientUDP;

import javax.ejb.Local;

/**
 *
 * @author alessandra
 */
@Local
public interface clientUDPLocal {


    void businessMethod(String[] args);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacchettoClient;

import javax.ejb.Local;

/**
 *
 * @author alessandra
 */
@Local
public interface ClientLocal {


    /**
     *
     * @param args
     */
    void businessMethod(String[] args);


    
}

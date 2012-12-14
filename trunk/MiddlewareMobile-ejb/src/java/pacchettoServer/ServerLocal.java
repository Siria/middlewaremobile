/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacchettoServer;

import javax.ejb.Local;

/**
 *
 * @author alessandra
 */
@Local
public interface ServerLocal {

   
    public void businessMethod(java.lang.String[] args);
    
}

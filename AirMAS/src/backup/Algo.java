/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author Seby
 */
public class Algo implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        return messaggio;
    }
	
}

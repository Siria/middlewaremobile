/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.proxy.AlgoritmoProxy;

/**
 *
 * @author Seby
 */

public class Algoritmo implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        return messaggio;
    }
	
}
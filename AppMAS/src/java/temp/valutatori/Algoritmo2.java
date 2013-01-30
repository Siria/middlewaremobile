/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import temp.proxy.AlgoritmoProxy;

/**
 *
 * @author Seby
 */

public class Algoritmo2 implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        messaggio = (messaggio + " Algo2... ");
        System.out.println(messaggio);
        return messaggio;
    }
	
}
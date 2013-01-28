/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import temp.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 */
public class Algoritmo6 implements AlgoritmoProxy{

    @Override
    public Object valuta(Object messaggio) {
        messaggio = (messaggio + " Algoritmo numero 6...");
        System.out.println(messaggio);
        return messaggio;
    }
    
}

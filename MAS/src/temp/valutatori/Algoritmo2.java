/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

/**
 *
 * @author Seby
 */

public class Algoritmo2 implements InterfaceAlgoritmo{
    
    @Override
    public Object valuta(Object messaggio){
        messaggio = (messaggio + " Algo2... ");
        System.out.println(messaggio);
        return messaggio;
    }
	
}
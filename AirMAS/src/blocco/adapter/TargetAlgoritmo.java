/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.adapter;

import configuratore.AlgoConfSoglie;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public interface TargetAlgoritmo{
    AlgoConfSoglie v = new AlgoConfSoglie();
    HashMap hvalue = (HashMap) v.valuta(v);
    public Object valute(Object messaggio);
}    


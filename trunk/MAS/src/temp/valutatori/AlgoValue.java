/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import blocco.configuratore.Variabile;
import java.util.HashMap;
import temp.Evento;
import temp.proxy.AlgoritmoProxy;

/**
 *
 * @author alessandra
 * 
 * bisogna realizzare dei controlli che abbiano come parametri
 * delle soglie che siano contenuti in file XML
 * Bisogna quindi, nel configuratore, effettuare il CREATE di un XML
 * cosi che nell'algoritmo AlgoValue sia possibile instanziare un READER
 */
public class AlgoValue implements AlgoritmoProxy{
    
    


    @Override
    public Object valuta(Object messaggio) {
         Evento e =  (Evento) messaggio;
         int x,v,a;
         if (e.get("type").toString().contentEquals("posizione")) {
            String[] parametri = e.get("content").toString().toLowerCase().split(";");
            for (String parametro : parametri) {
                String[] value = parametro.split(":");
                 //   for (int i = 0; i < value.length; i++) {
                        x = Integer.parseInt(value[0]);
                        v = Integer.parseInt(value[1]);
                        a = Integer.parseInt(value[2]);
                        if (compare(x,v,a))
                            return messaggio;
                        System.out.println();
        }

    }
        return null;
}


    private boolean compare(int x, int v, int a) {
        Variabile xvar = (Variabile) hvalue.get(x);
        Variabile vvar = (Variabile) hvalue.get(v);
        Variabile avar = (Variabile) hvalue.get(a);
        if (Integer.getInteger(xvar.getMin())<=x && x<=Integer.getInteger(xvar.getMax())) {
            if (Integer.getInteger(vvar.getMin())<=v && v<=Integer.getInteger(vvar.getMax())) {
                if (Integer.getInteger(avar.getMin())<=a && a<=Integer.getInteger(avar.getMax()))
            return true;
            }
        }
        return false;
    }

}
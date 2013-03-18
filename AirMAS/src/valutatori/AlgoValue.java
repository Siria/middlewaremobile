/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import configuratore.Variabile;
import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import configuratore.AlgoConfSoglie;

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
    
    AlgoConfSoglie v = new AlgoConfSoglie();
    HashMap hvalue = (HashMap) v.valuta(v);


    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco Filtro Value");
        
        Evento e =  new Evento(messaggio.toString());
        
        int x,v,a;
         if (e.get("type").toString().equals("posizione")) {
            String[] parametri = e.get("content").toString().toLowerCase().split(";");
            for (String parametro : parametri) {
                String[] value = parametro.split(":");
                        x = Integer.parseInt(value[0]);
                        v = Integer.parseInt(value[1]);
                        a = Integer.parseInt(value[2]);
                        if (compare(x,v,a)){
                            return e;
                        }    
                        
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
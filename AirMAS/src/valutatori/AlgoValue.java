/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import filtro.ConfFiltri;

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
    
    ConfFiltri filtri = new ConfFiltri(); 

    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco Filtro Value");
        
        Evento e =  new Evento(messaggio.toString());

         if (e.get("type").toString().equals("posizione")) {
            String[] parametri = e.get("content").toString().toLowerCase().split(";");
            
            String s[] = parametri[0].split(":"); //posizione
                for (int i=0; i<3;i++){
                    if (!filtri.checkAND("s"+(i+1), s[i]+"")){
                        System.out.println("Parametro S del Filtro NON rispettato");
                        return null;
                    }
                    if (!filtri.checkAND("v"+(i+1), s[i]+"")){
                        System.out.println("Parametro V del Filtro NON rispettato");
                        return null;
                    }
                    if (!filtri.checkAND("a"+(i+1), s[i]+"")){
                        System.out.println("Parametro A del Filtro NON rispettato");
                        return null;
                    }              
                }
                System.out.println("Tutti i parametri del Filtro sono rispettati");
                return e;
        }
        return e;
    }
}
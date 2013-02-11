/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

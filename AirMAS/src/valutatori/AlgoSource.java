/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valutatori;

import java.util.HashMap;
import blocco.Evento;
import blocco.proxy.AlgoritmoProxy;
import filtro.ConfFiltri;

/**
 *
 * @author alessandra
 */
public class AlgoSource implements AlgoritmoProxy{

    ConfFiltri filtri = new ConfFiltri();
    
    @Override
    public Object valuta(Object messaggio) {
        System.out.println("Sono nel blocco Filtro Source");
        
        Evento e =  new Evento(messaggio.toString());
                    if (filtri.checkOR("sourceType", e.get("sourceType").toString())){
                        System.out.println("Ho ricevuto un messaggio da: " + e.get("sourceType").toString().toUpperCase());
                        return e;
                    } else {
                        System.out.println("Origine del messaggio: SCONOSCIUTA");
                        return null;
                    }
                    
        }     
    }

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import blocco.Evento;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Seby
 */
public class TestVincolo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Scrivere tutti i vincoli qui per creare un XML di vincoli da rispettare
        //per i filtri
        
        Vincolo v1 = new Vincolo ();
        Vincolo v2 = new Vincolo ();
        Vincolo v3 = new Vincolo ();
        Vincolo v4 = new Vincolo ();
        Vincolo v5 = new Vincolo ();
        Vincolo v6 = new Vincolo ();
       
        
        v1.impostaVincolo ("sourceType","==","torre");
        v2.impostaVincolo ("sourceType","==","aereo");
        v3.impostaVincolo ("s1","<","300");
        v4.impostaVincolo ("s2","<","300");
        v5.impostaVincolo ("s3","<","300");
        v6.impostaVincolo ("a1","<","1000"); 
        HashMap<String,Vincolo> prop = new HashMap<>();
        prop.put("r1", v1);
        prop.put("r2", v2);
        prop.put("r3", v3);
        prop.put("r4", v4);
        prop.put("r5", v5);
        prop.put("r6", v6);
        ConfFiltri e = new ConfFiltri(prop);
        
        e.saveFiltri(new File("Filtri.xml"));
        
        e.regole=null; //resetto
        
        e.loadFiltri(new File("Filtri.xml"));
        
        System.out.println(e.checkAND("s1", "10000000"));
        System.out.println(e.checkOR("sourceType", "tower"));
        
    }
    
}

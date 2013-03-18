/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuratore;

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
        
        
        v1.impostaVincolo ("Accelerazione","<","1000");
        v2.impostaVincolo ("Accelerazione",">","50");
        v3.impostaVincolo ("Accelerazione","!=","500");
        v4.impostaVincolo ("Accelerazione",">","0");
         
        HashMap<String,Vincolo> prop = new HashMap<>();
        prop.put("regola1", v1);
        prop.put("regola2", v2);
        prop.put("regola3", v3);
        prop.put("regola4", v4);
        
        ConfFiltri e = new ConfFiltri(prop);
        
        e.saveFiltri(new File("Filtri.xml"));
        
        e.regole=null; //resetto
        
        e.loadFiltri(new File("Filtri.xml"));
        
        System.out.println(e.checkAll("Accelerazione", "500"));
        
        
    }
    
}

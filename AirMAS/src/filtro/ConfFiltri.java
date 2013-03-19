/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import filtro.Vincolo;
import blocco.Evento;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seby
 */
public class ConfFiltri extends Evento{
    HashMap<String,Vincolo> regole = new HashMap<>();
    
    public ConfFiltri(HashMap s) {
        regole = s;
    }
    
    public ConfFiltri(File f) {
        loadFiltri(f);
    }
    
    public ConfFiltri() {
        this.loadFiltri(new File("Filtri.xml"));
    }
    
    public Boolean checkOR(String id, String attuale){
        for (String nomeRegola : regole.keySet()){
            if (regole.get(nomeRegola).getId().equals(id)){
                if(regole.get(nomeRegola).check(id, attuale)){
                   return true;
                };
            }
        }
        return false;
    }
    
    public Boolean checkAND(String id, String attuale){
        for (String nomeRegola : regole.keySet()){
            if (regole.get(nomeRegola).getId().equals(id)){
                if(!regole.get(nomeRegola).check(id, attuale)){
                   return false;
                };
            }
        }
        return true;
    }
    
    public void loadFiltri(File f){
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(f))) {
            regole = (HashMap<String,Vincolo>)decoder.readObject();
        } catch (Exception ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveFiltri(File f){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
         try (XMLEncoder encoder = new XMLEncoder(baos)) {
            encoder.writeObject(regole);
         }
            
         catch (Exception ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
             FileOutputStream prova = new FileOutputStream(f);
             PrintStream scrivi = new PrintStream(prova);
             scrivi.println(baos.toString());
             
         }
         catch (Exception e)
         {
             System.out.println("Errore: " + e);
         }
    }
    
}

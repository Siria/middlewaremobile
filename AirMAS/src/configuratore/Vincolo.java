/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuratore;

import blocco.Evento;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seby
 */
public class Vincolo implements Serializable{
    public String id;
    public String type; // i possibili tipi di controllo ">","<","==","!="
    public String riferimento;

    public Vincolo() {
        id="";
        type=""; // i possibili tipi di controllo ">","<","==","!="
        riferimento="";
    }

    
    
    public void impostaVincolo(String id, String type, String riferimento) {
        this.id = id;
        this.type = type;
        this.riferimento = riferimento;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRiferimento() {
        return riferimento;
    }

    public void setRiferimento(String riferimento) {
        this.riferimento = riferimento;
    }
    
    public Boolean check(String id, String attuale){
        if (this.id.equals(id)){
            switch (type){
                case "<":
                    return (Integer.parseInt(attuale) < Integer.parseInt(riferimento));
                case ">":
                    return (Integer.parseInt(attuale) > Integer.parseInt(riferimento));
                case "==":
                    return (attuale.equals(riferimento));
                case "!=":
                    return (!attuale.equals(riferimento));
            }
        }
        return false;
    }
    
    public void saveVincolo(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
         try (XMLEncoder encoder = new XMLEncoder(baos)) {
            encoder.writeObject(this);
         }
            
         catch (Exception ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(baos.toString());
        
       
    }
}

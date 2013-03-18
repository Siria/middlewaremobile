/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author alessandra
 */
public class Evento implements Serializable{
    private HashMap data = new HashMap();
    
    
    public Evento(){}
    
    public Evento(String s){
        try (XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(s.getBytes("UTF-8")))) {
            data = (HashMap)decoder.readObject();
        } catch (Exception ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Evento(HashMap s){
        this.data = s;
    }
    
    public Object get(Object object) {
        return data.get(object);
    }

    public Object put(Object object, Object value) {
        return data.put(object, value);
    }
    
    @Override
    public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
         try (XMLEncoder encoder = new XMLEncoder(baos)) {
            encoder.writeObject(data);
         }
            
         catch (Exception ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return baos.toString();
    }
    
}
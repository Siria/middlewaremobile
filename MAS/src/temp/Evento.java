/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author alessandra
 */
public class Evento implements Serializable{
    private HashMap<String,String> data = new HashMap<>();
    
    //String context;
    //String tipo;
    //String content;

    
    public Evento(String s) {
        String[] dati = s.toLowerCase().split(";");
        for (String dato : dati){
            data.put(dato.split(":")[0],dato.split(":")[1]);
        }
    }

    public String get(String object) {
        return data.get(object);
    }

    public String put(String object, String value) {
        return data.put(object.toLowerCase(), value);
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Entry<String,String> s : data.entrySet()){
            out = out.append(s.getKey()).append(":").append(s.getValue()).append( ";");
        }
        return out.toString();
    }
    
}

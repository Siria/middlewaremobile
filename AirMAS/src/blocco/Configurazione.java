/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Seby
 */
public class Configurazione {
    HashMap<String,LinkedList<String>> data = new HashMap<>();
    HashMap<String,LinkedList<String>> dataB = new HashMap<>();
    
    public void put(String Key, String Object){
        if (data.containsKey(Key)){
            data.get(Key).add(Object);
            dataB.get(Key).add(Object);
            
        } else {
             data.put(Key, new LinkedList<String>());
             data.get(Key).add(Object);
             dataB.put(Key, new LinkedList<String>());
             dataB.get(Key).add(Object);
        }
    }
    
    public Object get(String Key){
        if (data.get(Key).size() == 0){
            data.put(Key,dataB.get(Key));
        }
        return data.get(Key).poll();
    }
    
    public Object getS(String Key){
        return data.get(Key);
    }

    public Configurazione() {
    }
    
    public Configurazione(String Key,String Object){
        this.put(Key, Object);
    }
    
}

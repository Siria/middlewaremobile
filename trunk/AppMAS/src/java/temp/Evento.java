/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.io.Serializable;

/**
 *
 * @author alessandra
 */
public class Evento implements Serializable{
    String context;
    String tipo;
    String contenuto;

    public Evento(String context, String tipo, String contenuto) {
        this.context = context;
        this.tipo = tipo;
        this.contenuto = contenuto;
    }

    public Evento() {
    }

    public String getContext() {
        return context;
    }

    public String getTipo() {
        return tipo;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
    


    
    
    
    
}

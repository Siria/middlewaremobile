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
    String content;

    public Evento(String context, String tipo, String contenuto) {
        this.context = context;
        this.tipo = tipo;
        this.content = content;
    }

    public Evento() {
    }

    public String getContext() {
        return context;
    }

    public String getTipo() {
        return tipo;
    }

    public String getContent() {
        return content;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setContent(String content) {
        this.content = content;
    }
    


    
    
    
    
}

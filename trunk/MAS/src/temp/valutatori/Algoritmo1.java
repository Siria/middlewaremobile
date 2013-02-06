/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import temp.Evento;
import temp.proxy.AlgoritmoProxy;

/**
 *
 * @author 
 */

public class Algoritmo1 implements AlgoritmoProxy{
    
    @Override
    public Object valuta(Object messaggio){
        messaggio = (messaggio + " Algo1... ");
        System.out.println(messaggio);
        Evento e = (Evento) messaggio;
        /*switch (e.getTipo()) {
            case "Start" : 
            case "Data" :  
            case "Control" :
                System.out.println("invio blocco filtro-tipo");
            case "Allarme" : 
                System.out.println("invio blocco filtro-tipo");
                break;
            default: break;
             }
        switch (e.getContext()) {
            case "Position" : 
            case "My" : 
            case "Other" : 
            case "Modify" :
                System.out.println("invio blocco filtro-context");
                     break;
            default: break;
             }
                switch (e.getContenuto()) {
            case "bu" : 
                System.out.println("ora vediamo...........");
                System.out.println("invio blocco filtro-content");
                     break;
            default: break;
             }*/
        if (e != null) {
            parametro = parametro.substring(nome.length() + 1);
            String[] valori = parametro.split(",");
            for (String valore : valori) {
                switch (valore) {
                    case ("rest"):
                        break;
                    case ("jms"):
                        break;
                    case ("socket"):
                        break;
                    case ("soap"):
                        break;
                }
            }
        }
        return messaggio;
    }
	
}
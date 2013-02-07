/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.valutatori;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import temp.Blocco;
import temp.Evento;
import temp.proxy.AlgoritmoProxy;
import temp.proxy.ProxyTarget;
import temp.proxy.TrasmettitoreProxy;

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
        return messaggio;
    }
    
   public static void ricevi_disp(Object messaggio) {
       Evento e = (Evento) messaggio;
        String[] parametri = e.getContent().toLowerCase().split(";");
        for (String parametro : parametri) {
            String type = parametro.split(":")[0];
            System.out.println("tipo " + type);

            if (type.equals("posizione") ) {
                parametro = parametro.substring(type.length() + 1);
                String[] contexts = parametro.split(":");
                for (String context : contexts) {

                    if (isPosition(context)) {
                        System.out.println("contesto " + context);
                        parametro = parametro.substring(context.length() + 1);
                        String[] contents = parametro.split(",");
                        for (String content : contents) {

                            if (content.isEmpty()) {
                                System.out.println("non ho valori");
                            } else {
                                System.out.println("contenuto " + content);
                                System.out.println("Invio al filtro");
                            }
                        }

                    }
                }

            } else if (type.equals("alarm")) {
                parametro = parametro.substring(type.length() + 1);
                String[] contexts = parametro.split(":");
                for (String context : contexts) {
                    
                    if (isAlarm(context)) {
                        System.out.println("contesto " + context);
                        parametro = parametro.substring(context.length() + 1);
                        String[] contents = parametro.split(",");
                        for (String content : contents) {

                            if (content.isEmpty()) {
                                System.out.println("non ho valori");
                            } else {
                                System.out.println("contenuto " + content);
                                System.out.println("Invio al filtro");
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static boolean isPosition(String context) {
        if (context.equals("start") || context.equals("data") || context.equals("control")) {
            return true;
        }
        return false;
    }
    private static boolean isAlarm(String context) {
        if (context.equals("my") || context.equals("other") || context.equals("modify")) {
            return true;
        }
        return false;
    }
            
    }
	

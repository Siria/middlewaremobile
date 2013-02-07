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
        messaggio = (messaggio + " Algoritmo dispatch... ");
        System.out.println(messaggio);
        Evento e = (Evento) messaggio;
        return ricevi_disp(e);
    }
    
   public static Object ricevi_disp(Evento e) {
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
                                return e;
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
                                return e;
                            }
                        }
                    }
                }
            }
        }
        return null;
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
	

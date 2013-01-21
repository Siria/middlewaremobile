/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import temp.proxy.ProxyTarget;
import temp.proxy.RicevitoreProxy;
import temp.proxy.TrasmettitoreProxy;
import temp.queue.Monitor;
import temp.ricevitori.SOAP.RicevitoreSOAP;
import temp.ricevitori.Socket.RicevitoreSocket;
import temp.trasmettitori.REST.TrasmettitoreREST;
import temp.trasmettitori.SOAP.TrasmettitoreSOAP;
import temp.trasmettitori.Socket.TrasmettitoreSocket;
import temp.valutatori.*;

/**
 *
 * @author Seby
 */
public class Starter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Monitor monitor4 = new Monitor();
            Blocco asc4 = new Blocco(monitor4);
            //asc4.setTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(monitor4,new TrasmettitoreREST(monitor4,"http://localhost:17781/WS/REST")));
            HashMap conf4 = new HashMap();
            conf4.put("portaAscoltoEsterna", 17781);
            asc4.ricevitori.add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSOAP(monitor4,conf4)));
            asc4.setAlgoritmo(new Algoritmo4());
            Thread th4 = new Thread (asc4);
            th4.start();
            
            Thread.sleep(1000);
            
            Monitor monitor3 = new Monitor();
            Blocco asc3 = new Blocco(monitor3);
            asc3.setTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreSocket(monitor3,new Socket("localhost",17781))));
            HashMap conf3 = new HashMap();
            conf3.put("portaAscoltoEsterna", 17778);
            asc3.ricevitori.add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSocket(monitor3,conf3)));
            HashMap conf3a = new HashMap();
            conf3a.put("portaAscoltoEsterna", 17780);
            asc3.ricevitori.add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSOAP(monitor3,conf3a)));
            asc3.setAlgoritmo(new Algoritmo3());
            Thread th3 = new Thread (asc3);
            th3.start();
            
            Thread.sleep(1000);
            
            Monitor monitor2 = new Monitor();
            Blocco asc2 = new Blocco(monitor2);
            asc2.setTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreSOAP(new URL("http://localhost:17780/WS/SOAP?wsdl"))));
            HashMap conf2 = new HashMap();
            conf2.put("portaAscoltoEsterna", 17776);
            asc2.ricevitori.add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSocket(monitor2,conf2)));
            asc2.setAlgoritmo(new Algoritmo2());
            Thread th2 = new Thread (asc2);
            th2.start();
            
            Thread.sleep(1000);
            
            Monitor monitor1 = new Monitor();
            Blocco asc1 = new Blocco(monitor1);
            asc1.setTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreSocket(monitor1,new Socket("localhost",17776))));
            HashMap conf1 = new HashMap();
            conf1.put("portaAscoltoEsterna", 17774);
            asc1.ricevitori.add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSocket(monitor1,conf1)));
            HashMap conf1a = new HashMap();
            conf1a.put("portaAscoltoEsterna", 17773);
            conf1a.put("portaAscoltoInterna", 17775);
            asc1.ricevitori.add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSOAP(monitor1,conf1a)));
            asc1.setAlgoritmo(new Algoritmo1());
            Thread th1 = new Thread (asc1);
            th1.start();
            
            Thread.sleep(1000);
            
            System.out.println("\t ----------------------------------------");
            System.out.println("\t --- Middleware for Autonomic Systems ---");
            System.out.println("\t ----------------------------------------");
            System.out.println("\n\nTutto OK: avviare un Client...");
            
        } catch (Exception ex){
                ex.printStackTrace();
        }        
    }
}

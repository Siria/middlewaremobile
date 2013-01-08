/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import temp.ricevitori.REST.RicevitoreREST;
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
            
            Blocco asc4 = new Blocco();
            asc4.setPortaAscolto(17782);
            asc4.setTrasmettitore(new TrasmettitoreREST());
            HashMap conf4 = new HashMap();
            conf4.put("portaAscoltoEsterna", 17781);
            conf4.put("portaAscoltoInterna", 17782);        
            asc4.ricevitori.add(new RicevitoreREST(conf4));
            asc4.setAlgoritmo(new Algoritmo4());
            Thread th4 = new Thread (asc4);
            th4.start();
            
            Thread.sleep(1000);
            
            Blocco asc3 = new Blocco();
            asc3.setPortaAscolto(17779);
            //asc3.setTrasmettitore(new TrasmettitoreSocket(new Socket("localhost",7780)));
            HashMap conf3 = new HashMap();
            conf3.put("portaAscoltoEsterna", 17778);
            conf3.put("portaAscoltoInterna", 17779);           
            asc3.ricevitori.add(new RicevitoreSocket(conf3));
            HashMap conf3a = new HashMap();
            conf3a.put("portaAscoltoEsterna", 17780);
            conf3a.put("portaAscoltoInterna", 17779);
            asc3.ricevitori.add(new RicevitoreSOAP(conf3a));
            asc3.setAlgoritmo(new Algoritmo3());
            Thread th3 = new Thread (asc3);
            th3.start();
            
            Thread.sleep(1000);
            
            Blocco asc2 = new Blocco();
            asc2.setPortaAscolto(17777);
            asc2.setTrasmettitore(new TrasmettitoreSOAP(new URL("http://localhost:17780/WS/SOAP?wsdl")));
            HashMap conf2 = new HashMap();
            conf2.put("portaAscoltoEsterna", 17776);
            conf2.put("portaAscoltoInterna", 17777);        
            asc2.ricevitori.add(new RicevitoreSocket(conf2));
            asc2.setAlgoritmo(new Algoritmo2());
            Thread th2 = new Thread (asc2);
            th2.start();
            
            Thread.sleep(1000);
            
            
            Blocco asc1 = new Blocco();
            asc1.setPortaAscolto(17775);
            asc1.setTrasmettitore(new TrasmettitoreSocket(new Socket("localhost",17776)));
            HashMap conf1 = new HashMap();
            conf1.put("portaAscoltoEsterna", 17774);
            conf1.put("portaAscoltoInterna", 17775);
            asc1.ricevitori.add(new RicevitoreSocket(conf1));
            HashMap conf1a = new HashMap();
            conf1a.put("portaAscoltoEsterna", 17773);
            conf1a.put("portaAscoltoInterna", 17775);
            asc1.ricevitori.add(new RicevitoreSOAP(conf1a));
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import temp.proxy.AlgoritmoProxy;
import temp.proxy.ProxyTarget;
import temp.proxy.RicevitoreProxy;
import temp.proxy.TrasmettitoreProxy;
import temp.ricevitori.File.RicevitoreFile;
import temp.ricevitori.JMS.RicevitoreJMS;
import temp.ricevitori.REST.RicevitoreREST;
import temp.ricevitori.SM.RicevitoreSM;
import temp.ricevitori.SOAP.RicevitoreSOAP;
import temp.ricevitori.Socket.RicevitoreSocket;
import temp.trasmettitori.File.TrasmettitoreFile;
import temp.trasmettitori.JMS.TrasmettitoreJMS;
import temp.trasmettitori.REST.TrasmettitoreREST;
import temp.trasmettitori.SM.TrasmettitoreSM;
import temp.trasmettitori.SOAP.TrasmettitoreSOAP;
import temp.trasmettitori.Socket.TrasmettitoreSocket;

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
            
            Blocco asc7 = new Blocco();
            asc7.getConf().put("sharedIngresso", "shared.dat");
            asc7.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSM()));
            asc7.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new Algoritmo6()));
            Thread th7 = new Thread (asc7);
            th7.start();
            
            Thread.sleep(1000);
            
            Blocco asc6 = new Blocco();
            asc6.getConf().put("sharedUscita", "shared.dat");
            asc6.getConf().put("fileIngresso", "file.dat");
            asc6.getTrasmettitori().add((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreSM()));
            asc6.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreFile()));
            asc6.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new Algoritmo6()));
            Thread th6 = new Thread (asc6);
            th6.start();
            
            Thread.sleep(1000);
            
            Blocco asc5 = new Blocco();
            asc5.getConf().put("fileUscita", "file.dat");
            asc5.getConf().put("jmsIngresso", "myDestination");
            asc5.getTrasmettitori().add((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreFile()));
            asc5.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreJMS()));
            asc5.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new Algoritmo5()));
            Thread th5 = new Thread (asc5);
            th5.start();
            
            Thread.sleep(1000);
            
            Blocco asc4 = new Blocco();
            asc4.getConf().put("restIngresso", "http://localhost:9998/WS/REST/");
            asc4.getConf().put("jmsUscita", "myDestination");
            asc4.getTrasmettitori().add((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreJMS()));
            asc4.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreREST()));
            asc4.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new Algoritmo4()));
            Thread th4 = new Thread (asc4);
            th4.start();
            
            Thread.sleep(1000);
            
            Blocco asc3 = new Blocco();
            asc3.getConf().put("socketIngresso","17782");
            asc3.getConf().put("restUscita", "http://localhost:9998/WS/REST/");
            asc3.getTrasmettitori().add((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreREST()));
            asc3.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSocket()));
            asc3.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new Algoritmo3()));
            Thread th3 = new Thread (asc3);
            th3.start();
            
            Thread.sleep(1000);
            
            Blocco asc2 = new Blocco();
            asc2.getConf().put("socketUscita","localhost:17782");
            asc2.getConf().put("soapIngresso","http://localhost:17779/WS/SOAP/?wsdl");
            asc2.getTrasmettitori().add((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreSocket()));
            asc2.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSOAP()));
            asc2.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new Algoritmo2()));
            Thread th2 = new Thread (asc2);
            th2.start();
            
            Thread.sleep(1000);
            
            Blocco asc1 = new Blocco();
            asc1.getConf().put("soapUscita","http://localhost:17779/WS/SOAP/?wsdl");
            asc1.getConf().put("soapIngresso","http://localhost:17780/WS/SOAP/?wsdl");
            asc1.getTrasmettitori().add((TrasmettitoreProxy)ProxyTarget.createProxy(new TrasmettitoreSOAP()));
            asc1.getRicevitori().add((RicevitoreProxy)ProxyTarget.createProxy(new RicevitoreSOAP()));
            asc1.setAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(new AlgoRicevitore()));
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import blocco.Blocco;
import blocco.BloccoBidirezionale;
import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.ProxyTarget;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import java.util.Scanner;

/**
 *
 * @author Seby
 */
public class BackUp2 {
    
     BloccoBidirezionale asc1;   
    
     public static void main(String[] args) throws Exception {
         try {
             
            BloccoBidirezionale asc1 = new BloccoBidirezionale();
            asc1.getConf().put("socketUscita","localhost:60002");
            asc1.getConf().put("socketIngresso","60020");
            asc1.getTrasmettitori().add(new AdapterTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(Class.forName("trasmissione.TrasmettitoreSocket").newInstance())));
            asc1.getRicevitori().add(new AdapterRicevitore((RicevitoreProxy)ProxyTarget.createProxy(Class.forName("ricezione.RicevitoreSocket").newInstance())));
            asc1.setAlgoritmo(new AdapterAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(Class.forName("backup.Primary").newInstance())));
            asc1.configuraRicevitori();
            
            BloccoBidirezionale asc2 = new BloccoBidirezionale();
            asc2.getConf().put("socketUscita","localhost:60012");
            asc2.getConf().put("socketIngresso","60021");
            asc2.getTrasmettitori().add(new AdapterTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(Class.forName("trasmissione.TrasmettitoreSocket").newInstance())));
            asc2.getRicevitori().add(new AdapterRicevitore((RicevitoreProxy)ProxyTarget.createProxy(Class.forName("ricezione.RicevitoreSocket").newInstance())));
            asc2.setAlgoritmo(new AdapterAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(Class.forName("backup.Primary").newInstance())));
            asc2.configuraRicevitori();
            
            //creare una funziona che incapsula tutti gli inviaconack ne fa il conto e se è
            //uguale al numero di backupSecondari ok else fail
            
            while(true){
            Scanner sc = new Scanner(System.in);

            System.out.print("Premi invio per continuare. ");
            sc.nextLine();
            System.out.println(asc1.inviaConAck("marameo ^_^", 1000));
            System.out.println(asc2.inviaConAck("marameo ^_^", 1000));
            }
            
         } catch(Exception e){
             e.printStackTrace();
         }  
     }
     
     
     
     
}

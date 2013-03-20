/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import blocco.BloccoBidirezionale;
import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.ProxyTarget;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import java.io.Console;
import java.util.Scanner;



/**
 *
 * @author Seby
 */
public class TestBidi2 extends TestBidi1{

    static String add1 = "localhost:60001";
    static String add2 = "60000";
    
    public static void main(String[] args) {
        // TODO code application logic here
        try{
        BloccoBidirezionale a = new BloccoBidirezionale();
        a.setTrasmettitore(new AdapterTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(Class.forName("trasmissione.TrasmettitoreSocket").newInstance())));        
        a.setRicevitore(new AdapterRicevitore((RicevitoreProxy)ProxyTarget.createProxy(Class.forName("ricezione.RicevitoreSocket").newInstance())));                                        
        a.getConf().put("socketUscita", add1);
        a.getConf().put("socketIngresso", add2);
        a.setAlgoritmo(new AdapterAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(Class.forName("valutatori.AlgoBackup").newInstance())));
        
        Thread th = new Thread(a);
        th.start();
        
            while(true){
            Scanner sc = new Scanner(System.in);
            System.out.print("Premi invio per continuare. ");
            sc.nextLine();
           
            
            System.out.println(a.inviaConAck("prepareToCommit", 1000));
        
            }

        
        
        } catch (Exception ex){
        ex.printStackTrace();
        }
}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedList;
import temp.ricevitori.SM.RicevitoreSM;
import temp.trasmettitori.SM.TrasmettitoreSM;

/**
 *
 * @author alessandra
 */
public class ClientSM {

    public static Object synchObject;
    public  static LinkedList<Object> coda ;
 
    public static void main(String[] args) {
        coda = new LinkedList<>();
	new TrasmettitoreSM().start();
	new RicevitoreSM().start();
    }
}
    


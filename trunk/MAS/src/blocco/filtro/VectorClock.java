/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blocco.filtro;

/**
 *
 * @author alessandra
 */

import java.util.Iterator;
import java.util.Vector;


public class VectorClock {
       
        private Vector<Integer> clocks= null;
        private int localID = 0;

        public VectorClock(int clientID){
                clocks= new Vector<Integer>();
               
                for (int i=0; i < clientID; i++)
                        clocks.add(i, 0);
               
                localID = clientID-1;
        }
       
        public Vector<Integer> getVector(){
                return clocks;
        }
       
        public void increment(){
                clocks.set(localID, clocks.get(localID) + 1);
        }
       
        public void     update(VectorClock c){
               
                Iterator<Integer> j = c.getVector().iterator();
               
                if (clocks.size() < c.getVector().size())
                        clocks.setSize(c.getVector().size());
               
                for (int i = 0;j.hasNext(); i++){
                        int next = j.next();
                        if ( i != localID)
                                if (clocks.get(i) == null || clocks.get(i) < next)
                                        clocks.set(i, next);
                }
               
        }
       
        public void extend(){
                clocks.add(0);
        }
       
        public void extend(int e){
                clocks.add(e);
        }
       

        public boolean lessThan(VectorClock c){
               
                Iterator<Integer> i = clocks.iterator();
                Iterator<Integer> j = c.getVector().iterator();
               
                while (i.hasNext() && j.hasNext()){
                        if ( i.next() > j.next())
                                return false;
                }
                return true;
        }
       

        public boolean equals(VectorClock c){
               
                Iterator<Integer> i = clocks.iterator();
                Iterator<Integer> j = c.getVector().iterator();
               
                if (clocks.size() != c.getVector().size())
                        return false;
               
                while (i.hasNext() && j.hasNext()){
                        if ( i.next() != j.next())
                                return false;
                }
               
                return true;
               
        }
       
        public String toString(){
                return clocks.toString();
               
        }
}


    
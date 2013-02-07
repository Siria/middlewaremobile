/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;


/**
 *
 * @author alessandra
 */

public class VectorClock {
    public int [] v;
    int myId;
    int N;
    
    public VectorClock (int numProc, int id){
    
        myId = id;
        N = numProc;
        v = new int[numProc];
        for (int i = 0; i < N; i++)
            v[i] = 0;
        v[myId] = 1;
    }

    public void doAct(){
    
        v[myId]++;
    
    }
    
    public void sendAction(){
    
        v[myId]++;
        
    }
    
    //il parametro è il timestamp inviato
    public void receiveAction(int[] val){
    
        for (int i = 0; i < N; i++) {
            v[i] = Math.max(v[i], val[i]);
        }
        v[myId]++;
       
    }
    public int getValue(int i){
    
        return v[i];
    
    }




}
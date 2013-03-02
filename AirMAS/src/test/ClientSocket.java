package test;

import java.io.*;
import java.net.*;

public class ClientSocket {
	
	 public static void main(String[] args) throws Exception {
             while (true){
                invia("Inizio Socket...");
                Thread.sleep(1000);
             }
        
         }
	
	
	public static void invia(Object DATI){
		try{
                Socket SK = new Socket("localhost",17782);
		ObjectOutputStream OOS = new ObjectOutputStream(SK.getOutputStream());
		OOS.writeObject(DATI);
		OOS.flush();
                OOS.close();
		}catch (Exception e){e.printStackTrace();}
	}
	
	
		
}   


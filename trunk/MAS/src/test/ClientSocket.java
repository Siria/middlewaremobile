package test;

import java.io.*;
import java.net.*;

public class ClientSocket {
	
	 public static void main(String[] args) throws Exception {
        
            invia("Inizio Socket...");

        
         }
	
	
	public static void invia(Object DATI){
		try{
                Socket SK = new Socket("localhost",17774);
		ObjectOutputStream OOS = new ObjectOutputStream(SK.getOutputStream());
		OOS.writeObject(DATI);
		OOS.flush();
                OOS.close();
		}catch (Exception e){e.printStackTrace();}
	}
	
	
		
}   


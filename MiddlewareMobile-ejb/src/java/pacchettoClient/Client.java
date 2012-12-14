/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacchettoClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.ejb.Stateless;

/**
 *
 * @author alessandra
 */
@Stateless
public class Client implements ClientLocal {

    public Client() {
    }
    
    
    
 private static final String HOSTNAME = "192.168.0.1";
    /**
     *
     */
    public static final int PORT = 6767;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @param args
     */
 @Override
 public void businessMethod(String[] args) {
        
  String host ="";
  int port = PORT;
  String frase ="alessandra";
  String fraseModificata;
  
  if (args.length==0){
   System.out.println("Parametri di default.");
   System.out.println("Host : "+HOSTNAME+" Porta : "+PORT);
   host = HOSTNAME;
  }else{
   if (args.length==1){
    host = args[0];
   }
   if (args.length==2){
    host = args[0];
    port = new Integer(args[1]).intValue();
   }
  }
  // dichiaro un buffer Reader
  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
  
  try{
         try (Socket clientSocket = new Socket(host,port)) {
             System.out.println("Connessione accettata dal server ");
             // crea un outputStream connesso allo stram di output del socket
             DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
             
             // creo un buffer di lettura dal server
             BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             
             
             // prelevo la frase dallo standard input
             frase = inFromUser.readLine();
             
             // passo la frase al server
             outToServer.writeBytes(frase + '\n');
             
             // recupero la frase modificata dal server
             fraseModificata = inFromServer.readLine();
             
             System.out.println(fraseModificata);
         }
   
  }catch(UnknownHostException e){
   System.out.println("Host sconosciuto : "+e.getMessage());
  }catch(IOException e){
   System.out.println("Problema con il server : "+e.getMessage());
  }
    }

}

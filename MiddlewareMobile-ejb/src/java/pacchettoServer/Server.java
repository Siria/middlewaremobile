/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacchettoServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.ejb.Stateful;

/**
 *
 * @author alessandra
 */
@Stateful
public class Server implements ServerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")


    /**
     * @param args the command line arguments
     */
    
 public static final int PORT = 6767;

        public Server() {
        }
 
 
 
        /**
         *
         * @param args
         */
        
        @Override
        public void businessMethod(String[] args){
     
        // TODO code application logic here
    
  String fraseClient;
  String fraseStrategia;
  try{
   // creo il socket di benvenuto
   ServerSocket welcomeSocket = new ServerSocket(PORT);
   
   // ciclo infinitamente (server)
   while (true){
    
    // aspetto il socket per il contatto con il client
    Socket connectionSocket = welcomeSocket.accept();
    
    System.out.println("Accettata connessione socket");
    System.out.println("Il client è connesso dall'ip : "+connectionSocket.getRemoteSocketAddress());
    
    //creo uno stream di input attaccato al socket
    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    
    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    
    // leggo la frase del client
    fraseClient = inFromClient.readLine();
    
    // trasformo la frase in maiuscolo
    fraseStrategia = fraseClient.toUpperCase();
    
    System.out.println("frase da inviare : "+fraseStrategia);
    
    outToClient.writeBytes(fraseStrategia+'\n');
    
   }
  }catch (IOException e) {
   System.out.println("Errore :"+e.getMessage());
  }
 }
    }



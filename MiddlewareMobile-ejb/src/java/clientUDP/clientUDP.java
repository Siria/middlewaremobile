/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientUDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.ejb.Stateful;

/**
 *
 * @author alessandra
 */
@Stateful
public class clientUDP implements clientUDPLocal {
    
     public static final String HOSTNAME = "localhost";
     public static final int PORT = 6769;
     
     

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void businessMethod(String[] args) {
    
    String host ="";
  int port = PORT;
  
  if (args.length==0){
   System.out.println("Parametri di default.");
   System.out.println("Host : "+HOSTNAME);
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
  
  // buffer di input del client
  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
  
  try{
         try (DatagramSocket clientSocket = new DatagramSocket()) {
             InetAddress IPAddress = InetAddress.getByName(host);
             
             byte[] sendData = new byte[1024];
             byte[] receiveData = new byte[1024];
             
             String frase = inFromUser.readLine();
             
             sendData = frase.getBytes();
             
             // creo un datagramma con i dati da inviare, lunghezza, ip e porta
             DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
             
             // invio il datagramma al server
             clientSocket.send(sendPacket);
             
             // credo un pacchetto 
             DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
             
             clientSocket.receive(receivePacket);
             
             // frase ricevuta dal server
             String fraseModificata = new String(receivePacket.getData());
             
             System.out.println("Frase ricevuta : "+fraseModificata);
         }
  }catch(SocketException e){
   System.out.println("Problemi con il socket : "+e.getMessage());
  }catch(UnknownHostException e){
   System.out.println("Host sconosciuto : "+e.getMessage());
  }catch (IOException e){
   System.out.println("I/O exception : "+e.getMessage());
  }
 }
    }



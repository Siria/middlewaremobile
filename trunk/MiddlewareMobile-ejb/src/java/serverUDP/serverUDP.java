/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.ejb.Stateful;

/**
 *
 * @author alessandra
 */
@Stateful
public class serverUDP implements serverUDPLocal {
    
     public static final int PORT = 6769;
     
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void businessMethod(String[] args) {
        
        try {
   
   // creo il socket
   DatagramSocket serverSocket = new DatagramSocket(PORT);
   
   byte[] sendData = new byte[1024];
   byte[] receiveData = new byte[1024];
   
   while (true){
    
    // istanzio il datagramma in input
    DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
    
    // ricevo il datagramma dal socket
    serverSocket.receive(receivePacket);
    
    // recupero la frase
    String frase = new String(receivePacket.getData());
    
    // indirizzo ip
    InetAddress iPAddress = receivePacket.getAddress();
    
    // porta
    int port = receivePacket.getPort();
    
    // modifico la porta
    String fraseModificata = frase.toUpperCase(); //frase.strategia
    
    // trasformo in byte 
    sendData = fraseModificata.getBytes();
    
    // creo un datagramma per inviare al client
    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,iPAddress,port);
    
    //scrivo il datagramma sul socket
    serverSocket.send(sendPacket); 
   } 
  }catch(SocketException e){
   System.out.println("Problemi nell'apertura del socket "+e.getMessage());
  }catch(IOException e){
   System.out.println("Problemi di I/O : "+e.getMessage());
  }
 } 
 
}
    



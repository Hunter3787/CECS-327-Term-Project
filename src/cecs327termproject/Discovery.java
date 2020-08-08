package cecs327termproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  Discovery class, implemented as a singleton, that will create 
*  a separate thread that searches for local nodes.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class Discovery extends Thread {
    private static Discovery discoveryInstance;
    private DatagramSocket socket;
    
    public Discovery(){ 
    }
    
    public static Discovery getInstances(){
        return discoveryInstance;
    }
    
    @Override
    public void run(){
        try{
            socket = new DatagramSocket(10000, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);
            
            while(true){
                //Packet receiver setup.
                byte[] receive = new byte[16384];
                DatagramPacket incomingPacket = new DatagramPacket(receive, receive.length);
                socket.receive(incomingPacket);
                
                String verify = new String(incomingPacket.getData()).trim();
                if(verify.equals("REQUEST")){
                    byte[] send = "RESPONSE".getBytes();
                    
                    DatagramPacket outgoingPacket 
                            = new DatagramPacket(send, 
                                    send.length, 
                                    incomingPacket.getAddress(), 
                                    incomingPacket.getPort());
                    socket.send(outgoingPacket);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Discovery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Discovery.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}

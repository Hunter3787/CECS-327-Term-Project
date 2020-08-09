package cecs327termproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  Broadcaster class, implemented as a singleton, that will create 
*  a separate thread that searches for local nodes.
*  Largely influenced by "baeldung.com/upd-in-java".
*  After days of struggling with broadcasting to open TCP links.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class Broadcaster{
    private static Broadcaster discoveryInstance = null;
    private DatagramSocket socket;
    
    public Broadcaster(){ 
    }
    
    /**
     * Singleton instance call. 
     * @return 
     */
    public static Broadcaster getInstance(){
        if(discoveryInstance == null)
            discoveryInstance = new Broadcaster();
        
        return discoveryInstance;
    }
    
    public void broadcast(){
        try{
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            //Packet receiver setup.
            byte[] buffer = new byte[16384];
            DatagramPacket outgoingPacket = new DatagramPacket(buffer, buffer.length);
            socket.send(outgoingPacket);

            

            
        } catch (SocketException ex) {
            System.out.println("Broadcast: Socket Exception.");
        } catch (IOException ex) {
            System.out.println("Broadcast: IOException.");
        }  
    }
}
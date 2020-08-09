package cecs327termproject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
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
    private static Broadcaster broadcasterInstance = null;
    private DatagramSocket socket;
    
    public Broadcaster(){ 
    }
    
    /**
     * Singleton instance call. 
     * Not really necessary, but thought I'd try it out.
     * @return 
     */
    public static Broadcaster getInstance(){
        //If instance of Broadcaster doesn't exist, createa a new one.
        if(broadcasterInstance == null)
            broadcasterInstance = new Broadcaster();
        
        //Return broadcasterInstance.
        return broadcasterInstance;
    }
    
    public void broadcast(){
        try{
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            //Create packet and pass local IP to other machines.
            byte[] buffer = InetAddress.getLocalHost().getAddress();
            DatagramPacket outgoingPacket 
                    = new DatagramPacket(buffer, buffer.length, 
                            InetAddress.getByName("255.255.255.255"), 
                            Main.DEFAULT_PORT);
            socket.send(outgoingPacket);

        } catch (SocketException ex) {
            System.out.println("Broadcast: Socket Exception.");
        } catch (IOException ex) {
            System.out.println("Broadcast: IOException.");
        } finally {
            socket.close();
        }
    }
}
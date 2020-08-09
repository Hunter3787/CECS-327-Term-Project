package cecs327termproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  BroadcastListener class, to handle inbound UDP communication.
*  Largely influenced by "baeldung.com/upd-in-java".
*  After days of struggling with broadcasting to open TCP links.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class BroadcastListener implements Runnable {
    
    /** Local DatagramSocket for UDP communication. */
    private DatagramSocket socket = null;
    /** Boolean to end the while loop in the run. */
    private boolean running;
    /** A buffer of excessive size to carry IP and maybe other information. */
    private byte[] buffer = new byte[16384];
    
    /**
     * Constructor that throws an IO Exception if the sockets fail to
     * be created.
     * @throws IOException 
     */
    public BroadcastListener() throws IOException {
        socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(10000));
    }
    
    /**
     * Runnable method that will be performed on initialization 
     * in the main method, functioning as the server for UDP calls.
     */
    @Override
    public void run(){
        running = true;
        
        while (running){
            try{
                //Builds packet to receive data.
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                //Asks to receive the data from the socket into the packet.
                socket.receive(packet);
                //Collects the InetAdress from the packet.
                InetAddress address = packet.getAddress();
                //Collects the port from the packet. 
                int port = packet.getPort();
                packet = new DatagramPacket(buffer, buffer.length, address, port);

                
            } catch (IOException ex) {
                System.out.println("BroadcastListener: Socket failed to receive.");
                running = false;
            } 
        }
        socket.close();
    }
    
}

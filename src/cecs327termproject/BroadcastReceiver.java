package cecs327termproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  BroadcastReceiver class, to handle inbound UDP communication.
*  Largely influenced by "baeldung.com/udp-in-java".
*  After days of struggling with broadcasting to open TCP links.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class BroadcastReceiver implements Runnable {
    
    /** Local DatagramSocket for UDP communication. */
    private DatagramSocket socket = null;
    /** Boolean to end the while loop in the run. */
    private boolean running;
    /** A buffer of excessive size to carry IP and maybe other information. */
    private byte[] buffer = new byte[32];
    /** ArrayList of all found InetAddresses. */
    private ArrayList<InetAddress> addresses;
    
    /**
     * Constructor that throws an IO Exception if the sockets fail to
     * be created.
     * @throws IOException 
     */
    public BroadcastReceiver() {
        try {
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(10000));
        } catch (SocketException ex) {
           System.out.println("BroadcastReceiver: Socket Failure");
        }
        addresses = new ArrayList<>();
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
                //Test line to verify incoming packet.
                System.out.println("InetAddress Received" + address);
                //If found address is not part of the collected addresses
                //Add it to the list.
                if(!addresses.contains(address))
                    addresses.add(address);
                
            } catch (IOException ex) {
                System.out.println("BroadcastListener: Socket failed to receive.");
                running = false;
            } 
        }
        socket.close();
    }
    
    /**
     * Return ArrayList of InetAddreses that have been received.
     * @return 
     */
    public ArrayList<InetAddress> getAddresses(){
        return addresses;
    }
    
    /**
     * Simple helper to end the loop in the main run method.
     */
    public void stopReceiver(){
        socket.close();
        running = false;
    }
}

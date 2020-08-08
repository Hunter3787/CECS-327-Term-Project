package cecs327termproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
*  Peer class.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class Peer extends Thread{
    /** Default port number. */
    private static final int DEFAULT_PORT = 10100;
    /** Server socket instance to be connected to from remote client. */
    private ServerSocket socket;
    /** String value of IP address. */
    private String ip;
    /** Peer port value as integer. */
    private int port;
    
    /** 
     * Constructor that takes only a string IP value.
     * @param ip String type, IP address. 
     */
    public Peer(String ip){
        this.ip = ip;
        port = DEFAULT_PORT;
    }
    
    /**
     * Constructor that takes in IP address and Port number.
     * @param ip
     * @param port 
     */
    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * 
     */
    @Override
    public void run(){
        
        
    }
    
    /**
     * Getter for IP address.
     * @return String type, IP address used by peer.
     */
    public String getIP(){ 
        return ip; 
    }
    
    /**
     * Getter for port number.
     * @return Integer type, Port number used by peer.
     */
    public int getPort(){
        return port;
    }
    
    /**
     * Setter for IP address.
     * @param ip String type, IP address used by peer.
     */
    public void setIP(String ip){ 
        this.ip = ip; 
    }
    
    /**
     * Setter for port number.
     * @param port Integer type, port number used by peer.
     */
    public void setPort(int port){
        this.port = port;
    }
    
    /**
     * Overridden to String method that will return a string
     * displaying IP and Port of the peer.
     * @return String type, containing the IP and Port of the peer object.
     */
    @Override
    public String toString(){
        return "IP: " + ip + " Port: " + port;
    }
}

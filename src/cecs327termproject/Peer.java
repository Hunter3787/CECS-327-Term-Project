package cecs327termproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
*  Peer class.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class Peer implements Runnable{
    /** Default port number. */
    private static final int DEFAULT_PORT = 10100;
    /** Server socket instance to be connected to from remote client. */
    private Socket socket;
    /** String value of IP address. */
    private String ip;
    /** Peer port value as integer. */
    private int port;

    
    /** Local ArrayList of Files to sync in the run method. */
    private ArrayList<FileData> toSync;
    
    
    /**
     * Default constructor.
     */
    public Peer(){
        //No use as of yet.
    }
    
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
    public Peer(String ip, int port) throws IOException{
        this.ip = ip;
        this.port = port;
        this.socket = new Socket(ip, port);
    }
    
    /**
     * Constructor that takes in only a socket.
     * @param socket 
     */
    public Peer(Socket socket){
        this.socket = socket;
        ip = socket.getRemoteSocketAddress().toString();
    }
    
    /**
     * Run method that will download the requested 
     * files to the local computer. 
     * Upon completion will clear the toSync list.
     */
    @Override
    public void run(){
        
        
        
        
        //TODO: Perform the download 
        
        
        
        
        
        
        
        
        toSync.clear();
    }
    
    /**
     * Helper method that passes in an ArrayList of the requested files
     * from this particular peer, then runs the thread as normal. 
     * @param toSync 
     */
    public void syncWithPeer(ArrayList<FileData> toSync){
        this.toSync = toSync;
        Peer temp = this;
        Thread t = new Thread(temp);
        t.start();
    }
    
    /**
     * Getter for IP address.
     * @return String type, IP address used by peer.
     */
    public String getIP(){ 
        return ip; 
    }
    
    /**
     * Getter for the Socket used by this Peer.
     * @return Socket type, sock used by this peer.
     */
    public Socket getSocket(){
        return socket;
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
     * Setter for the Socket used.
     * @param socket Socket type, socket to be used by peer.
     */
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    /**
     * Setter for port number.
     * @param port Integer type, port number used by peer.
     */
    public void setPort(int port){
        this.port = port;
    }
    
    /**
     * Overridden equals method that compares the IP addresses.
     * @param in Instance of Peer to compare.
     * @return True if the IP addresses match and it is an instance of peer.
     */
    @Override
    public boolean equals(Object in){
        if(in instanceof Peer){
            Peer p = (Peer) in;
            return this.ip.equals(p.getIP());
        }
        else
            return false;
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
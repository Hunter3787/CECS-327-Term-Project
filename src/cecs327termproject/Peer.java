package cecs327termproject;

/**
*  Peer class.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class Peer extends Thread{
    private static final int DEFAULT_PORT = 10100;
    private String ip;
    private int port = DEFAULT_PORT;
    
    public Peer(String ip){
        this.ip = ip;
        port = DEFAULT_PORT;
    }
    
    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    
    public String getIP(){ 
        return ip; 
    }
    
    public int getPort(){
        return port;
    }
    
    public void setIP(String ip){ 
        this.ip = ip; 
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public String toString(){
        return "IP: " + ip + " Port: " + port;
    }
}

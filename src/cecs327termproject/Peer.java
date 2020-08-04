package cecs327termproject;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Peer,ArrayList<FileData>> peers;
    
    public Peer(){
        
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
    
}

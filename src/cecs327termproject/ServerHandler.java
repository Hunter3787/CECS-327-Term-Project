package cecs327termproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  ServerHandler class, handles peer connections on a separate thread
*  effectively allowing the current machine to act as a server.
*  Handles most outbound communication.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class ServerHandler implements Runnable{
    
    
    /** Initialized server socket listener. */
    private ServerSocket listener = null; 
    /** ArrayList of all active Peers on the server. */
    private ArrayList<Peer> peers = new ArrayList<>();
    /** Pool of executable threads. 
     * Threads will have up to 60 seconds of inactivity before
     * automatically being shut down by the executor.
     * Removed to not perform run on peer creation.
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    */
    
    /**
     * Run method to be performed on thread start.
     */
    @Override
    public void run(){
        
        //Try catch to assign a server socket to the listener.
        try { listener = new ServerSocket(Main.DEFAULT_PORT); } 
        catch (IOException ex) { 
            System.out.println("ServerSocket Failure."); 
        }
        
        //Infinite loop that will try to assign a new client peer to the peers
        //ArrayList and execute another thread for that peer. 
        while(!Thread.currentThread().isInterrupted()){
            //Use the listener ServerSocket to create a new connection
            //Waiting for any new connections.
            try { Socket client = listener.accept(); 
                //Create new peer using the newly found client socket.
                Peer peerThread = new Peer(client);
                //Add new peer to the peers list.
                peers.add(peerThread);
                /* Running on creation not needed at this time.
                //Executes the new thread in a pool using ExecutorService.
                //The threads should exit after 60 seconds of idle time if needed.
                threadPool.execute(peerThread);
                */
            }
            catch (IOException ex) { 
                System.out.println("Server Socket null."); 
            }
        }
    }
    
    /**
     * Helper method to close the server socket from outside the thread.
     */
    public void closeServer(){
        try {
            listener.close();
        } catch (IOException ex) {
            System.out.println("Server Socket failed to close.");
        }
    }
    
    /**
     * Getter for Server Peers ArrayList.
     * @return ArrayList of all active Peer threads.
     */
    public ArrayList<Peer> getPeers(){ 
        return peers; 
    }
    
    /**
     * Setter for Server Peers ArrayList.
     * @param peers ArrayList of Peers that are actively connected.
     */
    public void setPeers(ArrayList<Peer> peers){ 
        this.peers = peers; 
    }
    
    /*
    Code commented out while thread pool is not in use.
    */
    /**
     * Returns an ExecutorService object of the pool of threads.
     * @return 
     *
    public ExecutorService getThreadPool(){
        return threadPool;
    }
    
    /**
     * Sets the ExectuorService with a new thread pool.
     * @param threadPool 
     *
    public void setThreadPool(ExecutorService threadPool){
        this.threadPool = threadPool;
    }
    */
}
package cecs327termproject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  Main class.
*  Synopsis of functionality on mid page. 
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 22, 2020
*/
public class Main implements Runnable{
    
    /** Volatile boolean, to help maintain thread safety, that handles main loop. */
    volatile boolean loop = true;
    /** Static port number for testing. */
    public static final int DEFAULT_PORT = 10100;
    /** Local static directory for MacBook. */
    public static final String DEFAULT_DIRECTORY 
            = "/Users/nickolausmarshall-eminger/Documents/CSULB/CECS 327/TestFolder";
    /** Local static directory for Desktop. */
    public static final String UPLOAD_DIRECTORY
            = "C:\\Users\\Snake\\Documents\\TermProjectTest";
    
    
    /**
     * Main method.
     * @param args 
     */
    public static void main(String[] args){
        
        //Creates an instance of main to run as a thread.
        Main main = new Main();
        Thread mainLogicThread = new Thread(main);
        mainLogicThread.start();
        
        //Scanner that waits for the user to type exit to end the program 
        //and all threads.
        Scanner in = new Scanner(System.in);
        while(!in.next().toLowerCase().equals("exit"));
        
        in.close();
        main.loop = false;  //Stops the main while loop.
        mainLogicThread.interrupt(); //Should interupt the sleep and exit faster.
    }
    
    /**
     * Runnable mainLogicThread to check and update local data 
     * files against connected peers. 
     * Handles most of the main logic, while the main method handles the
     * user input and request to exit.
     * Every X seconds the mainLogicThread will search for new peers and check their
     * file list against the clients. X = 60 at time of comments.
     */
    @Override
    public void run() {
        //SetupBroadcastReceiver "server"
        BroadcastReceiver br = new BroadcastReceiver();
        Thread receiver = new Thread(br);
        receiver.start();
        
        //Setup for server implimentation.
        ServerHandler server = new ServerHandler();
        Thread serverThread = new Thread(server); 
        serverThread.start();
        
        //Create instance of Broadcaster.
        Broadcaster call = Broadcaster.getInstance();
        
        //String of the local IP.
        String localIP = null;
        //Gather local IP through try.
        try { localIP = InetAddress.getLocalHost().getHostAddress(); }
        catch (UnknownHostException ex) { System.out.println("Unknown Host."); }
        //These will not be moving so one time check is fine.
        
        //Send broadcast to inform network this system is active.
        call.broadcast();
        
        /*
        After failed discovery and server attempts I've decided to just
        hardcode the laptop and desktop together for file sync.
        The above code was attempts at sending out a UDP to ping local computers
        and return their IP, then to create set up a TCP connection to link the
        two together, however the problem I couldn't think of a work around for
        was to keep the sockets open yet refresh with new sockets every 30 seconds
        for the new peers. 
        Resulting in my final incarnation of leaving every thread running, to show
        that it could, while creating a static link between A and B that will 
        simply parse data, and refresh it on each computer every 30 seconds.
        
        The bruteForce socket attaches to the other client, effectively making this
        a client-server attachent, which I tried to avoid but could not figure out.
        
        Above is the attempts at a broadcast server, below is the broadcast call
        neither of which seem to work.
        */
        
        Socket bruteForce = null;
        try{ bruteForce = new Socket("192.168.1.28",DEFAULT_PORT); } 
        catch (IOException ex) { System.out.println("Server not active yet."); }
        
        //While loop to perform until the user exits.
        while(loop){ 
                        
            //Send broadcast to inform network this system is active.
            call.broadcast();
            
            //Place received broadcast addresses into an arryalist.
            ArrayList<InetAddress> addressList = br.getAddresses();
            
            //Build an array of files from the default directory of files.
            File[] fileArray = new File(DEFAULT_DIRECTORY).listFiles();
            //ArrayList of custom type FileData carrying all local files
            //and the information needed to compare to the networks files.
            ArrayList<FileData> localData = new ArrayList<>();

            //An ArrayList of active peers on the server.
            ArrayList<Peer> peers = server.getPeers();
            //Map of collected peers and peerData on each loop.
            Map<String,ArrayList<FileData>> peerData = new HashMap<>();
            //Map of the data to be added and by which peer. (IP as a String)
            Map<String,ArrayList<FileData>> toDownload = new HashMap<>();

            //Parse local data and add it to the localData ArrayList.
            for(File read : fileArray)  
                try { localData.add(new FileData(read, read.getName())); } 
                catch (IOException ex) { System.out.println("File not found.");}
            
            //Send file data from A to B. 
            for(FileData send : localData){
                
            }
            
            
            
            //TODO: Send TCP request to all local devices.
            
            //TODO: Populate map with peer data.
            
            
            
            
            
            
            
            
            
            
            
            
            // A very rough compare method that checks each files existence
            // in the localData array, along with comparing the modified date, and
            // downloading the new file if necessary. 
            for(Map.Entry<String,ArrayList<FileData>> map : peerData.entrySet()){
                ArrayList<FileData> temp = new ArrayList<>();
                for(FileData file : map.getValue()){
                    //If the file exists we can do a compare to determine if the peers
                    //file is newer, if it is add it to the download list.
                    if(localData.contains(file)){
                        int index = localData.indexOf(file);
                        if(localData.get(index).getDate().compareTo(file.getDate()) < 0){
                            temp.add(file);
                        }
                    }
                    //If the file is not present, we do not do a compare 
                    //and add it to the list, we do not have a way to delete at this time.
                    //If we were to add a delete it would likely be here, 
                    //where the map would have a "deleted" tag and inform the peers
                    //that is the most up to date version, removing from the peer.
                    else{
                        temp.add(file);
                    }
                }
                //Adds arraylist of files that need to be synced or added to lcoal
                //machine with peer IP as the key.
                toDownload.put(map.getKey(), temp);
            }
            
            //Second pass through the new "toDownload" map that will start requesting
            //peer syncs. 
            for(Map.Entry<String,ArrayList<FileData>> map : toDownload.entrySet()){
                Peer tempPeer = peers.get(peers.indexOf(map.getKey()));
                tempPeer.syncWithPeer(map.getValue());
            }
            
            /**
             * Map and ArrayList data check area.
             * 
             * ----REMOVE BELOW ------
             */
            for(FileData out : localData)
                System.out.println(out.toString());
            
            System.out.println("Local IP: " + localIP);
            
            for(Peer out : peers)
                System.out.println(out.toString());
            
            for(InetAddress out : addressList)
                System.out.println(out.toString());
            
            
            /**
             * -----REMOVE ABOVE ----
             */
            
            try {
                Thread.sleep(5 * 1000);    //Main mainLogicThread sleeps 
                                            //for half a minute after each loop.
            } catch (InterruptedException ex) {
                System.out.println("Sleep Interupted.");
            }
        }//End while
        
        //Cleanup
        try {bruteForce.close();} 
        catch (IOException ex) { System.out.println("BruteForce does not exist."); }
        br.stopReceiver();
        server.closeServer();
        serverThread.interrupt();        
    }
}
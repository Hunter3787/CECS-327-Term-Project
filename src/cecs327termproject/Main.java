package cecs327termproject;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*  Main class.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 22, 2020
*/
public class Main implements Runnable{
    
    volatile boolean loop = true;
    /** Static port number for testing. */
    private static final int PORT = 10100;
    /** Local static directory for MacBook. */
    private static final String DEFAULT_DIRECTORY 
            = "/Users/nickolausmarshall-eminger/Documents/CSULB/CECS 327";
        
    /**
     * Main method.
     * @param args 
     */
    public static void main(String[] args){
        
        Main main = new Main();
        Thread thread = new Thread(main);
        thread.start();
        
        Scanner in = new Scanner(System.in);
        while(!in.next().toLowerCase().equals("exit"));
        
        main.loop = false;  //Stops the main while loop.
        thread.interrupt(); //Should interupt the leep and exit faster.
    }
    
    /**
     * Runnable thread to check and update local data 
     * files against connected peers. 
     * Handles most of the main logic, while the main method handles the
     * user input and request to exit.
     * Every X seconds the thread will search for new peers and check their
     * file list against the clients. X = 60 at time of comments.
     */
    @Override
    public void run() {
        while(loop){ 
            
            InetAddress local;
            String localIP;

            try {
                local = InetAddress.getLocalHost();
                localIP = local.getHostAddress();
            } catch (UnknownHostException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Build an array of files from the default directory of files.
            File[] fileArray = new File(DEFAULT_DIRECTORY).listFiles();
            //ArrayList of custom type FileData carrying all local files
            //and the information needed to compare to the networks files.
            ArrayList<FileData> localData = new ArrayList<>();
            //An ArrayList of peers that stores the custom peer data.
            ArrayList<Peer> peers = new ArrayList<>();
            //Map of collected peers and peerData on each loop.
            Map<String,ArrayList<FileData>> peerData = new HashMap<>();
            //Map of the data to be added and by which peer. (IP as a String)
            Map<String,ArrayList<FileData>> toAdd = new HashMap<>();

            //Parse local data and add it to the local ArrayList.
            for(File read : fileArray)  
                try { localData.add(new FileData(read, read.getName())); } 
                catch (IOException ex) { System.out.println("File not found.");}

            // A very rough compare method that checks each files existence
            // in the localData array, along with comparing the modified date, and
            // downloading the new file if necessary. 
            for(Map.Entry<String,ArrayList<FileData>> map : peerData.entrySet()){
                for(FileData file : map.getValue()){
                    if(localData.contains(file)){
                        int index = localData.indexOf(file);
                        if(localData.get(index).getDate().compareTo(file.getDate()) < 0){
                            //TODO: request copy from peer with updated file.
                        }
                    }
                    else{
                        //TODO: Request copy from peer of file. 
                    }
                }
            }
            
            
            
            
            
            try {
                Thread.sleep(60 * 1000);    //Main thread sleeps 
                                            //for one minute after each loop.
            } catch (InterruptedException ex) {
                System.out.println("Sleep Interupted.");
            }
        }
    }
}

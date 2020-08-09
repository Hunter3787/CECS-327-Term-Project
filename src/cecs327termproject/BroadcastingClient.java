/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs327termproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 *
 * @author nickolausmarshall-eminger
 */
public class BroadcastingClient {
    
    private DatagramSocket socket;
    private InetAddress address;
    private int expectedServerCount;
    private byte[] buffer;
    
    public BroadcastingClient (int expectedServerCount) throws Exception{
        this.expectedServerCount = expectedServerCount;
        this.address = InetAddress.getByName("255.255.255.255");
    }
    
    public int discoverServers(String msg) throws IOException {
        initializeSocketForBroadcasting();
        copyMessageOnBuffer(msg);
        
        broadcastPacket(address);
        
        return receivePackets();
    }

    public ArrayList<InetAddress> listAllBroadcastAddresses() throws SocketException{
        ArrayList<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        
        while(interfaces.hasMoreElements()){
            NetworkInterface ni = interfaces.nextElement();
            
            if(ni.isLoopback() || !ni.isUp()){
                continue;
            }
            
            broadcastList.addAll(ni.getInterfaceAddresses()
                    .stream()
                    .filter(address -> address.getBroadcast() != null)
                    .map(address -> address.getBroadcast())
                    .collect(Collectors.toList()));
        }
        return broadcastList;
    }
    
    private void initializeSocketForBroadcasting() throws SocketException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }
    
    private void copyMessageOnBuffer(String msg){
        buffer = msg.getBytes();
    }
    
    private void broadcastPacket(InetAddress address) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 10000);
        socket.send(packet);
    }
    
    private int receivePackets() throws IOException{
        int serversDiscovered = 0;
        while (serversDiscovered != expectedServerCount) {
            receivePacket();
            serversDiscovered++;
        }
        
        return serversDiscovered;
    }
    
    private void receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
    }
    
    public void close(){
        socket.close();
    }
}

package com.project.utils;


import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

public class PeerData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long uuid;
    private String hostname;
    private InetAddress address;
    private int port;

    public PeerData(long uuid, String hostname, InetAddress address, int port ){

    	this.uuid = uuid;
        this.hostname = hostname;
        this.address = address;
        this.port = port;
    }
    
    public void printData() {
    	
    	System.out.println("[PEER DATA] PEER DATA: \n");
    	System.out.println("	UUID: " + uuid);
    	System.out.println("	Hostname: " + hostname);
    	System.out.println("	Port: " + port);
    	System.out.println("	IP: " + address.getHostAddress() + "\n");
    }

    public long getUuid() {
        return uuid;
    }

    public String getHostname() {
        return hostname;
    }

    public InetAddress getAddress() {
        return address;
    }
    
    public int getPort() {
        return port;
    }
}
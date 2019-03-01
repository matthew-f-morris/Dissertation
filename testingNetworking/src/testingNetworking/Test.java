package testingNetworking;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class Test {
	
	public String broadcastAddress = "255.255.255.255";
	public int broadcastPort = 50008;
	private int broadcastInterval = 1000;
	
	private static InetAddress localhost;
	private static int commPort = 50010;
    public PeerData nodeInfo;

	private static boolean isRunning = true;

	private static InetAddress inetAddress;

	public void go() {
		
		String hostname = null;
    	
    	try {    	
    		
    		localhost = InetAddress.getLocalHost();
			hostname = localhost.getHostName();
			
		} catch (UnknownHostException e) {			
			System.out.println("Unable to get local host data!");
			e.printStackTrace();
		}
    	
    	nodeInfo = new PeerData(UUID.randomUUID().toString(), hostname, localhost, this.commPort);  
		
		System.out.println("Broadcast started...");

		try {

			DatagramSocket socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName(this.broadcastAddress);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));

			oos.flush();
			oos.writeObject(this.nodeInfo);
			oos.flush();
			oos.close();

			byte[] messageByte = bos.toByteArray();

			while (isRunning) {

				System.out.println("Broadcasting packet...");
				socket.send(new DatagramPacket(messageByte, messageByte.length, address, this.broadcastPort));

				try {

					Thread.sleep(this.broadcastInterval);
				}

				catch (InterruptedException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}

			socket.close();
		}

		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		System.out.println("[PEER DISCOVERY] Thread Ended");
	}
}

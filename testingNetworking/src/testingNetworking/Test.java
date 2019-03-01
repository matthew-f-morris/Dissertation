package testingNetworking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Test {
	
	private static final String broadcastAddress = "255.255.255.255";
	private static final int broadcastPort = 50008;
	private static InetAddress inetAddress;
	private static long interval = 2000;
	private Thread listener;
	
	public Test() {

		try {
			
			Test.inetAddress = InetAddress.getLocalHost();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
        System.out.println("Host Name: " + inetAddress.getHostName());
        
        if(listener == null) {
        	listener = new BroadcastListener();
        	listener.start();
        }
        
        try {
			this.broadcast(5);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void broadcast(int times) throws IOException {
			   
		DatagramSocket socket = new DatagramSocket();
		InetAddress address = InetAddress.getByName(Test.broadcastAddress);
				   
		String message = "Hello from " + inetAddress.getHostName();
		
		int messageLength = message.length();
		byte[] messageByte = message.getBytes();
				
		try {
			for (int i = 0; i < times ; i++) {
				
				socket.send(new DatagramPacket(messageByte, messageLength, address, broadcastPort));
				
				try {
					Thread.sleep(Test.interval);
				}
				
				catch (InterruptedException e){
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
		   
	}
	
	class BroadcastListener extends Thread {
		
		private volatile boolean isRunning = true;
		
		public BroadcastListener() {
			
			System.out.println("Listening for peer broadcasts...");
		}
		
		public void run() {
			
			String text;
			byte[] message = new byte[1500];
			
			try {
				
				DatagramPacket p = new DatagramPacket(message, message.length);
				DatagramSocket socket = new DatagramSocket(Test.broadcastPort);
				
				while(isRunning) {
					
					socket.receive(p);
					text = new String(message, 0, p.getLength());
					System.out.println("Broadcast Listener, message: " + text);					
				}
				
				socket.close();
				System.out.println("Stopping Thread");
			}
			
			catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}

//    public static final InetSocketAddress discoverService(String serviceName, int port, int maxTries, int socketTimeout) {
//    	try {
//		    int packetLength = 1024;
//		
//		    DatagramSocket socket = new DatagramSocket(port, InetAddress.getLocalHost());
//		    DatagramPacket packet = new DatagramPacket(new byte[packetLength], 0, packetLength);
//		
//		    socket.setSoTimeout(socketTimeout);
//
//		    for (int i = 0; i < maxTries; i++) {
//		    	try {
//		    		socket.receive(packet);
//		    	}
//		    	
//	            catch (IOException exc) {
//	            	
//	               exc.printStackTrace();
//	               break;
//	            }
//
//	            ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
//	            DataInputStream dis = new DataInputStream(bais);
//	
//	            String theServiceName = dis.readUTF();
//	            
//	            if (!theServiceName.equals(serviceName))
//	            	continue;
//	
//	            String notifyHost = dis.readUTF();
//	            int notifyPort = dis.readInt();
//	            return new InetSocketAddress(notifyHost, notifyPort);
//		    }
//    	}
//    	
//	    catch (IOException exc) {
//	    	
//	    	exc.printStackTrace();
//	    }
//
//    	throw new NoSuchServiceException("Service \"" + serviceName + "\" not found");
//    }
//
//    public static class NoSuchServiceException extends RuntimeException {
//    
//    	public NoSuchServiceException(String msg){
//      
//    		super(msg);
//    	}
//    }
//}


//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//DataOutputStream dos = new DataOutputStream(baos);
//dos.writeUTF(serviceName);
//dos.writeUTF(broadcastAboutService.getHostName());
//dos.writeInt(broadcastAboutService.getPort());
//dos.flush();
//packetData = baos.toByteArray();

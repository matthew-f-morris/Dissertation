package com.project.network;

import com.project.Thread.BroadcastListenerManager;
import com.project.Thread.BroadcastManager;

public class PeerDiscovery {

	public Node node;
	
	
	private BroadcastManager broadcast;
	private BroadcastListenerManager listener;
	
	public PeerDiscovery(Node node) {
		this.node = node;
		broadcast = new BroadcastManager(node);
		listener = new BroadcastListenerManager(node);
		startDiscovery();
	}

	public void shutdown() {
		broadcast.stop();
		listener.stop();
	}
	
	public void leaveNetwork() {
		shutdown();
	}
	
	public void startDiscovery() {
		broadcast.start();
		listener.start();
	}

//	private Thread threadListener;
//	private Thread threadBroadcaster;
//	private BroadcastListener listener;
//	private Broadcaster broadcaster;
//
//
//	//private DatagramSocket socket;	
//	private InetAddress inetAddress;
//
//	public PeerDiscovery(Node node) {
//
//		PeerDiscovery.node = node;
//
//		try {
//
//			inetAddress = InetAddress.getLocalHost();
//			socket = new DatagramSocket(PeerDiscovery.broadcastPort);
//
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//
//		startDiscovery();
//	}
//
//	public void startDiscovery() {
//
//		isRunning = true;
//		
//		if(socket.isClosed()) {
//			
//			try {
//				socket = new DatagramSocket(PeerDiscovery.broadcastPort);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		listener = new BroadcastListener();
//		broadcaster = new Broadcaster();
//
//		threadBroadcaster = new Thread(broadcaster);
//		threadBroadcaster.setName("[PEER DISCOVERY] BROADCASTER");
//		threadBroadcaster.start();
//
//
//
//		threadListener = new Thread(listener);
//		threadListener.setName("[PEER DISCOVERY] BROADCAST LISTENER");
//		threadListener.start();
//
//	}
//	
//	public void shutdown() {
//		
//		leaveNetwork();
//	}
//	
//	public void leaveNetwork() {
//		
//		isRunning = false;
//		socket.close();
//		
//		try {
//			threadBroadcaster.join();
//			threadListener.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("[PEER DISCOVERY] ALL DISCOVERY SYSTEMS SHUT DOWN");
//	}
//
//	class Broadcaster implements Runnable, ThreadStop {
//
//		private boolean isRunning = true;
//		
//		public void run() {
//
//			System.out.println("[PEER DISCOVERY] Broadcast started...");
//
//			try {
//
//				DatagramSocket socket = new DatagramSocket();
//				InetAddress address = InetAddress.getByName(PeerDiscovery.broadcastAddress);
//
//				ByteArrayOutputStream bos = new ByteArrayOutputStream();
//				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));
//
//				oos.flush();
//				oos.writeObject(PeerDiscovery.node.nodeInfo);
//				oos.flush();
//				oos.close();
//
//				byte[] messageByte = bos.toByteArray();
//
//				while (isRunning) {
//
//					//System.out.println("[PEER DISCOVERY] Broadcasting packet...");
//					socket.send(new DatagramPacket(messageByte, messageByte.length, address, PeerDiscovery.broadcastPort));
//
//					try {
//						Thread.sleep(PeerDiscovery.broadcastInterval);
//					}
//
//					catch (InterruptedException e) {
//						System.out.println(e);
//						e.printStackTrace();
//					}
//				}
//
//				socket.close();
//			}
//
//			catch (Exception e) {
//				System.out.println(e);
//				e.printStackTrace();
//			}
//
//			System.out.println("[PEER DISCOVERY] Broadcaster Thread Ended");
//		}
//
//		public void stopThread() {	
//			
//		}
//	}
//
//	class BroadcastListener implements Runnable, ThreadStop {
//		
//		DatagramSocket socket;
//		private boolean isRunning = ThreadStop.isRunning;
//		
//		public BroadcastListener() {
//			
//			try {
//				socket = new DatagramSocket(PeerDiscovery.broadcastPort);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		public void run() {
//
//			System.out.println("[PEER DISCOVERY] Listening for broadcasting peers...");
//			byte[] message = new byte[5000];
//
//			try {
//
//				DatagramPacket p = new DatagramPacket(message, message.length);
//
//				while (isRunning) {
//																
//					socket.receive(p);
//
//					ByteArrayInputStream bis = new ByteArrayInputStream(message);
//					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
//
//					PeerData data = (PeerData) ois.readObject();
//					PeerDiscovery.node.addPeer(data.getUuid(), data.getHostname(), data.getAddress(), data.getPort());
//					ois.close();
//
//				}
//
//				socket.close();
//				
//			}
//
//			catch (Exception e) {
//				System.out.println("[PEER DISCOVERY] Listener Thread Ended");
//			}
//		}
//
//		public void stopThread() {
//	
//			isRunning = false;
//			socket.close();			
//		}
//	}
}
package com.project.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class PeerDiscovery {

	public static Node node;
	public static final String broadcastAddress = "255.255.255.255";
	public static final int broadcastPort = 50008;
	private static int broadcastInterval = 5000;

	private Thread threadListener;
	private Thread threadBroadcaster;
	private BroadcastListener listener;
	private Broadcaster broadcaster;
	private boolean isRunning = true;

	private DatagramSocket socket;	
	private InetAddress inetAddress;

	public PeerDiscovery(Node node) {

		PeerDiscovery.node = node;

		try {

			inetAddress = InetAddress.getLocalHost();
			socket = new DatagramSocket(PeerDiscovery.broadcastPort);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		startDiscovery();
	}

	public void startDiscovery() {

		isRunning = true;
		
		if(socket.isClosed()) {
			
			try {
				socket = new DatagramSocket(PeerDiscovery.broadcastPort);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
		listener = new BroadcastListener(socket);
		broadcaster = new Broadcaster();

		threadBroadcaster = new Thread(broadcaster);
		threadBroadcaster.setName("[PEER DISCOVERY] BROADCASTER");
		threadBroadcaster.start();



		threadListener = new Thread(listener);
		threadListener.setName("[PEER DISCOVERY] BROADCAST LISTENER");
		threadListener.start();

	}
	
	public void shutdown() {
		
		leaveNetwork();
	}
	
	public void leaveNetwork() {
		
		isRunning = false;
		socket.close();
		
		try {
			threadBroadcaster.join();
			threadListener.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("[PEER DISCOVERY] ALL DISCOVERY SYSTEMS SHUT DOWN");
	}

	
	class Broadcaster implements Runnable {

		public void run() {

			System.out.println("[PEER DISCOVERY] Broadcast started...");

			try {

				DatagramSocket socket = new DatagramSocket();
				InetAddress address = InetAddress.getByName(PeerDiscovery.broadcastAddress);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));

				oos.flush();
				oos.writeObject(PeerDiscovery.node.nodeInfo);
				oos.flush();
				oos.close();

				byte[] messageByte = bos.toByteArray();

				while (isRunning) {

					//System.out.println("[PEER DISCOVERY] Broadcasting packet...");
					socket.send(new DatagramPacket(messageByte, messageByte.length, address, PeerDiscovery.broadcastPort));

					try {
						Thread.sleep(PeerDiscovery.broadcastInterval);
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

			System.out.println("[PEER DISCOVERY] Broadcaster Thread Ended");
		}
	}

	class BroadcastListener implements Runnable {
		
		DatagramSocket socket;
		
		public BroadcastListener(DatagramSocket socket) {
			this.socket = socket;
		}
		
		public void run() {

			System.out.println("[PEER DISCOVERY] Listening for broadcasting peers...");
			byte[] message = new byte[5000];

			try {

				DatagramPacket p = new DatagramPacket(message, message.length);

				while (isRunning) {
																
					socket.receive(p);

					ByteArrayInputStream bis = new ByteArrayInputStream(message);
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));

					PeerData data = (PeerData) ois.readObject();
					PeerDiscovery.node.addPeer(data.getUuid(), data.getHostname(), data.getAddress(), data.getPort());
					ois.close();

				}

				socket.close();
				
			}

			catch (Exception e) {
				System.out.println("[PEER DISCOVERY] Listener Thread Ended");
			}
		}
	}
}
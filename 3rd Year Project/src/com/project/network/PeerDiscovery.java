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
	private boolean isRunning2 = true;

	private InetAddress inetAddress;

	public PeerDiscovery(Node node) {

		PeerDiscovery.node = node;

		try {

			inetAddress = InetAddress.getLocalHost();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		startDiscovery();
	}

	public void startDiscovery() {

		listener = new BroadcastListener();
		broadcaster = new Broadcaster();

		if (threadBroadcaster == null) {
			threadBroadcaster = new Thread(broadcaster);
			threadBroadcaster.start();
		}

		if (threadListener == null) {
			threadListener = new Thread(listener);
			threadListener.start();
		}
	}
	
	public void shutdown() {
		
		endThreads();
	}
	
	public void leaveNetwork() {
		
		endThreads();
	}
	
	public void endThreads() {
		
		System.out.println("[PEER DISCOVERY] ENDING THREADS");
		isRunning = false;
		
		try {
			threadBroadcaster.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		isRunning2 = false;
		System.out.println("HERE");
		
		try {
			threadListener.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

		public void run() {

			System.out.println("[PEER DISCOVERY] Listening for peer broadcasts...");
			byte[] message = new byte[5000];

			try {

				DatagramPacket p = new DatagramPacket(message, message.length);
				DatagramSocket socket = new DatagramSocket(PeerDiscovery.broadcastPort);

				while (isRunning2) {

					socket.receive(p);

					ByteArrayInputStream bis = new ByteArrayInputStream(message);
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));

					PeerData data = (PeerData) ois.readObject();
					PeerDiscovery.node.addPeer(data.getUuid(), data.getHostname(), data.getAddress(), data.getPort());
					ois.close();
				}

				socket.close();
				System.out.println("[PEER DISCOVERY] Listener Thread Ended");
			}

			catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
}
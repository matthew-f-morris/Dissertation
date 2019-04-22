package com.project.Thread;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.project.network.Node;
import com.project.utils.CommunicationInfo;

public class BroadcastManager implements Manager {
	
	private Thread threadBroadcaster;
	private Broadcaster broadcaster;
	private Node node;
	
	private static int broadcastInterval = 3000;
	
	public BroadcastManager(Node node) {
		this.node = node;
	}	
	
	public void start() {
		
		broadcaster = new Broadcaster();
		threadBroadcaster = new Thread(broadcaster);
		threadBroadcaster.setName("[PEER DISCOVERY] BROADCASTER");
		threadBroadcaster.start();
	}
	
	public void stop() {		

		broadcaster.stopThread();
		
		try {
			threadBroadcaster.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean threadState() {
		return threadBroadcaster.isAlive();		
	}
	
	class Broadcaster implements Runnable, ThreadStop {

		private DatagramSocket socket;
		private boolean isRunning = true;
		
		public void run() {

			System.out.println("[BROADCASTER] Broadcast started...");

			try {

				socket = new DatagramSocket();
				InetAddress address = InetAddress.getByName(CommunicationInfo.broadcastAddress);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));

				oos.flush();
				oos.writeObject(node.nodeInfo);
				oos.flush();
				oos.close();

				byte[] messageByte = bos.toByteArray();

				while (isRunning) {

					//System.out.println("[PEER DISCOVERY] Broadcasting packet...");
					socket.send(new DatagramPacket(messageByte, messageByte.length, address, CommunicationInfo.broadcastPort));

					try {
						Thread.sleep(broadcastInterval);
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

			System.out.println("[BROADCASTER] Broadcaster Thread Ended");
		}

		public void stopThread() {	
			
			socket.close();
			isRunning = false;			
		}
	}
}

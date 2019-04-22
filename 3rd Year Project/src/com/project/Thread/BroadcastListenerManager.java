package com.project.Thread;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.project.network.Node;
import com.project.utils.CommunicationInfo;
import com.project.utils.PeerData;

public class BroadcastListenerManager implements Manager {

	private Thread threadListener;
	private BroadcastListener listener;
	private Node node;
	
	public BroadcastListenerManager(Node node) {		
		this.node = node;
	}
	
	public void start() {
		
		listener = new BroadcastListener();
		threadListener = new Thread(listener);
		threadListener.setName("[BROADCAST LISTENER] BROADCAST LISTENER");
		threadListener.start();
	}

	public void stop() {
		
		listener.stopThread();
		
		try {
			threadListener.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean threadState() {
		return threadListener.isAlive();		
	}
	
	class BroadcastListener implements Runnable, ThreadStop {
		
		DatagramSocket socket;
		private boolean isRunning = ThreadStop.isRunning;
		
		public BroadcastListener() {
			
			try {
				socket = new DatagramSocket(CommunicationInfo.broadcastPort);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {

			System.out.println("[BROADCAST LISTENER] Listening for broadcasting peers...");
			byte[] message = new byte[1500];

			try {

				DatagramPacket p = new DatagramPacket(message, message.length);

				while (isRunning) {
																
					socket.receive(p);

					ByteArrayInputStream bis = new ByteArrayInputStream(message);
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));

					PeerData data = (PeerData) ois.readObject();
					System.out.println("[BROADCAST LISTENER MANAGER] Recieved Vector Clock" + data.getVectorClock().toString());
					node.addPeer(data.getUuid(), data.getHostname(), data.getAddress(), data.getPort(), data.getVectorClock());
					ois.close();

				}

				socket.close();				
			}

			catch (Exception e) {
				System.out.println("[BROADCAST LISTENER] Listener Thread Ended");
			}
		}

		public void stopThread() {
	
			isRunning = false;
			socket.close();			
		}
	}
}

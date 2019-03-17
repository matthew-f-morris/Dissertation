package com.project.Thread;

import com.project.controller.MessageController;
import com.project.network.PeerData;
import com.project.utils.AddressMessage;
import com.project.utils.Message;
import com.project.utils.MessageControllerInfo;

public class SenderCheckerManager implements Manager{	
	
	private MessageController controller;
	
	private Thread threadSender;
	private SenderChecker senderChecker;

	public SenderCheckerManager(MessageController controller) {
		this.controller = controller;
	}
	
	public void start() {

		senderChecker = new SenderChecker();
		threadSender = new Thread(senderChecker);
		threadSender.setName("[MESSAGE CONTROLLER] SEND CHECKER");
		threadSender.start();
	}

	public void stop() {
		
		senderChecker.stopThread();
		
		try {			
			threadSender.join();			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class SenderChecker implements Runnable, ThreadStop {		
		
		private boolean isRunning = ThreadStop.isRunning;
				
		public void run() {
	
			System.out.println("[MESSAGE CONTROLLER] Sender Checker Thread started...");
			
			while (isRunning) {
	
				try {
					Thread.sleep(MessageControllerInfo.sendInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				if (controller.messagesToSend.size() >= 1) {
					Message toSend = controller.messagesToSend.removeLast();
					for (PeerData peer : controller.peers.values()) {						
						if(!(peer.getUuid().equals(controller.node.nodeInfo.getUuid()))){							
							boolean sent = controller.sender.sendMessage(peer.getAddress(), toSend);
							
							if(!sent) {
								controller.toResend.add(new AddressMessage(peer.getAddress(), toSend));
							}
						}						
					}
				}
			}
	
			System.out.println("[MESSAGE CONTROLLER] Sender Checker Thread Ended");
		}
	
		public void stopThread() {
			
			isRunning = false;
		}
	}
}

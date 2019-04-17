package com.project.Thread;

import com.project.controller.MessageController;
import com.project.utils.AddressMessage;
import com.project.utils.MessageControllerInfo;

public class ResenderManager implements Manager {

	private MessageController controller;
	
	private Thread threadResender;
	private Resender resender;
	
	public ResenderManager(MessageController controller) {
		this.controller = controller;
	}
	
	public void start() {
		
		resender = new Resender();		
		threadResender = new Thread(resender);
		threadResender.setName("[MESSAGE CONTROLLER] RESENDER");
		threadResender.start();
	}

	public void stop() {
		
		resender.stopThread();
		
		try {
			threadResender.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public Boolean threadState() {
		return threadResender.isAlive();		
	}
	
	class Resender implements Runnable, ThreadStop {
		
		private boolean isRunning = ThreadStop.isRunning;
		
		public void run() {
			
			System.out.println("[MESSAGE CONTROLLER] Resender Thread started...");
			
			while (isRunning) {

				try {
					Thread.sleep(MessageControllerInfo.resendInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (controller.toResend.size() >= 1) {
					
					System.out.println("Number of messages to resend: " + controller.toResend.size());
					AddressMessage resend = controller.toResend.poll();
					
					boolean resent = controller.sender.sendMessage(resend.getAddress(), resend.getMessage());					
					if(!resent) {
						controller.toResend.add(resend);
					}
				}
			}
			
			System.out.println("[MESSAGE CONTROLLER] Resender Thread Ended");
		}

		public void stopThread() {
			isRunning = false;
		}
	}
}

package telran.multithreading.consumer;

import java.util.concurrent.atomic.AtomicInteger;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	private static AtomicInteger messagesCounter = new AtomicInteger(0);
	private MessageBox messageBox;
	private boolean running = true;
	public Receiver(MessageBox messageBox) {
		
		this.messageBox = messageBox;
	}
	@Override
	public void run() {
		String message = null;
		while(running ) {
			try {
				message = messageBox.get();
				messageProcessing(message);
			} catch (InterruptedException e) {
				
			}
		}
		while((message = messageBox.take()) != null) {
			messageProcessing(message);
		}
	}
	private void messageProcessing(String message) {
		messagesCounter.incrementAndGet();
		System.out.println(message + getName());
	}
	public static int getMessagesCounter() {
		return messagesCounter.get();
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
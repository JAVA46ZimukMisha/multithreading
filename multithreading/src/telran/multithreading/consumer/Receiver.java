package telran.multithreading.consumer;

import java.util.concurrent.atomic.AtomicInteger;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;
	public static AtomicInteger messagesCounter = new AtomicInteger(0);
	public static AtomicInteger getMessagesCounter() {
		return messagesCounter;
	}
	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
	}
	@Override
	public void run() {
		while(true) {
			try {
				String message = messageBox.get();
				System.out.println(message + getName());
				messagesCounter.incrementAndGet();
			} catch (InterruptedException e) {
				if(messageBox.take()==null) {
					break;
				}
			}
		}
	}
	
}
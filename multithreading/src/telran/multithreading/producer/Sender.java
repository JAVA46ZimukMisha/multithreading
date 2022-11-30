package telran.multithreading.producer;

import telran.multithreading.MessageBox;

public class Sender extends Thread {
	private static final long TIME_OUT = 10;
	private MessageBox messageBox;
	private int nMessages;

	public Sender(MessageBox messageBox, int nMessages) {
		this.nMessages = nMessages;
		this.messageBox = messageBox;
	}
	@Override
	public void run() {
		for(int i = 0; i < nMessages; i++) {
			try {
				messageBox.put(String.format("message%d from thread ", i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			try {
//				sleep(TIME_OUT);
//			} catch (InterruptedException e) {
//				
//			}
		}
	}
	
}
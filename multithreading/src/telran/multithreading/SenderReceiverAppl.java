package telran.multithreading;

import java.util.Arrays;
import java.util.stream.IntStream;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.producer.Sender;

public class SenderReceiverAppl {

	private static final int N_RECEIVERS = 10;
	private static final int N_MESSAGES = 20000;

	public static void main(String[] args) throws InterruptedException {
		Receiver receivers[] = new Receiver[N_RECEIVERS];
		MessageBox messageBox = new MessageBox();
		startReceivers(receivers, messageBox);
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		sender.join();
		stopReceivers(receivers);
		waitReceivers(receivers);
		System.out.println("number of the processed messages is " + Receiver.getMessagesCounter());

	}

	private static void waitReceivers(Receiver[] receivers) {
		Arrays.stream(receivers).forEach(r -> {
			try {
				r.join();
			} catch (InterruptedException e) {
				
			}
		});
	}

	private static void stopReceivers(Receiver[] receivers) {
		Arrays.stream(receivers).forEach(r -> {
			r.setRunning(false);
			r.interrupt();
		});
		
	}

	private static void startReceivers(Receiver[] receivers, MessageBox messageBox) {
		IntStream.range(0, N_RECEIVERS).forEach(i -> {
			receivers[i] = new Receiver(messageBox);
			receivers[i].start();
		});

		
	}

}
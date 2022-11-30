package telran.multithreading;

import java.util.concurrent.locks.*;

public class MessageBox {
	private String message;
	private Lock monitor = new ReentrantLock();
	private Condition producerWaitingCondition = monitor.newCondition();
	private Condition consumerWaitingCondition = monitor.newCondition();

	public void put(String message) throws InterruptedException {
		monitor.lock();
		try {
			while (this.message != null) {
				producerWaitingCondition.await();
			}
			this.message = message;
			consumerWaitingCondition.signal();
		} finally {
			monitor.unlock();
		}
	}

	public String get() throws InterruptedException {
		monitor.lock();
		try {
			while (message == null) {
				consumerWaitingCondition.await();
			}
			String res = message;
			message = null;
			producerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}

	}
	public String take() {
	
		monitor.lock();
		try {
			return message; //possible null
		} finally {
			monitor.unlock();
		}
		
	}

}
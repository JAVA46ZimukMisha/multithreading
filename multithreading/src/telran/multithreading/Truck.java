package telran.multithreading;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Truck extends Thread {
	private int load;
	private int nLoads;
	private static long elevator1;
	private static long elevator2;
	private static AtomicLong waitingCounter = new AtomicLong(0);
//	private static final Object mutex = new Object();
	public static final Lock lock = new ReentrantLock(true);

//	public static final Lock lock1 = new ReentrantLock(true);
//	public static final Lock lock2 = new ReentrantLock(true);
	public Truck(int load, int nLoads) {
		this.load = load;
		this.nLoads = nLoads;
	}

	@Override
	public void run() {
		while (!lock.tryLock()) {
			waitingCounter.addAndGet(1);
		}
		try {
			for (int i = 0; i < nLoads; i++) {
				loadElevator1(load);
				loadElevator2(load);
			}
		} finally {
			lock.unlock();
		}

//			for (int i = 0; i < nLoads; i++) {
//				while (!lock1.tryLock()) {
//					waitingCounter = getWaitingCounter() + 1;
//				}
//				try {
//				loadElevator1(load);
//				}finally {
//					lock1.unlock();
//				}
//				while (!lock2.tryLock()) {
//					waitingCounter = getWaitingCounter() + 1;
//				}
//				try {
//				loadElevator2(load);
//				}finally {
//					lock2.unlock();
//				}
//			}

	}

	static private void loadElevator2(int load) {
		elevator2 += load;
	}

	static private void loadElevator1(int load) {
		elevator1 += load;
	}

	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}

	public static AtomicLong getWaitingCounter() {
		return waitingCounter;
	}
}
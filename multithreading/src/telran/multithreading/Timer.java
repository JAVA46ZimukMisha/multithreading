package telran.multithreading;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer extends Thread {
	private static final long DEFAULT_TIMEOUT = 1000;
	private static final String DEFAULT_FORMAT = "H:m:s";
	private DateTimeFormatter dtf;
	private long timeout;

	public Timer(String format, long timeout) {
		dtf = DateTimeFormatter.ofPattern(format);
		this.timeout = timeout;
		setDaemon(true);
	}

	public Timer(String format) {
		this(format, DEFAULT_TIMEOUT);
	}

	public Timer(long timeout) {
		this(DEFAULT_FORMAT, timeout);
	}

	public Timer() {
		this(DEFAULT_FORMAT, DEFAULT_TIMEOUT);
	}
	@Override
	public void run() {
		while(true) {
			System.out.println(LocalTime.now().format(dtf));
			try {
				sleep(timeout);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
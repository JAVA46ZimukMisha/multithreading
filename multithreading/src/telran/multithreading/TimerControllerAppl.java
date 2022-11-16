package telran.multithreading;

public class TimerControllerAppl {

	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer("hh:mm:ss a");
		
		timer.start();
		
		Thread.sleep(5000);
		timer.interrupt();
		Thread.sleep(5000);

	}

}
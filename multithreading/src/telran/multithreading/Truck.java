package telran.multithreading;

public class Truck extends Thread{
	private int load;
	private int nLoads;
	private static long elevator1;
	private static long elevator2;
	private static final Object mutex = new Object();
	public Truck(int load, int nLoads) {
		this.load = load;
		this.nLoads = nLoads;
	}
	@Override
	public void run() {
		for (int i = 0; i < nLoads; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}
	synchronized static private void loadElevator2(int load) {
		 {
			elevator2 += load;
		}
		
	}
	synchronized static private void loadElevator1(int load) {
		elevator1 += load;
		
	}
	public static long getElevator1() {
		return elevator1;
	}
	public static long getElevator2() {
		return elevator2;
	}
}
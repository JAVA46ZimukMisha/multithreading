package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

public class FermAppl {

	private static final int N_TRUCKS = 2000;
	private static final int N_LOADS = 5000;

	public static void main(String[] args) {
		Truck[] trucks = new Truck[N_TRUCKS];
		Instant start = Instant.now();
		startTrucks(trucks);
		waitigForFinishing(trucks);
		System.out.printf("Report: elevator1 contains %d tons; elevator2 contains %d tons"
				+ "\nrunning time is %d\n", Truck.getElevator1(),
				Truck.getElevator2(), ChronoUnit.MILLIS.between(start, Instant.now()));
		System.out.printf(" value of the waiting iterations counter = " + Truck.getWaitingCounter());

	}

	private static void waitigForFinishing(Truck[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				
			}
		});
		
	}

	private static void startTrucks(Truck[] trucks) {
		IntStream.range(0, trucks.length)
		.forEach(i -> {
			trucks[i] = new Truck(1, N_LOADS);
			trucks[i].start();
		});
		
	}

}
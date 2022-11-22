package telran.multithreading.games;

import java.time.Instant;

public class Runner extends Thread {
private Race race;
private int runnerId;
private Instant finish;
public Runner(Race race, int runnerId) {
	this.race = race;
	this.runnerId = runnerId;
}
@Override
public void run() {
	int sleepRange = race.getMaxSleep() - race.getMinSleep() + 1;
	int minSleep = race.getMinSleep();
	int distance = race.getDistance();
	for (int i = 0; i < distance; i++) {
		try {
			sleep((long) (minSleep + Math.random() * sleepRange));
		} catch (InterruptedException e) {
			throw new IllegalStateException();
		}
		System.out.println(runnerId);
	}
	synchronized(race) {
		finish = Instant.now();
		race.getResults().add(this);
	}
}
public Instant getFinish() {
	return finish;
}
}
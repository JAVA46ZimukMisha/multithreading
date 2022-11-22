package telran.multithreading.games;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.IntStream;

import telran.view.*;

public class RaceAppl {

	private static final int MAX_THREADS = 20;
	private static final int MIN_DISTANCE = 10;
	private static final int MAX_DISTANCE = 1000;
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Item[] items = getItems();
		Menu menu = new Menu("Race Game", items);
		menu.perform(io);

	}

	private static Item[] getItems() {
		Item[] res = {
				Item.of("Start new game", RaceAppl::startGame),
				Item.exit()
		};
		return res;
	}
	static void startGame(InputOutput io) {
		int nThreads = io.readInt("Enter number of the runners","Wrong number of the runners", 2, MAX_THREADS);
		int distance = io.readInt("Enter distance", "Wrong Distance",MIN_DISTANCE, MAX_DISTANCE);
		Instant start = Instant.now();
		ArrayList<Runner> results = new ArrayList<Runner>(nThreads);
		Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP, results);
		Runner[] runners = new Runner[nThreads];
		startRunners(runners, race);
		joinRunners(runners);
		displayTable(race, start);
	}

	private static void displayTable(Race race, Instant start) {
		System.out.println("place\trunner\ttime in millis");
		IntStream.range(0, race.getResults().size()).forEach(i -> {
			Runner runner = race.getResults().get(i);
		System.out.println(i+1 + "\t"
			+ runner.getName()+ "\t"
				+ ChronoUnit.MILLIS.between(start, runner.getFinish()));
		});
	}

	private static void joinRunners(Runner[] runners) {
		IntStream.range(0, runners.length).forEach(i -> {
			try {
				runners[i].join();
			} catch (InterruptedException e) {
				throw new IllegalStateException();
			}
		});
		
	}

	private static void startRunners(Runner[] runners, Race race) {
		IntStream.range(0, runners.length).forEach(i -> {
			runners[i] = new Runner(race, i + 1);
			runners[i].start();
		});
		
	}

}
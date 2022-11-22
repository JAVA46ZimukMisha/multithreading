package telran.multithreading.games;

import java.util.ArrayList;

public class Race {
	private int distance;
	private int minSleep;
	private int maxSleep;
	private int winner = -1;
	private ArrayList<Runner> results;
	public Race(int distance, int minSleep, int maxSleep, ArrayList<Runner> results) {
		this.distance = distance;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		this.results = results;
	}
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		if (this.winner == -1) {
			this.winner = winner;
		}
	}
	public int getDistance() {
		return distance;
	}
	public int getMinSleep() {
		return minSleep;
	}
	public int getMaxSleep() {
		return maxSleep;
	}
	public ArrayList<Runner> getResults() {
		return results;
	}
	
}
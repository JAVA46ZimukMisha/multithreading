package telran.multithreading;

public class SynPrinter extends Thread {
	private String symbols;
	private int index;
	private SynPrinter[] printers;
	private int n_printers;
	final int N_NUMBERS = 100;
	final int N_PORTIONS = 10;
	private boolean running = true;

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public SynPrinter(String symbols, SynPrinter[] printers, int index, int n_printers) {
		this.symbols = symbols;
		this.printers = printers;
		this.index = index;
		this.n_printers = n_printers;
	}

	public void run() {
		int length = symbols.length();
			while (running) {
				if (index < n_printers-1) {
					printers[index+1] = new SynPrinter(symbols, printers, index+1, n_printers);
					printers[index+1].start();
				}
				for(int i = 0; i<N_NUMBERS/N_PORTIONS; i++) {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					break;
				}
				System.out.println((symbols.charAt(index)+"").repeat(10));
				}
				printers[index].interrupt();
				printers[index].setRunning(false);
			}
		}
}

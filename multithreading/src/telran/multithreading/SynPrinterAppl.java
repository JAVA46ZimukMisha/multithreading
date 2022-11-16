package telran.multithreading;

public class SynPrinterAppl {
	public static void main(String[] args) throws InterruptedException {
	final int N_PRINTERS = 3;
	String symbols = "123";
	SynPrinter[] printers = new SynPrinter[N_PRINTERS];
	int index = 0;
	printers[index] = new SynPrinter(symbols, printers, index, N_PRINTERS);
	printers[index].start();
	Thread.sleep(500);
	printers[index].interrupt();
	printers[index].setRunning(false);
}
}

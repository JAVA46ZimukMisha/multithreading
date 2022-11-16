package telran.multithreading;

import java.util.Scanner;

public class PrinterControllerAppl {
public static void main(String[] args) {
	String symbols = args.length == 0 ? "12345" : args[0];
	Printer printer = new Printer(symbols);
	printer.start();
	Scanner scanner = new Scanner(System.in);
	while(true) {
		String line = scanner.nextLine();
		if(line.equals("q")) {
			break;
		}
		printer.interrupt();
	}
	printer.setRunning(false);
}
}
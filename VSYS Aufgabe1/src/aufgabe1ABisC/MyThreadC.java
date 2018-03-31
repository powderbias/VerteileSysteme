package aufgabe1ABisC;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadC extends Thread {

	private static final int threadMax = 10;
	private static int runCount = 0;

	/*
	 * Die modifier static und synchronized ermöglichen den zugriff auf die
	 * displayCounter() methode erst nachdem diese durchlaufen ist. Durch den static
	 * modifier wird die methode für die anderen threads nicht freigegeben während
	 * dem sleep
	 */

	public static synchronized void displayCounter() {

		System.out.println(runCount + ": " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run() {

		while (runCount++ < 100) {

			this.displayCounter();

		}

	}

	public static void main(String[] args) {

		for (int i = 0; i < threadMax; i++) {
			new MyThreadC().start();
		}

	}

}

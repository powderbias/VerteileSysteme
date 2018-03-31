package aufgabe1ABisC;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadAundB extends Thread {

	private static final int threadMax = 10;
	private static int runCount = 0;

	public synchronized void displayCounter() {

		System.out.println(runCount + ": " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);

			/*
			 * Die Ausgabe erfolgt nebenläufig das nachdem die sleep method aufgerufen wurde
			 * die methode wieder freigegeben wird für andere threads
			 */

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
			new MyThreadAundB().start();
		}

	}

}

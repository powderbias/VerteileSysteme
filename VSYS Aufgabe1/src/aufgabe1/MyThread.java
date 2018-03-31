package aufgabe1;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread extends Thread {

	private static final int threadMax = 10;
	private static int runCount = 0;
	private static boolean belegt = false;

	public synchronized void displayCounter() {

			System.out.println(runCount + ": " + Thread.currentThread().getName());
			try {
				
				belegt=false;
				

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	

	public void run() {

		while (runCount++ < 100) {
			if(belegt == false) {
				
				this.displayCounter();	
			}
			
		}

	}

	public static void main(String[] args) {

		for (int i = 0; i < threadMax; i++) {
			new MyThread().start();
		}

	}

}

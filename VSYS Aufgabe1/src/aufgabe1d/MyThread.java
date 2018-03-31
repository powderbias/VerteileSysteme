package aufgabe1d;

import aufgabe1d.MyAccount;

public class MyThread extends MyAccount implements Runnable {

	private static final int threadMax = 10;
	private static int runCount = 0;
	MyAccount myAccount = new MyAccount();

	public void run() {
		/*synchronized auf das myaccount objekt bewirkt die datenkonsistenz*/
		
		synchronized(myAccount) {
			
			int anfang = myAccount.getWert();
			int zufallsZahl = (int) (Math.random() * 10) - 5;
			int ende = anfang + zufallsZahl;
			myAccount.setWert(ende);
			
			System.out.println(Thread.currentThread()+": "+anfang+" + "+zufallsZahl+" = "+ende);
			
			
		}

		

	}

	public static void main(String[] args) {

		for (int i = 0; i < threadMax; i++) {
			new Thread(new MyThread()).start();
		}

	}

}

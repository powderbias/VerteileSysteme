package aufgabe1d;

public class MyThread extends MyAccount {

	private static final int threadMax = 10;
	private static int runCount = 0;

	public void run() {

		while (runCount++ < 100) {
			System.out.println(runCount + ": " + Thread.currentThread().getName());

		}

	}

	public static void main(String[] args) {

		for (int i = 0; i < threadMax; i++) {
			//new MyThread().start();
		}

	}

}

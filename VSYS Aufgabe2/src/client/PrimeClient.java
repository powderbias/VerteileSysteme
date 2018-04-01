package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rm.requestResponse.*;

public class PrimeClient implements Runnable, Callable {
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 1234;
	private static final long INITIAL_VALUE = (long) 1e17;
	private static final long COUNT = 20;
	private static final String CLIENT_NAME = PrimeClient.class.getName();
	private static final boolean BLOCKING = true;
	private static final boolean MULTITHREADED = true;

	private static ExecutorService executor = Executors.newFixedThreadPool(2);

	private Component communication;
	String hostname;
	int port;
	long initialValue, count;
	boolean blocking;
	boolean multiThreaded;
	long value;

	public PrimeClient() {

	}

	public PrimeClient(String hostname, int port, long initialValue, long count, boolean blocking,
			boolean multiThreaded) {
		this.hostname = hostname;
		this.port = port;
		this.initialValue = initialValue;
		this.count = count;
		this.blocking = blocking;
		this.multiThreaded = multiThreaded;
	}

	public PrimeClient(String hostname, int port, long initialValue, long count, boolean blocking,
			boolean multiThreaded, Component communication) {
		this.hostname = hostname;
		this.port = port;
		this.initialValue = initialValue;
		this.count = count;
		this.blocking = blocking;
		this.multiThreaded = multiThreaded;
		this.communication = communication;

	}

	// former run method without threads
	public void processInputData() throws ClassNotFoundException, IOException, InterruptedException {
		communication = new Component();
		if (!this.blocking) {
			for (long i = initialValue; i < initialValue + count; i++)
				processNumber(i);

		} else if (this.multiThreaded) {
				processNumberBlockedAndThreaded();
		

		} else if (this.blocking) {
			for (long i = initialValue; i < initialValue + count; i++)
				processNumberBlocked(i);

		}

	}
	
	//aufgabe 2d 
	public void processNumberBlockedAndThreaded() throws InterruptedException{
		
		ExecutorService executor = Executors.newFixedThreadPool(1);

		for (long i = initialValue; i < initialValue + count; i++) {
			this.value=i;
			System.out.print(this.value+": ");
			Future<Boolean> future = executor.submit(receivePrime);
			while (!future.isDone()) {

				System.out.print("...");	
				Thread.sleep(2000);
			}

		}
		
		
		
		
	}
	
	
	
	
	 /**Mehode für Aufgabe 2c
	  * Der resquest wird abgeschickt und solange keine response kommt läuft
	  * der algorithmus in einer endlosschleife
	 * @param value
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void processNumber(long value) throws IOException, ClassNotFoundException {
		System.out.print(value + ":");
		communication.send(new Message(hostname, port, new Long(value)), true);

		try {
			Message messageReceived = communication.receive(port, false, true);
			Boolean isPrime;
			//message received returned nachdem der request abgeschickt wurde direkt null.
			//solange messageReceived kein response liefer ist es null
			while (messageReceived == null) {
				System.out.print("...");
				Thread.sleep(1000);
				messageReceived = communication.receive(port, false, true);

			}
			//messageReceived wird != null sobald die response angekommen ist
			if (messageReceived != null) {

				isPrime = (Boolean) messageReceived.getContent();
				System.out.println(isPrime ? "prime" : "not prime");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void processNumberBlocked(long value) throws IOException, ClassNotFoundException {
		communication.send(new Message(hostname, port, new Long(value)), false);

		Boolean isPrime = (Boolean) communication.receive(port, true, true).getContent();

		System.out.println(value + ": " + (isPrime ? "prime" : "not prime"));
	}



	Callable<Boolean> receivePrime = () -> {
			
		try {
			//send request to server
			communication.send(new Message(hostname, port, new Long(this.value)), false);
			//waiting for response
			Boolean isPrime = (Boolean) communication.receive(port, true, true).getContent();
			//if the response has been received it is written to console
			System.out.println((isPrime ? "prime" : "not prime"));

		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	};

	

	

	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		String hostname = HOSTNAME;
		int port = PORT;
		long initialValue = INITIAL_VALUE;
		long count = COUNT;
		boolean blocking = BLOCKING;
		boolean multiThreaded = MULTITHREADED;

		boolean doExit = false;

		String input;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to " + CLIENT_NAME + "\n");

		while (!doExit) {
			System.out.print("Server hostname [" + hostname + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				hostname = input;

			System.out.print("Server port [" + port + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				port = Integer.parseInt(input);

			// selection of request mode
			System.out.println("Request mode, true for blocking, false for non blocking [" + blocking + "]");
			input = reader.readLine();
			if (input.equals("true"))
				blocking = true;
			if (input.equals("false"))
				blocking = false;

			// selection of multithreading
			System.out.println("true = enable multithreading, false = disable multithreading[" + multiThreaded + "]");
			input = reader.readLine();
			if (input.equals("true"))
				multiThreaded = true;
			if (input.equals("false"))
				multiThreaded = false;

			System.out.print("Prime search initial value [" + initialValue + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				initialValue = Integer.parseInt(input);

			System.out.print("Prime search count [" + count + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				count = Integer.parseInt(input);

			new PrimeClient(hostname, port, initialValue, count, blocking, multiThreaded).processInputData();

			System.out.println("Exit [n]> ");
			input = reader.readLine();
			if (input.equals("y") || input.equals("j"))
				doExit = true;
		}
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

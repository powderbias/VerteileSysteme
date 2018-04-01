package server;

import java.io.IOException;
import java.util.logging.*;

import rm.requestResponse.*;

public class PrimeServer implements Runnable {
	private final static int PORT = 1234;
	private final static Logger LOGGER = Logger.getLogger(PrimeServer.class.getName());

	private Component communication;
	private int port = PORT;

	PrimeServer(int port) {
		communication = new Component();
		if (port > 0)
			this.port = port;

		// setLogLevel(Level.FINER);
	}

	private boolean primeService(long number) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (long i = 2; i < Math.sqrt(number) + 1; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

	void setLogLevel(Level level) {
		for (Handler h : LOGGER.getLogger("").getHandlers())
			h.setLevel(level);
		LOGGER.setLevel(level);
		LOGGER.info("Log level set to " + level);
	}

	 public void run() {
		 this.listen();
	 }	
		
		void listen() {
	
    	LOGGER.info("Listening on port "+port);
    	System.err.println("Listening on port"+port);
    	while (true) {
    		Long request=null;

    		LOGGER.info("Receiving ...");
    		try {
    			
    			request = (Long) communication.receive(port, true, false).getContent();
    			
    			if(request == null) {
    				request=11L;
    				
    			}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
    		LOGGER.info(request.toString()+" received.");

    		LOGGER.info("Sending ...");
		    try {
		    	communication.send(new Message("localhost",port,
		    			new Boolean(primeService(request.longValue()))),true);
		    	//new Thread(new PrimeServer(port)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    LOGGER.info("message sent.");
    	}
    }
	

	public static void main(String[] args) {
		int port = 0;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-port":
				try {
					port = Integer.parseInt(args[++i]);
				} catch (NumberFormatException e) {
					LOGGER.severe("port must be an integer, not " + args[i]);
					System.exit(1);
				}
				break;
			default:
				LOGGER.warning("Wrong parameter passed ... '" + args[i] + "'");
			}
		}

		new PrimeServer(port).listen();
	}
}

package cscie55.hw6;

import java.net.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;


public class Server {
	
	//create pool of threads that will service incoming requests
	//open server socket and listen to requests
	//when a request is received, create an ATMRunnable and add it to the queue

    private ServerSocket serverSocket;
    private ATM atmImplementation;
    private BufferedReader bufferedReader;
    
    private Set<ATMThread> threadPool;
    private LinkedList<ATMRunnable> requestQueue;    
    
    public Server (int port) throws java.io.IOException {
		
    	serverSocket = new ServerSocket(port);
		atmImplementation = new ATMImplementation();
		requestQueue = new LinkedList<ATMRunnable>();

		//create 5 threads and put in set
		threadPool = new HashSet<ATMThread>();
		
		for (int i = 0; i < 5; i++) {
			ATMThread worker = new ATMThread(requestQueue);
			worker.setName(String.valueOf(i));
			worker.start();
			threadPool.add(worker);
		}
		
    }

    /** serviceClient accepts a client connection and reads lines from the socket.
     *  Each line is handed to executeCommand for parsing and execution.
     * @throws InsufficientFundsException 
     */
    public void serviceClient () throws java.io.IOException, InsufficientFundsException, ATMException {
		System.out.println("Accepting clients now");
		Socket clientConnection = serverSocket.accept();
		
		// Arrange to read input from the Socket	
		InputStream inputStream = clientConnection.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		System.out.println("Client acquired on port #" + serverSocket.getLocalPort() + ", reading from socket");
		
		//Arrange to write result across Socket back to client- push this connection to the ATMRunnable
		OutputStream outputStream = clientConnection.getOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
	
		String commandLine;
		
		//push parsing string logic to ATMRunnable
		
		while((commandLine = bufferedReader.readLine()) != null) {
			
			try {
				//create a new new ATMRunnable with the command line
				ATMRunnable newRequest = new ATMRunnable(commandLine, atmImplementation, printStream);
			    
				//synchronize on the requestQueue 
				synchronized (requestQueue) {
			    	
			    	//add the request
			    	requestQueue.add(newRequest);
			    	
			    	//notify all threads waiting on the queue they need to start working
			    	requestQueue.notifyAll();
			    }
			} catch (Exception e) {
			    throw new RemoteException(e.getMessage());
			}
		}
			
    }

   
    public static void main(String argv[]) {
		int port = 1099;
		if(argv.length > 0) {
		    try {
		    	port = Integer.parseInt(argv[0]);
		    } catch (Exception e) { }
		}
		try {
		    Server server = new Server(port);
		    server.serviceClient();
		    System.out.println("Client serviced");
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
    }
}

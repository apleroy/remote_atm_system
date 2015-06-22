package cscie55.hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ATMThread extends Thread {
	
	private LinkedList<ATMRunnable> requestQueue;
	private ATMRunnable atmRunnable;
	
	public ATMThread(LinkedList<ATMRunnable> requestQueue) {
	    this.requestQueue = requestQueue;
	    atmRunnable = null;
    }
	
	public void run () {
		while (true) {

			synchronized (requestQueue) {
				
				while (requestQueue.isEmpty()) {
					//while the queue has nothing, this thread goes into a waiting state until notified
					
					try {	    
					    requestQueue.wait(); //the thread is told to wait on the queue until further notice
					} catch (InterruptedException e) {}
		    	}
				//if the queue is not empty - we are here - get the next unit of work
				//before doing the work - let all the other threads know they can check back for work
				    atmRunnable = requestQueue.getFirst();
				    requestQueue.remove();
				    requestQueue.notifyAll();

			}  //end of synchronized  
			try {
		    	//call run method of ATMRunnable outside of synchronized queue
	            atmRunnable.run();
	            
	        } catch (IOException e) {  
	            e.printStackTrace();
	        } catch (ATMException e) { 
		        e.printStackTrace();
	        } catch (InsufficientFundsException e) {
		        e.printStackTrace();
	        } 
		}	
	}

}
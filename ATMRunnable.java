package cscie55.hw6;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;



public class ATMRunnable {
	
	private String commandLine;
	private ATM atmImplementation;
	private PrintStream printStream;
	
	
	public ATMRunnable(String commandLine, ATM atmImplementation, PrintStream printStream) {
	    this.commandLine = commandLine;
	    this.atmImplementation = atmImplementation;
	    this.printStream = printStream;
    }


	public void run () throws IOException, ATMException, InsufficientFundsException { 
		
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		String commandAndParam[] = new String[tokenizer.countTokens()];
		
		int index = 0;
		
		while(tokenizer.hasMoreTokens()) {
		    commandAndParam[index++] = tokenizer.nextToken();
		}
		
		String command = commandAndParam[0];
		// Dispatch BALANCE request without further ado.
		if(command.equalsIgnoreCase(Commands.BALANCE.toString())) {
			try {    
				printStream.println(atmImplementation.getBalance());  // Write it back to the client	    
		    } catch (ATMException atmex) {
		    	System.out.println("ERROR: " + atmex);
		    }
	            
		}
		else {
			if(commandAndParam.length < 2) {
			    throw new ATMException("Missing amount for command \"" + command + "\"");
			}
			try {
			    float amount = Float.parseFloat(commandAndParam[1]);
			    if(command.equalsIgnoreCase(Commands.DEPOSIT.toString())) {
			    	atmImplementation.deposit(amount);	      
			    }
			    else if(command.equalsIgnoreCase(Commands.WITHDRAW.toString())) {
			    	atmImplementation.withdraw(amount);
			    } else {
			    	throw new ATMException("Unrecognized command: " + command);
			    }
			} catch (NumberFormatException nfe) {
			    throw new ATMException("Unable to make float from input: " + commandAndParam[1]);
			}
		}
	}

}

package cscie55.hw6;

public class Client {
    public static void main(String[] args) {
		try {
            // parse command line arguments
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            ATM atm = new ATMProxy(host, port);
            // get initial account balance
            System.out.println("Balance: "+atm.getBalance());
            
            // make $1000 deposit and get new balance    
            System.out.println(" Depositing: 1000");
            atm.deposit(1000);
            System.out.println("Balance: "+atm.getBalance());
            
            // make $250 withdrawal and get new balance
            System.out.println(" Withdrawing: 250");
            atm.withdraw(250);
            System.out.println("Balance: "+atm.getBalance());
            
            // make $750 withdrawal and get new balance
            System.out.println(" Withdrawing: 750");
            atm.withdraw(750);
            System.out.println("Balance: "+atm.getBalance());            
	            
		} catch (Exception ae) {
            System.out.println("An exception occurred while communicating with the ATM");
            ae.printStackTrace();
		}
    }
}

package cscie55.hw6;

public class ATMImplementation implements ATM {
	
	//this ATM implementation acts as a "controller" and passes data
	//to and from the Server instance of itself (which receives data from the client via PrintStream)
	//and to and from the Account Object which currently holds the business logic and conditions
	
	private Account account = new Account("000-Test-001");
	
	public void deposit(float amount) throws ATMException {
		account.deposit(amount);
	}
	
    public void withdraw(float amount) throws ATMException, InsufficientFundsException {
		try {
            account.withdraw(amount);
        } catch (InsufficientFundsException e) {
        	System.out.println(e);
        }
    }
    
    public Float getBalance() throws ATMException {
    	return account.balance();
    }
}

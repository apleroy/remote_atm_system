package cscie55.hw6;

public class Account {

	//this Account class holds the "business logic"
	//it prevents withdrawals where the withdrawal would put the account in overdraft
	//it prevents negative withdrawals or deposits
	//the account is defined with a String ID
	
	private float balance;
	private String id;
	
	public Account (String id) {
		this.id = id;
		balance = 0;
	}
	
	public synchronized Float balance () {
		return balance;
	}
	
	public synchronized void deposit (float amount) throws ATMException {
		if (amount < 0) {
			throw new ATMException("Cannot deposit a negative amount");
		} else {
			balance += amount;
		}
	}
	
	public synchronized void withdraw (float amount) throws InsufficientFundsException, ATMException {
		if (amount < 0) {
			throw new ATMException("Cannot deposit a negative amount");
		} else if ((this.balance - amount) < 0) {
			throw new InsufficientFundsException("Insufficient Funds in Customers Account to make their request");
		} else {
			balance -= amount;
		}
	}
	
}

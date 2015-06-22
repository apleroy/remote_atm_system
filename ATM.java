package cscie55.hw6;

public interface ATM {
    public void deposit(float amount) throws ATMException;
    public void withdraw(float amount) throws ATMException, InsufficientFundsException;
    public Float getBalance() throws ATMException;
}


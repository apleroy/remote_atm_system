# remote_atm_system
This application simulates the process involved in an ATM machine talking to a central Bank Server, which manages Accounts and supports standard Teller functions such as deposit, withdraw, balance check, and transfer.

This application simulates the process involved in an ATM machine talking to a central Bank Server, which manages Accounts and supports standard Teller functions such as deposit, withdraw, balance check, and transfer.

	This Application allows for the setup of multiple ATM's, which act as an intermediary between the Client application and the Bank Server.  In this setup, the Client can be thought of as the "neighborhood ATM" that you might use.  That Client has access to an associated ATM which takes the Client requests and transfers them to the Bank.  The Bank holds the Account data and balance of "your personal Bank account."  

	To demonstrate a real life scenario of the application's use and how some of its moving pieces work, we start by walking up to the ATM and requesting one of four actions (Check Balance, Withdraw, Deposit, or Transfer).  This takes place in the Client class of the application.  As a customer, we enter the account number and a PIN and then the operation and an amount if applicable.  At this point several things have happened behind the scenes:

	First, we are able to get access to the ATM object which handles the request through the use of the ATMServer.  This ATMServer creates an ATMFactory and registers it with the Naming Registry.  That ATMFactory will create ATM objects which can be accessed through the client.

	When an ATM is instantiated, it can access the Bank Object, which holds all of the accounts, and the Security Object, which holds all of the PIN information and supported operations for each Account.  Both the Bank and Security Objects were registered with the Naming Registry in the BankServer.

	Now that the Client, or "neighborhood ATM Machine", has an ATM instance, every request can be processed.  This happens through the following:

	First, the ATM request is bundled up into an AccountInfo object, which contains an Account Number and PIN.  The request is passed to the newly instantiated ATM along with the command and amount.  The ATM can talk to the Bank which has the account data.  To ensure that the proper account is debited or credited, the Security Object is passed the AccountInfo object.  First, the PIN number is verified for the specified account.  Next, the Security Object authorizes that the Client command can take place based on the predefined actions assigned to each Account.  If those both pass, the ATM will then pass the now authorized and authenticated request to the Bank Server who can act on the individual account and complete the request.

	The Client also implements the Remote ATMListener interface which allows for TransactionNotifications to be created and printed.  As a result, when a request is received from the client, the ATM creates a transaction notification message which is printed to the Server.

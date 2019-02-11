import java.io.Serializable;
import java.util.Date;

public abstract class Transaction implements Serializable{

	private long transNumber;
	private Date timeOfTrans;
	private double transAmount;
	private Account onAccount;
	protected Customer customer;
	protected String userMachine;

	protected Transaction(){

		this.transNumber = 1111111;
		this.timeOfTrans = new Date();
		this.transAmount = 0.0;
		this.onAccount = new SavingsAccount();
		customer = new Customer("Unassigned", "Unassigned", "Unassigned");
		userMachine = System.getProperty("user.name");

	}

	protected Transaction(long transNumber, double transAmount,
				 Account onAccount, Customer customer){

		this.transNumber = transNumber;
		this.timeOfTrans = new Date();
		this.transAmount = transAmount;
		this.onAccount = onAccount;
		this.customer = customer;
		userMachine = System.getProperty("user.name");

	}

	protected Transaction(long transNumber, double transAmount,
				 Account fromAccount, Account toAccount){

		this.transNumber = transNumber;
		this.timeOfTrans = new Date();
		this.transAmount = transAmount;
		this.onAccount = toAccount;
		this.customer = new Customer(fromAccount.getAccountName(), fromAccount.getPhone(), fromAccount.getAddress());
		userMachine = System.getProperty("user.name");

	}

	protected void setAmount(double amount){

		transAmount = amount;

	}

	protected void changeAccount(Account newAccount){

		double amount = transAmount;

		onAccount.makeWithdrawal(amount, customer);

		onAccount = newAccount;

		onAccount.deposit(amount, customer);

	}

	protected long getNumber(){ return transNumber; }

	protected double getAmount(){

		return transAmount;

	}

	protected Date getTime(){

		return timeOfTrans;

	}

	protected Account getAccount(){

		return onAccount;

	}

	protected String getCustomerName(){

		return customer.getName();

	}

	protected String getCustomerPhone(){

		return customer.getPhone();

	}

	@Override
	public String toString(){

		return "Transaction: " + getNumber() + "\n" + 
			"was carried by " + customer.getName() + "\n" + 
			"On " + getAccount().getAccountName() + " | " + getAccount().getAccountNumber() + "\n" +
			"with amount: N" + getAmount() + " On " + getTime() + "\n";
	}

}
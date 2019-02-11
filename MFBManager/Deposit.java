public class Deposit extends Transaction {

	public Deposit(){

		

	}

	public Deposit(long transNumber, double transAmount,
				 Account onAccount, Customer customer){

		super(transNumber, transAmount, onAccount, customer);

	}

	@Override
	public String toString(){

		return "Deposit: #" + getNumber() + "\n" + 
			"was carried by " + customer.getName() + "\n" + 
			"On " + getAccount().getAccountName() + " | " + getAccount().getAccountNumber() + "\n" +
			"with amount: N" + getAmount() + " On " + getTime() + "\n" + 
			"Using " + userMachine + "'s computer." + "\n" + 
			"Previous Balance: N" + (getAccount().getBalance() - getAmount()) + "\n" + 
			"New Balance: N" + getAccount().getBalance() + "\n";
	}

}
public class Transfer extends Transaction{

	private Account fromAccount;

	public Transfer(){

		

	}

	public Transfer(long transNumber, double transAmount,
				 Account fromAccount, Account toAccount){

		super(transNumber, transAmount, fromAccount, toAccount);

		this.fromAccount = fromAccount;

	}

	@Override
	public String toString(){

		return "Transfer: #" + getNumber() + "\n" + 
			"was carried by " + fromAccount.getAccountName() + " | " + fromAccount.getAccountNumber() + "\n" + 
			"On " + getAccount().getAccountName() + " | " + getAccount().getAccountNumber() + "\n" +
			"with amount: N" + getAmount() + " On " + getTime() + "\n" + 
			"Balance before was: N" + (fromAccount.getBalance() - getAmount()) + "\n" + 
			"New Balance: N" + fromAccount.getBalance() + "\n";
	}

}
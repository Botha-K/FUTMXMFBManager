import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DeskOfficer extends Banker{

	protected DeskOfficer(){

	}

	protected DeskOfficer(String name, String staffId, String password){

		super(name, staffId, password);

	}

	//constructor for a Savings Account
	public Account createSavingsAccount(String accountNumber, String accountName, 
				String pin, String email, String phone, 
				GregorianCalendar dob, String occupation, String maritalStatus, 
				String address, String title, char gender, String NIN_Number, 
				double balance, boolean restriction){

		Account savingsAccount = new SavingsAccount(accountNumber, accountName, pin, email, 
					phone, dob, occupation, maritalStatus, address,
					 title, gender, NIN_Number, balance, restriction);

		return savingsAccount;

	}

	//Constructor for a Current Account
	public Account createCurrentAccount(String accountNumber, String accountName, 
				String pin, String email, String phone, 
				GregorianCalendar dob, String occupation, String maritalStatus, 
				String address, String title, char gender, String NIN_Number, 
				double balance, boolean restriction){

		Account currentAccount = new CurrentAccount(accountNumber, accountName, pin, email, 
					phone, dob, occupation, maritalStatus, address,
					 title, gender, NIN_Number, balance, restriction);

		return currentAccount;

	}

	public Account restrictAccount(Account account){

		account.setRestriction();

		return account;

	}

	public Account removeRestriction(Account account){

		account.removeRestriction();

		return account;


	}

	//this method sets the account object passed to it to a null value
	//and returns the account
	//the caller can decide what to do with the returned value
	public Account closeAccount(Account account){

		account = null;
		return account;

	}

	@Override
	public String toString(){

		return "Name: " + getName() + ", Staff ID: " + getStaffId() + "\n";

	}
	
}
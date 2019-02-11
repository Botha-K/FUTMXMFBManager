import java.util.GregorianCalendar;
import java.util.Date;

public class SavingsAccount extends Account{

	private static final double MAXIMUM_AMOUNT = 100000000.00;
	private static final double MINIMUM_AMOUNT = 0.00;

	public SavingsAccount(){

	}

	public SavingsAccount(String accountNumber, String accountName, 
				String pin, String email, String phone, 
				GregorianCalendar dob, String occupation, String maritalStatus, 
				String address, String title, char gender, String NIN_Number, 
				double balance, boolean restriction){

		super(accountNumber, accountName, pin, email, phone, dob, occupation, maritalStatus,
			 address, title, gender, NIN_Number, balance, restriction);

	}

	@Override
	public String getAccountType(){ return "Savings"; }

	public static double getMaximumAmount(){ return MAXIMUM_AMOUNT; }

	@Override
	public boolean deposit(double amount, Customer customer){

		if((getBalance() + amount) > SavingsAccount.getMaximumAmount()){

			printer.println("Maximum Amount receivable exceeded");

			return false;

		}
		else if(amount > 0){

			balance += amount;

			transactions.add(new Deposit(++transactionNumber, amount, this, customer));
			lastTransactionDate = new Date();

			return true;
		}
		else{

			return false;

		}

	}

	@Override
	public boolean makeWithdrawal(double amount, Customer customer){

		if(!restriction){

			if(getBalance() < amount){

				printer.println("Minimum account balance reached");
				return false;

			}
			else if(amount < 1000){

				printer.println("Enter an amount in multiples of N1000");
				return false;
			}
			else{

				balance -= amount;

				transactions.add(new Withdrawal(++transactionNumber, amount, this, customer));
				lastTransactionDate = new Date();

				return true;

			}

		}
		else{

			printer.println("Account has been restricted.");
			return false;

		}

	}

	@Override
	protected boolean transfer(double amount, Account toAccount){

		if(!restriction){

			if(this.equals(toAccount)){

				printer.println("You cannot make a transfer to yourself");
				return false;

			}

			if((getBalance() - amount) >= MINIMUM_AMOUNT){

				if(toAccount.deposit(amount, new Customer(getAccountName(), getPhone(), getAddress()))){

					balance -= amount;
					lastTransactionDate = new Date();
					transactions.add(new Transfer(++transactionNumber, amount, this, toAccount));
					printer.println("You transfered N" + amount + " to " + toAccount.getAccountName());
					return true;

				}

			}
			else if(amount < 100){

				printer.println("Minimum transfer amount");

			}
			else{

				printer.println("Transfer error");

			}

		}
		else{

			printer.println("Account has been restricted");

		}

		return false;

	}

	public String toString(){

		return "Account Name: " + getAccountName() + "\n" +
			"Account Number: " + getAccountNumber() + "\n" + 
			"email: " + getEmail() + "\n" + 
			"phone: " + getPhone() + "\n" + 
			"Date-of-Birth: " + getDob().get(Calendar.YEAR) + "-" + getDob().get(Calendar.MONTH) + "-" + getDob().get(Calendar.DATE) + "\n" + 
			"Occupation: " + getOccupation() + "\n" + 
			"Marital Status: " + getStatus() + "\n" + 
			"Address: " + getAddress() + "\n" + 
			"Title: " + getTitle() + "\n" +
			"Gender: " + getGender() + "\n" + 
			"NIN: " + getNIN() + "\n" + 
			"Current Balance: " + getBalance() + "\n" +
			"Account Type: " + getAccountType() + "\n" +
			"Last transaction was on: " + ((getLastTransactionDate() != null)
				 ? lastTransactionDate : "No transaction was ever carried on this account") + "\n" + 
			"Account Restrictions ?: " + (this.restriction ? "On" : "None") + "\n";

	}

}
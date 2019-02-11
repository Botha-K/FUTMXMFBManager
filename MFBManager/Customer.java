import java.io.Serializable;
import java.io.PrintWriter;
import java.util.List;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;

public class Customer implements Serializable{

	private String name;
	private String phone;
	private String address;
	private transient PrintWriter printer;

	public Customer(){
	}

	public Customer(String name, String phone, String address){

		this.name = name;
		this.phone = phone;
		this.address = address;
		this.printer = new PrintWriter(System.out, true);

	}

	public void setName(String name){

		this.name = name;

	}

	public String getName(){

		return name;

	}

	public void setPhone(String phone){

		this.phone = phone;

	}

	public String getPhone(){

		return phone;

	}

	public void setAddress(String address){

		this.address = address;

	}

	public String getAddress(){

		return address;

	}

	public void makeDeposit(double amount, HashMap<String, Account> accounts, String accountNumber){

		Set<Map.Entry<String, Account>> setOfAccounts = accounts.entrySet();

		Account account = null;

		for(Map.Entry<String, Account> a : setOfAccounts){

			if(a.getKey().equals(accountNumber)){

				account = a.getValue();
				account.deposit(amount, this);
				return;

			}

		}

		printer.println("No such account in our system");

	}

	public Account makeWithdrawal(double amount, Account account){

			account.makeWithdrawal(amount, this);
			return account;

	}

	public boolean transferFund(double amount, Account fromAccount, Account toAccount){

		return fromAccount.transfer(amount, toAccount);

	}

	public void getStatement(Collection<Account> accounts, String accountNum, String pin){

		for(Account account : accounts){

			if(accountNum.equalsIgnoreCase(account.getAccountName()) && pin.equals(account.getPin())){

				account.printTransactions();

				break;

			}
			else{

				printer.println("No such account in existence");

			}

		}

	}

	public double checkBalance(Collection<Account> accounts, String accountNumber, String pin){

		double balance = 0.00;

		for(Account account : accounts){

			if(accountNumber.equals(account.getAccountNumber()) && pin.equals(account.getPin())){

				balance = account.getBalance();
				break;

			}

		}

		return balance;

	}

	public void changePassword(Collection<Account> accounts, String accountNumber, String oldPin, String newPin){

		for(Account account : accounts){

			if(accountNumber.equals(account.getAccountNumber()) && oldPin.equals(account.getPin())){

				if(!account.getPin().equals(newPin)){

					account.setPin(newPin);

				}

				break;

			}

		}

	}

	@Override
	public String toString(){

		return "Customer: " + getName() + "\n" + 
			"Phone: " + getPhone() + "\n" + 
			"Address: " + getAddress() + "\n" ;

	}

}
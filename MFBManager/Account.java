import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;

public abstract class Account implements Serializable{

	private String accountNumber;
	private String accountName;
	private String pin;
	private String email;
	private String phone;
	private GregorianCalendar dob;
	private String occupation;
	private String maritalStatus;
	private String address;
	private String title;
	private char gender;
	private String NIN_Number;
	protected double balance;
	private String accountType;
	protected Date lastTransactionDate;
	protected ArrayList<Transaction> transactions;
	protected int transactionNumber;
	protected transient Customer currentCustomer;
	protected static String userMachine;
	protected Date dateCreated;
	protected boolean restriction;

	public transient PrintWriter printer;

	protected Account(){

		accountNumber = "0000000000";
		accountName = "Unassigned";
		pin = "1234abcdef";
		email = "name@gmail.com";
		phone = "08111111111";
		occupation = "Unassigned";
		maritalStatus = "Unassigned";
		address = "Unassigned";
		title = "Unassigned";
		gender = 'N';
		NIN_Number = "Unassigned";
		balance = 0.00;
		printer = new PrintWriter(System.out, true);
		userMachine = System.getProperty("user.name");
		restriction = false;

	}

	protected Account(String accountNumber, String accountName, 
				String pin, String email, String phone, 
				GregorianCalendar dob, String occupation, String maritalStatus, 
				String address, String title, char gender, String NIN_Number, 
				double balance, boolean restriction){

		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.pin = pin;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
		this.occupation = occupation;
		this.maritalStatus = maritalStatus;
		this.address = address;
		this.title = title;
		this.gender = gender;
		this.NIN_Number = NIN_Number;
		this.balance = balance;
		this.dateCreated = new Date();
		printer = new PrintWriter(System.out, true);
		transactions = new ArrayList<>();
		userMachine = System.getProperty("user.name");
		this.restriction = restriction;

	}

	protected void setAccountName(String newName){

		this.accountName = newName;

	}

	protected String getAccountName(){

		return accountName;

	}

	protected String getAccountNumber(){

		return accountNumber;

	}

	protected void setPin(String newPin){

		if(!restriction){

			this.pin = newPin;

		}
		else{

			printer.println("Your Account has been restricted. Please contact your bank");

		}

	}

	protected String getPin(){

		return pin;

	}

	protected void setEmail(String newEmail){

		this.email = newEmail;

	}

	protected String getEmail(){

		return email;

	}

	protected void setPhone(String newPhone){

		this.phone = newPhone;

	}

	protected String getPhone(){

		return phone;

	}

	protected void setDob(GregorianCalendar newDob){

		this.dob = newDob;

	}

	protected GregorianCalendar getDob(){

		return dob;

	}

	protected void setOccupation(String occupation){

		this.occupation = occupation;

	}

	protected String getOccupation(){

		return occupation;

	}

	protected void setStatus(String status){

		this.maritalStatus = status;

	}

	protected String getStatus(){

		return maritalStatus;

	}

	protected void setAddress(String address){

		this.address = address;

	}

	protected String getAddress(){

		return address;

	}

	protected void setTitle(String title){

		this.title = title;

	}

	protected String getTitle(){

		return title;

	}

	protected void setGender(char gender){

		this.gender = gender;

	}

	protected char getGender(){

		return gender;

	}

	protected void setNIN(String nin){

		this.NIN_Number = nin;

	}

	protected String getNIN(){

		return NIN_Number;

	}

	protected double getBalance(){

		if(!restriction){

			return balance;

		}
		else{

			return 0.00;

		}

	}

	protected boolean getRestriction(){

		return restriction;

	}

	protected void setRestriction(){

		restriction = true;

	}

	protected void removeRestriction(){

		restriction = false;

	}

	protected Customer getCustomer(){

		return currentCustomer;

	}

	protected abstract boolean deposit(double amount, Customer customer);

	protected abstract boolean makeWithdrawal(double amount, Customer customer);

	protected abstract boolean transfer(double amount, Account toAccount);

	public Date getLastTransactionDate(){

		return lastTransactionDate;

	}

	public void printTransactions(){

		for(Transaction transaction : transactions){

			System.out.println(transaction);

		}

	}

	public ArrayList<Transaction> getTransactions(){

		return new ArrayList<Transaction>(transactions);

	}

	protected abstract String getAccountType();

	@Override
	public boolean equals(Object o){

		Account testAccount = (Account) o;

		return this.getAccountNumber().equals(testAccount.getAccountNumber());

	}

	@Override
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
			"Account Type: " + "Bank Account" + "\n" +
			"Last transaction was on: " + getLastTransactionDate();

	}

}
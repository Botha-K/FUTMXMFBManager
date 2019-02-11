import java.io.*;
import java.util.*;

public class MainApplication {

	private static PrintWriter printer;
	private static Console console;
	private static File accountRecords;
	private static File bankerRecords;
	private static File journal;
	private static HashMap<String, Account> accountRegister;
	private static HashMap<String, Banker> bankerRegister;
	private static ArrayList<Transaction> journalRegister;
	private static Customer customer;
	private static BankManager bankManager;
	private static DeskOfficer deskOfficer;
	private static Account account;
	private static final String ADMIN_NAME = "Backspace";
	private static final String ADMIN_PASSWORD = "thenoiseofthunder";

	public static void main(String args[]){

		accountRegister = new HashMap<>();
		bankerRegister = new HashMap<>();
		journalRegister = new ArrayList<>();

		accountRecords = new File("AccountRecords.ser");
		bankerRecords = new File("BankerRecords.ser");
		journal = new File("Journal.ser");

		printer = new PrintWriter(System.out, true);
		console = System.console();
		
		//Ensure data is loaded into memory before using system.
		if(!load()){

			printer.println("Failed to load the Database... Exiting");
			return;

		}

		printer.println("Welcome to FUTMinna MFBank.");

		do{

			customer = null;
			deskOfficer = null;
			account = null;
			mainMenu();
		}while(true);

	}

	public static void mainMenu(){

		printer.println("Make a selection.");
		printer.println("1: I'm a Customer");
		printer.println("2: I'm a Bank Manager");
		printer.println("3: I'm a DeskOfficer");

		char selection = console.readLine().charAt(0);

		switch(selection){

			case '1':
				customerMenu();
			break;

			case '2':
				bankManagerMainMenu();
			break;

			case '3':
				deskOfficerMenu();
			break;

			default:
				printer.println("Invalid Selection");
				return;

		}
		

	}

	public static void customerMenu(){

		char selection = '\n';

		do{

			printer.println("1: Make Deposit");
			printer.println("2: Make Withdrawal");
			printer.println("3: Make Transfer");
			printer.println("4: Bank Statement");
			printer.println("5: CheckBalance");
			printer.println("6: Change Password");
			printer.println("7: End");

			selection = console.readLine().charAt(0);

			switch(selection){

				case '1':
					deposit();
				break;

				case '2':
					withdrawal();
				break;

				case '3':
					transfer();
				break;

				case '4':
					bankStatement();
				break;

				case '5':
					checkBalance();
				break;

				case '6':
					changePassword();
				break;

				case '7':
				break;

				default:
					printer.println("Invalid Input");

			}

		}while(selection != '7');

	}

	public static void deposit(){

		String myAccountNumber;
		double amount;
		String pin;
		String toAccountNumber;

		printer.println("Enter your account Number: ");
		myAccountNumber = console.readLine().trim();
		printer.println("Enter your pin:");
		pin = new String(console.readPassword());
		printer.println("Enter amount to deposit: ");
		amount = Double.parseDouble(console.readLine().trim());

		printer.println("Receiving account number: ");
		toAccountNumber = console.readLine().trim();

		Account customerAccount = findAccount(myAccountNumber);
		Account receivingAccount = findAccount(toAccountNumber);

		if(customerAccount == null){

			printer.println("You need to have an account with us before you can deposit.");
			return;

		}

		if(receivingAccount == null){

			printer.println("Receipients account does not exist.");
			return;

		}

		if(pin.trim().equals(customerAccount.getPin())){

			customer = new Customer(customerAccount.getAccountName(), 
				customerAccount.getPhone(), customerAccount.getAddress());

			customer.makeDeposit(amount, accountRegister, toAccountNumber);
			accountRegister.put(toAccountNumber, findAccount(toAccountNumber));
			save();
			customer = null;

		}
		else{

			printer.println("Invalid pin");
			return;

		}

	}

	public static void withdrawal(){

		String accountNumber;
		String pin;
		double amount;

		printer.println("Enter your account number: ");
		accountNumber = console.readLine().trim();
		char[] password = console.readPassword("Enter your pin: ");

		pin = new String(password);

		Account customerAccount = findAccount(accountNumber);
		

		if(customerAccount != null && pin.equals(customerAccount.getPin())){

			customer = new Customer(customerAccount.getAccountName(),
					 customerAccount.getPhone(), customerAccount.getAddress());

			amount = Double.parseDouble(console.readLine("Enter amount: ").trim());

			customerAccount = customer.makeWithdrawal(amount, customerAccount);
			accountRegister.put(accountNumber, customerAccount);
			save();
			customerAccount = null;
		}
		else{

			printer.println("Invalid pin");

		}

		account = null;

	}

	public static void transfer(){

		String myAccountNumber;
		String receiverAccountNumber;
		Account fromAccount;
		Account toAccount;
		String pin;
		double amount;

		printer.println("Enter your account number: ");
		myAccountNumber = console.readLine().trim();
		fromAccount = findAccount(myAccountNumber);

		if(fromAccount == null){

			printer.println("Your account could not be found");
			fromAccount = null;
			toAccount = null;
			customer = null;
			return;

		}

		char[] password = console.readPassword("Enter your pin: ");
		pin = new String(password);

		if(!pin.equals(fromAccount.getPin())){

			printer.println("Invalid pin");
			fromAccount = null;
			toAccount = null;
			customer = null;
			return;

		}

		receiverAccountNumber = console.readLine("Enter receipients account number: ").trim();
		toAccount = findAccount(receiverAccountNumber);

		amount = Double.parseDouble(console.readLine("Enter amount: ").trim());

		if(toAccount != null){

			customer = new Customer(fromAccount.getAccountName(), fromAccount.getPhone(), fromAccount.getAddress());
			if(customer.transferFund(amount, fromAccount, toAccount)){

				accountRegister.put(myAccountNumber, fromAccount);
				accountRegister.put(receiverAccountNumber, toAccount);
				save();
			}

		}
		else{

			printer.println("Receipients account could not be found");

		}

		fromAccount = null;
		toAccount = null;
		customer = null;

	}

	public static void bankStatement(){

		String accountNumber;
		String pin;

		accountNumber = console.readLine("Enter your account number: ").trim();

		char[] password = console.readPassword("Enter your pin: ");

		pin = new String(password);

		Account account = findAccount(accountNumber);

		if(account != null){

			if(account.getPin().equals(pin.trim())){

				account.printTransactions();

			}
			else{

				printer.println("Invalid pin");
				account = null;
				return;

			}

		}
		else{

			printer.println("Account not found.");

		}

		account = null;

	}

	public static void checkBalance(){

		String accountNumber;
		String pin;
		Account account = null;

		accountNumber = console.readLine("Enter your account number: ").trim();

		char[] password = console.readPassword("Enter your pin: ");

		pin = new String(password);

		account = findAccount(accountNumber);

		if(account != null){

			if(account.getPin().equals(pin.trim())){

				printer.println("Balance: " + account.getBalance());

			}
			else{

				printer.println("Invalid pin");

			}

		}
		else{

			printer.println("No such account");

		}

		account = null;

	}

	public static void changePassword(){

		String accountNumber;
		String oldPin;
		String newPin;

		accountNumber = console.readLine("Enter your account number: ").trim();

		if(findAccount(accountNumber) == null){

			printer.println("No such account");
			return;

		}

		Account account = findAccount(accountNumber);

		char[] password = console.readPassword("Enter old pin: ");

		oldPin = new String(password);

		if(account != null){

			if(account.getPin().equals(oldPin.trim())){

				password = console.readPassword("Enter new pin: ");

				newPin = new String(password);
				newPin = newPin.trim();
				String confirmPassword = console.readLine("Confirm new pin: ").trim();

				if(newPin.equals(confirmPassword)){

					account.setPin(newPin);
					accountRegister.put(account.getAccountNumber(), account);
					save();
					account = null;

				}
				else{

					do{

						printer.println("Mismatch. try again");
						password = console.readPassword("Enter new pin: ");

						newPin = new String(password);
						newPin = newPin.trim();
						confirmPassword = console.readLine("Confirm new pin: ").trim();

					}while(newPin.compareTo(confirmPassword) != 0);

					account.setPin(newPin);
					accountRegister.put(account.getAccountNumber(), account);
					save();
					account = null;

				}

			}
			else{

				printer.println("Incorrect pin");
				account = null;
				return;

			}

		}
		else{

			printer.println("Account not found");

		}

	}

	public static void bankManagerMainMenu(){

		char input;

		do{

			printer.println("1. Sign in: ");
			printer.println("2. Create account: ");
			printer.println("3. Exit: ");

			input = console.readLine().charAt(0);

			switch(input){

				case '1':
					signIn();
				break;

				case '2':

					createBankManager();
				
				break;

				case '3':
					printer.println("Exiting...");
					return;

				default:
					printer.println("Invalid Selection");
				break;

			}

		}while(input != '3');

	}

	public static void signIn(){

		String staffId;
		String pin;

		staffId = console.readLine("Enter your staffId: ").trim();
		pin = new String(console.readPassword("Enter password: ")).trim();

		Banker manager = findBanker(staffId);

		if(manager == null){

			printer.println("No manger with such staff Id");
			return;

		}

		if(pin.equals(manager.getPassword())){

			if(manager instanceof BankManager){

				bankManager = (BankManager) manager;
				managerMenu();

			}

		}
		else{

			printer.println("Invalid username/password combination!");

		}

	}

	public static void managerMenu(){

		char input;

		printer.println("You are signed in as - " + bankManager.getName().toUpperCase());

		do{

			printer.println("Make a selection");
			printer.println("1. Create account for new Desk officer");
			printer.println("2. View list of all Desk Officers");
			printer.println("3. View list of all Bank Accounts");
			printer.println("4. View Transaction Journal");
			printer.println("5. Sign out");

			input = console.readLine().charAt(0);

			switch(input){

				case '1':
					createDeskOfficer();
				break;

				case '2':
					viewDeskOfficersList();
				break;

				case '3':
					viewAccountsList();
				break;

				case '4':
					printer.println("1. Select this option to generate journal for a specific account:");
					printer.println("2. Select this to print journal for all accounts: ");
					char c = console.readLine().charAt(0);

					if(c == '1'){

						String acNumber = console.readLine("Enter account number: ").trim();
						account = findAccount(acNumber);

						if(account != null){

							viewJournal(account);

						}
						else{

							printer.println("This account does not exist!");

						}

						account = null;

					}
					else if(c == '2'){

						viewJournal();

					}
					else{

						printer.println("Invalid input");

					}

				break;

				case '5':
					printer.println("Signing you out...");
				break;

				default:
					printer.println("Invalid input");
				break;

			}

		}while(input != '5');

		bankManager = null;
		

	}

	public static void createDeskOfficer(){

		String name;
		String staffId;
		String password;

		name = console.readLine("Enter a name for new desk officer: ").trim();
		staffId = console.readLine("Enter a staff id for new desk officer: ").trim();

		while(findBanker(staffId) != null){

			printer.println("Someone with that staffId already exists. pick another: ");
			staffId = console.readLine("Enter a staff id for new desk officer: ").trim();

		}

		password = new String(console.readPassword("Enter a password for new desk officer: "));
		String confirmPassword = new String(console.readPassword("Confirm password: "));

		while(!password.equals(confirmPassword)){

			printer.println("Password mismatch! try again.");
			password = new String(console.readPassword("Enter a password for new desk officer: "));
			confirmPassword = new String(console.readPassword("Confirm password: "));

		}

		deskOfficer = bankManager.createDeskOfficer(name, staffId, password);
		bankerRegister.put(staffId, deskOfficer);
		printer.println("A new Desk Officer has just been created with name: " + name + 
				", staff ID: " + staffId);
		save();
		deskOfficer = null;

	}

	public static void viewDeskOfficersList(){

		Set<Map.Entry<String, Banker>> bankers = bankerRegister.entrySet();

		for(Map.Entry<String, Banker> banker : bankers){

			if(banker.getValue() instanceof DeskOfficer){

				deskOfficer = (DeskOfficer) banker.getValue();

				printer.println(deskOfficer);

			}

		}

		deskOfficer = null;

	}

	public static void viewAccountsList(){

		Set<Map.Entry<String, Account>> accounts = accountRegister.entrySet();

		for(Map.Entry<String, Account> account : accounts){

			printer.println(account);

		}

	}

	public static void viewJournal(Account account){

		account.printTransactions();

	}

	public static void viewJournal(){

		Set<Map.Entry<String, Account>> accounts = accountRegister.entrySet();

		for(Map.Entry<String, Account> entry : accounts){

			entry.getValue().printTransactions();

		}

	}

	public static void createBankManager(){

		String adminName;
		String pin;

		adminName = console.readLine("Enter admin username: ").trim();
		pin = new String(console.readPassword("Enter admin password: "));

		if(adminName.equals(ADMIN_NAME) && pin.equals(ADMIN_PASSWORD)){

			String name;
			String staffId;
			String password;
			String confirmPassword;

			name = console.readLine("Enter a name for new bank manager: ").trim();
			staffId = console.readLine("Enter an ID for new bank manager: ").trim();
			
			do{

				password = new String(console.readPassword("Enter a password for new bank manager: "));
				confirmPassword  = new String(console.readPassword("confirm password: "));

			}while(!password.equals(confirmPassword));

			bankManager = new BankManager(name, staffId, password);
			bankerRegister.putIfAbsent(staffId, bankManager);
			save();
			bankManager = null;

		}
		else{

			printer.println("Incorrect");

		}

	}

	public static Account findAccount(String accountNumber){

		Set<Map.Entry<String, Account>> accounts = accountRegister.entrySet();

		for(Map.Entry<String, Account> entry : accounts){

			if(accountNumber.equals(entry.getKey())){

				return entry.getValue();

			}

		}

		return null;

	}

	public static Banker findBanker(String staffId){

		Set<Map.Entry<String, Banker>> bankers = bankerRegister.entrySet();

		for(Map.Entry<String, Banker> entry: bankers){

			if(staffId.trim().compareToIgnoreCase(entry.getKey().trim()) == 0){

				return entry.getValue();

			}

		}

		return null;

	}

	public static void deskOfficerMenu(){

		String staffId;
		String pin;

		char option;

		printer.println("1. Select this option to login");
		printer.println("2. Exit");

		option = console.readLine().charAt(0);

		if(option == '1'){
		}
		else if(option == '2'){

			return;

		}
		else{

			printer.println("Invalid Input");

		}

		staffId = console.readLine("Enter your staff ID: ").trim();
		pin = new String(console.readPassword("Enter your password: "));

		deskOfficer = null;

		if(findBanker(staffId) instanceof DeskOfficer){

			deskOfficer = (DeskOfficer) findBanker(staffId);

		}

		if(deskOfficer != null){

			if(pin.equals(deskOfficer.getPassword())){

				deskOfficerMainMenu();
				deskOfficer = null;

			}
			else{

				printer.println("Invalid pin");

			}
			

		}
		else{

			printer.println("No such account in our records!");

		}

	}

	public static void deskOfficerMainMenu(){

		printer.println("You are logged in as " + deskOfficer.getName().toUpperCase());

		char c;

		do{

			printer.println("Make a selection below");
			printer.println();
			printer.println("0. Remove Account Restriction");
			printer.println("1. View Journal");
			printer.println("2. Create Account");
			printer.println("3. Restrict Account");
			printer.println("4. Close Account");
			c = console.readLine("5. Log out").charAt(0);

			switch(c){

				case '1':
					printer.println("1. Select this option to generate journal for a specific account:");
					printer.println("2. Select this to print journal for all accounts: ");
					c = console.readLine().charAt(0);

					if(c == '1'){

						String acNumber = console.readLine("Enter account number: ").trim();
						account = findAccount(acNumber);

						if(account != null){

							viewJournal(account);

						}
						else{

							printer.println("This account does not exist!");

						}

						account = null;

					}
					else if(c == '2'){

						viewJournal();

					}
					else{

						printer.println("Invalid input");

					}

				break;

				case '2':

					createAccount();
				break;

				case '3':

					String accountNumber = console.readLine("Please enter Account Number of the account to be restricted: ").trim();
					account = findAccount(accountNumber);
					account = deskOfficer.restrictAccount(account);
					accountRegister.put(accountNumber, account);
					save();
					account = null;

				break;

				case '4':
					String closeAccountNumber = console.readLine("Enter Account Number of the account to be closed. Note once an account is closed it cannot be retrieved," + 
								" and the account number cannot be reassigned to another account. proceed with caution!").trim();

					account = findAccount(closeAccountNumber);
					deskOfficer.closeAccount(account);
					printer.println("Account closed");
					accountRegister.put(closeAccountNumber, account);
					save();
					account = null;

				break;

				case '5':
					deskOfficer = null;
					return;

				case '0':

					String acctNum = console.readLine("Please enter Account Number of the account to be unlocked: ").trim();
					account = findAccount(acctNum);
					account = deskOfficer.removeRestriction(account);
					accountRegister.put(acctNum, account);
					save();
					account = null;

				break;

				default:
					printer.println("Invalid selection");

			}

		}while(c != 5);

	}

	public static void createAccount(){

		char c = console.readLine("Select account type: '1' for Savings Account, '2' for Current Account.").charAt(0);

		String accountName = console.readLine("Enter your full name. Surname first: ").trim();
		String title = console.readLine("Enter your title. 'Mr' or 'Mrs': ").trim();
		String email = console.readLine("E-mail address: ").trim();
		String phone = console.readLine("Phone number: ").trim();
		String dob = console.readLine("Date of birth in this format '1995-6-14': ").trim();
		char gender = console.readLine("Enter your gender. 'M' for male, 'F' for female: ").trim().toUpperCase().charAt(0);
		String accountNumber = "023" + phone.substring(2);
		String occupation = console.readLine("Enter your occupation: ").trim();
		String maritalStatus = console.readLine("Enter your marital status e.g 'Married', 'Divorced', or 'Single': ").trim();
		String address = console.readLine("Enter your home address: ").trim();
		String nin = console.readLine("Enter your National Identification Number: ").trim();
		int year = 0;
		int month = 0;
		int day = 0;
		GregorianCalendar date;

		try{

			year = Integer.parseInt(dob.substring(0, dob.indexOf('-')));
			month = Integer.parseInt(dob.substring(dob.indexOf('-') + 1, dob.lastIndexOf('-'))) - 1;
			day = Integer.parseInt(dob.substring(dob.lastIndexOf('-') + 1));

			date = new GregorianCalendar(year, month, day);

		}catch(NumberFormatException e){

			printer.println("'" + year + "' is not a valid year");
			printer.println("'" + month + "' is not a valid month");
			printer.println("'" + day + "' is not a valid day");

			return;

		}

		String pin = new String(console.readPassword("Enter a pin: "));
		String confirmPin = new String(console.readPassword("Confirm pin: "));

		if(!pin.equals(confirmPin)){

			printer.println("both pins do not match!");
			return;

		}

		if(c == '1'){

			account = deskOfficer.createSavingsAccount(accountNumber, accountName, pin, email, phone,
					date, occupation, maritalStatus, address, title, gender, nin, 0.00, false);
			accountRegister.put(accountNumber, account);
			printer.println("Account created successfully");
			save();

		}
		else if(c == '2'){

			account = deskOfficer.createCurrentAccount(accountNumber, accountName, pin, email, phone,
					date, occupation, maritalStatus, address, title, gender, nin, 0.00, false);
			accountRegister.put(accountNumber, account);
			printer.println("Account created successfully");
			save();

		}
		else{

			printer.println("The account type you selected is invalid.");

		}

		account = null;

	}

	private static boolean load(){

		try{

		if(!accountRecords.exists())
			accountRecords.createNewFile();

		if(!bankerRecords.exists())
			bankerRecords.createNewFile();

		if(!journal.exists())
			journal.createNewFile();

		}
		catch(IOException e){

			printer.println("IO error occurred: " + e);
			return false;

		}

		//Try loading account, banker, journal file into their respective hashmaps
		try(ObjectInputStream readAccounts = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(accountRecords)));
			ObjectInputStream readBankers = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(bankerRecords)));
			ObjectInputStream readJournals = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(journal)));){

			accountRegister = (HashMap) readAccounts.readObject();
			bankerRegister = (HashMap) readBankers.readObject();
			journalRegister = (ArrayList) readJournals.readObject();

		}catch(EOFException e){

			printer.println("Database is empty.");
			return true;

		}
		catch(IOException e){

			printer.println("IOError: loading accounts/bankers/journal: " + e);

			return false;

		}
		catch(ClassNotFoundException e){

			printer.println("Class cast exception HashMap<Object> -> HashMap<Account>:" + e);

			return false;

		}

		return true;

	}

	private static boolean save(){

		try(ObjectOutputStream writeAccounts = new ObjectOutputStream(
			new BufferedOutputStream(new FileOutputStream(accountRecords, false)));
			ObjectOutputStream writeBankers = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(bankerRecords, false)));
			ObjectOutputStream writeJournal = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(journal, false)));){

			writeAccounts.writeObject(accountRegister);
			writeBankers.writeObject(bankerRegister);

			Collection<Account> listOfAccounts = accountRegister.values();

			for(Account account : listOfAccounts){

				for(int i = 0; i < account.getTransactions().size(); i++){

					if(journalRegister.contains(account.getTransactions().get(i))){

						continue;

					}
					else{

						journalRegister.add(account.getTransactions().get(i));

					}

				}

			}

			writeJournal.writeObject(journalRegister);

		}
		catch(IOException e){

			printer.println("IO Error: failed to save accounts/bankers/journals: " + e);
			return false;

		}

		return true;

	}

}
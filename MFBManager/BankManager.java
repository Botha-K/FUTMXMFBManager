public class BankManager extends Banker{

	public BankManager(){

	}

	public BankManager(String name, String staffId, String password){

		super(name, staffId, password);

	}

	public DeskOfficer createDeskOfficer(String name, String staffId, String password){

		DeskOfficer officer = new NewDeskOfficer(name, staffId, password);

		return officer;

	}

	private class NewDeskOfficer extends DeskOfficer {

		public NewDeskOfficer(String name, String staffId, String password){

			super(name, staffId, password);

		}

		@Override
		public void viewAccounts(){

			

		}

		@Override
		public void viewJournal(){

			

		}

	}

	public void viewOfficersList(){

		

	}

	@Override
	public void viewAccounts(){

		

	}

	@Override
	public void viewJournal(){

		

	}

	@Override
	public String toString(){

		return "Name: " + getName() + ", Staff ID: " + getStaffId();

	}
	
}
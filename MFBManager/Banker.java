import java.io.Serializable;
import java.util.Date;

public abstract class Banker implements Serializable{

	private String name;
	private String staffId;
	private String password;
	private Date dateCreated;
	private Date lastModified;
	protected static String userMachine;

	protected Banker(){

		this.name = "Unassigned";
		this.staffId = "Unassigned";
		this.password = "Unassigned";
		this.dateCreated = new Date();
		this.lastModified = new Date();

	}

	protected Banker(String name, String staffId, String password){

		this.name = name;
		this.staffId = staffId;
		this.password = password;
		this.dateCreated = new Date();
		this.lastModified = new Date();
		userMachine = System.getProperty("user.name");

	}

	protected String getName(){

		return this.name;

	}

	protected void setName(String name){

		this.name = name;

	}

	protected String getStaffId(){

		return this.staffId;

	}

	protected void setStaffId(String staffId){

		this.staffId = staffId;

	}

	protected void setPassword(String password){

		this.password = password;

	}

	protected String getPassword(){

		return this.password;

	}

	protected Date getDateCreated(){

		return this.dateCreated;

	}

	protected Date getLastModified(){

		return this.lastModified;

	}

	protected abstract void viewAccounts();

	protected abstract void viewJournal();

}
package sharedData;

public class User extends SocketMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7649437873694239705L;
	
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private String type;
	
	public User(boolean isQuerry, int ID, String password, String email, String firstname, String lastname, String type) 
	{
		super(isQuerry, ID);
		setMessageType();
		
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.type = type;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getType() {
		return type;
	}

	@Override
	public void setMessageType() 
	{
		messageType = userMessage;
	}
}
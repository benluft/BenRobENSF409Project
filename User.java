package sharedData;

/**
 * A user message that can be sent over a socket
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
public class User extends SocketMessage
{

	private static final long serialVersionUID = -7649437873694239705L;
	
	/**
	 * The users password
	 */
	private String password;
	/**
	 * The users email
	 */
	private String email;
	/**
	 * The users first name
	 */
	private String firstname;
	/**
	 * The users last name
	 */
	private String lastname;
	/**
	 * The type of the user(student or prof)
	 */
	private String type;
	
	/**
	 * Initializes all the data members
	 * 
	 * @param isQuerry
	 * @param ID
	 * @param password
	 * @param email
	 * @param firstname
	 * @param lastname
	 * @param type
	 */
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

	/* (non-Javadoc)
	 * @see sharedData.SocketMessage#setMessageType()
	 */
	@Override
	public void setMessageType() 
	{
		messageType = userMessage;
	}
}
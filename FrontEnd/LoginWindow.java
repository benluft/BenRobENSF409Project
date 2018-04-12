package frontEnd;

import java.util.Vector;

import javax.swing.JOptionPane;

import sharedData.User;

/**
 * Creates a JOptionPane that asks the user for their user ID and password.  If either
 * are incorrect, the loginwindow restarts, otherwise the GUI is opened
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class LoginWindow {
	/**
	 * Username that the user has attempted
	 */
	private int userName;
	/**
	 * Password that the user has attempted
	 */
	private String password;
	/**
	 * True if the login was done correctly
	 */
	private boolean loginCorrect;
	
	/**
	 * Attempts to log the user into the system
	 * 
	 * @param coms allows this system to communicate with the socket
	 */
	public LoginWindow(SocketCommunicator coms) {
		userName = 0;
		password = null;
		loginCorrect = false;
		Vector<User> user = null;
		
		while(!loginCorrect) 
		{
			coms.write(getUserInput());
			user = (Vector<User>)coms.read();
			
			if(user.get(0).getIsQuerry() == true)
			{
				loginCorrect = true;
				break;
			}
			
			JOptionPane.showMessageDialog(null, "Incorrect user ID or password");
			
		}
		runMainMenu(coms, user.get(0));
	}
	
	/**
	 * Gets the user input from the JOptionPane
	 * 
	 * @return the user that has been found, or null
	 */
	private User getUserInput() {
		
		try 
		{
			userName = Integer.parseInt(JOptionPane.showInputDialog("Enter Username: "));
			password = JOptionPane.showInputDialog("Enter Password: ");
			return new User(true, userName, password, null, null, null, null);
		}
		catch(NumberFormatException e)
		{
			return new User(true, 0, null,null,null,null,null);
		}
		
	}
//	private boolean testUserInput() {
//		
//		
//		if(userName.equals("Rob") && password.equals("bunny")) {
//			return true;
//		}
//		JOptionPane.showMessageDialog(null,
//			    "Please try entering your information again",
//			    "Wrong Username or Password",
//			    JOptionPane.ERROR_MESSAGE);	
//		return false;
//	}
	
	/**
	 * Starts the GUI
	 * 
	 * @param coms connection to the socket
	 * @param user the first user
	 */
	private void runMainMenu(SocketCommunicator coms, User user) {
		MainMenuView theView = new MainMenuView(user);
		//while(true) {}
		MainMenuController theController = new MainMenuController (theView, coms, user);
	}

}

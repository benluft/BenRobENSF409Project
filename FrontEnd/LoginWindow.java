package frontEnd;

import java.util.Vector;

import javax.swing.JOptionPane;

import sharedData.User;

public class LoginWindow {
	private int userName;
	private String password;
	private boolean loginCorrect;
	
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
	
	private void runMainMenu(SocketCommunicator coms, User user) {
		MainMenuView theView = new MainMenuView();
		//while(true) {}
		MainMenuController theController = new MainMenuController (theView, coms, user);
	}

}

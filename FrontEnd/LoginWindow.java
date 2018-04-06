import javax.swing.JOptionPane;

public class LoginWindow {
	private String userName;
	private String password;
	private boolean loginCorrect;

	
	public LoginWindow() {
		userName = password = null;
		loginCorrect = false;
		while(!loginCorrect) {
			loginCorrect = getUserInput();
		}
		runMainMenu();
	}
	
	private boolean getUserInput() {
		userName = JOptionPane.showInputDialog("Enter Username: ");
		password = JOptionPane.showInputDialog("Enter Password: ");
		return testUserInput();
		
	}
	private boolean testUserInput() {
		// communicate with socket
		if(userName.equals("Rob") && password.equals("bunny")) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
			    "Please try entering your information again",
			    "Wrong Username or Password",
			    JOptionPane.ERROR_MESSAGE);	
		return false;
	}
	private void runMainMenu() {
		MainMenuView theView = new MainMenuView();
		MainMenuController theController = new MainMenuController (theView );
	}

}

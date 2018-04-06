package frontEnd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import sharedData.Assignment;
import sharedData.Course;
import sharedData.Enrolment;
import sharedData.FileMessage;
import sharedData.User;

class Client
{
	/**
	 * Socket for this client
	 */
	private Socket socket;
	/**
	 * Used to read from the socket
	 */
	private ObjectInputStream readersocket;
	/**
	 * Used to write to the socket
	 */
	private ObjectOutputStream writersocket;
	
	/**
	 * Attempts to open a socket connection, along with all IO member objects.
	 * Also starts the frame
	 */
	public Client()
	{
		try 
		{
			socket = new Socket("localhost", 9091);
			writersocket = new ObjectOutputStream(socket.getOutputStream());
			readersocket = new ObjectInputStream(socket.getInputStream());
			
			//MainMenuController controller = new MainMenuController();
			
		} 
		catch (IOException e) {
			System.out.println("Could not create socket");
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the client
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Client client = new Client();
		
		SocketCommunicator communicator = new SocketCommunicator(client.readersocket, client.writersocket);
		
		LoginWindow login = new LoginWindow(communicator);
		
//		try 
//		{
//			client.writersocket.writeObject(new User(true, 1, "password1",null,null,null,null));
//			Vector<User> user = (Vector<User>)client.readersocket.readObject();
//		
//			if(user.get(0).getIsQuerry() == false)
//			{
//				System.out.println("User Exists");
//			}
//			else
//			{
//				System.out.println("User Does not exist");
//			}
//			
//			System.out.println("Profs Id is " +user.get(0).getID());
//			
//			client.writersocket.writeObject(new Course(false,0,user.get(0).getID(),"ENEL445", false));
//			
//			client.writersocket.writeObject(new Course(true,0,user.get(0).getID(),null,false));
//			Vector<Course> courses = (Vector<Course>)client.readersocket.readObject();
//			
//			for(int i = 0; i < courses.size(); i++)
//			{
//				System.out.println(courses.get(i).getName());
//			}
//			
//			client.writersocket.writeObject(new User(true,0,null,null,null,null,"S"));
//			Vector<User> users = (Vector<User>)client.readersocket.readObject();
//			
//			for(int j = 0; j < users.size(); j++)
//			{
//				System.out.println(users.get(j).getFirstname());
//			}
//			
//			client.writersocket.writeObject(new Enrolment(true, 0, 0, 3));
//			Vector<Enrolment> enrolled = (Vector<Enrolment>) client.readersocket.readObject();
//			
//			for(int k = 0; k < enrolled.size(); k++)
//			{
//				int studentID = enrolled.get(k).getStudentID();
//				
//				for(int r = 0; r < users.size(); r++)
//				{
//					if(users.get(r).getID() == studentID)
//					{
//						System.out.println(users.get(r).getEmail());
//						break;
//					}
//				}
//			}
//			
//			JFileChooser fileBrowser = new JFileChooser();
//			
//			File selectedFile = null;
//			
//			if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
//			{
//				selectedFile = fileBrowser.getSelectedFile();
//			}
//			
//			long length = selectedFile.length();
//			
//			byte[] content = new byte[(int) length]; // Converting Long to Int
//			try {
//				FileInputStream fis = new FileInputStream(selectedFile);
//				BufferedInputStream bos = new BufferedInputStream(fis);
//				bos.read(content, 0, (int)length);
//			} 
//			catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} 
//			catch(IOException e){
//				e.printStackTrace();
//			}
//			
//			FileMessage fileMessage = new FileMessage(false, 0, content);
//			
//			Assignment assign = new Assignment(false, 0, courses.get(0).getID(), selectedFile.getName(), 
//					false, "March 16");
//			
//			try{
//				client.writersocket.writeObject(assign);
//				client.writersocket.flush();
//				client.writersocket.writeObject(fileMessage);
//				} catch(IOException e){
//				e.printStackTrace();
//			}
//
//			
//			while(true) {}
//		} 
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
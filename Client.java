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
/**
 * Client class that interacts with the socket and GUI
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
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
		
	}
}
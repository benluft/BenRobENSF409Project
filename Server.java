package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server
{
	/**
	 * A server socket for the server
	 */
	private ServerSocket serverSocket;
	/**
	 * The socket for the client
	 */
	private Socket socket;
	/**
	 * The output stream for the server
	 */
	private ObjectOutputStream writer;
	/**
	 * The input stream for the server
	 */
	private ObjectInputStream reader;

	/**
	 * The thread pool to communicate with multiple clients at once
	 */
	private ExecutorService pool;

	/**
	 * Constructs the serversocket and threadpool
	 */
	public Server()
	{
		try {
			serverSocket = new ServerSocket(9091);
			System.out.println("Server is now running");
			pool = Executors.newCachedThreadPool();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void decodeMessage()
	{
		
	}
	
	/**
	 * Attempts to connect a client to the server, and give them 
	 * object to write to the server and read from it
	 */
	public void connectSocket()
	{
		try {
			socket = serverSocket.accept();
			
			System.out.println("Client is Connected");
			
			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Begins threads.  A new thread is created each time a new client connects to the
	 * server
	 * 
	 */
	public void runThreads()
	{
		while(true)
		{
			connectSocket();
			Runnable worker = new Worker();
			pool.execute(worker);
		}
	}
	
	/**
	 * Starts the server
	 * 
	 * @param args not used
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {	
		Server s = new Server();
		s.runThreads();

	}

}



import java.net.*;
import java.io.*;

public class ConnectionHandler implements Runnable{
	private DataOutputStream out;
	private DataInputStream in;
	private Socket connection;
	private Server server;

	public ConnectionHandler(Socket connection, Server server){
		this.connection = connection;
		this.server = server;
	}

	public void run(){
		try{
			out = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			in = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
			boolean isDone = false;
			
			while(!isDone){
				String input = in.readUTF();
				isDone = input.endsWith("/quit");
				if(isDone)
					server.receive(input.substring(0, input.indexOf(":")) + " has disconnected");
				else
					server.receive(input);
			}
		}catch(Exception e){}
	}
	
	public void sendMessage(String message){
		try{
			out.writeUTF(message);
			out.flush();
		}catch(Exception e){}
	}
}

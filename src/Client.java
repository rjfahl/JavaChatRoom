
import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;
public class Client {
	private String host_name, port, userID;
	private DataOutputStream out;
	private DataInputStream in;
	private GUI gui;
	private Socket connection;
	
	public static void main(String[] args) {
		new Client();
	}

	public Client(){
		getInitialInformationFromUser();
		createDefaultHostName();
		createDefaultPort();
		
		connection = null;
		connectToServer();
		gui = new GUI(this);
		
		createDataStreamConnection();
	}
	
	private void getInitialInformationFromUser(){
		host_name = JOptionPane.showInputDialog("Enter host_name:");
		port = JOptionPane.showInputDialog("Enter port:");
		userID = JOptionPane.showInputDialog("Enter user name:");
	}
	
	private void createDefaultHostName(){
		if(host_name.isEmpty())
			host_name = "localhost";
	}
	
	private void createDefaultPort(){
		if(port.isEmpty())
			port = "8888";
	}
	
	private void connectToServer(){
		try{
			connection = new Socket(host_name, Integer.parseInt(port));
		}catch(Exception e){
			System.err.println("Connection Failed");
			return;
		}
	}
	
	private void createDataStreamConnection(){
		try{
			out = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			in = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
			out.writeUTF(userID + " connected");
			out.flush();
			
			while(true){
				String message = in.readUTF();
				gui.newInput(message);
			}
		}catch(Exception e){}
	}
	
	public void sendMessage(String message){
		try{
			out.writeUTF(userID + ": " + message);
			out.flush();
			if(message.equals("/quit"))
				System.exit(0);
		}catch(Exception e){}
	}
}

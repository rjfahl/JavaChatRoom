
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Server {
	private String port = JOptionPane.showInputDialog("Enter Port Number");
	private ServerSocket server;
	private GUI gui;
	private ArrayList<ConnectionHandler> connections;
	private int numConnections = 0;
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server(){
		openServerPort();
		initializeServerVariables();
		startTheServer();
	}
	
	private void openServerPort(){
		try{
			if(port.isEmpty())
				port = "8888";
			server = new ServerSocket(Integer.parseInt(port));
		}catch(Exception e){
			System.err.println("Couldn't run server on port " + port);
		}
	}
	
	private void initializeServerVariables(){
		gui = new GUI(this);
		connections = new ArrayList<ConnectionHandler>();
	}
	
	private void startTheServer(){
		while(true){
			try{
				if(hasNotReachedMaximumConnections()){
					ConnectionHandler handler = new ConnectionHandler(server.accept(), this);
					addNewConnection(handler);
					new Thread(handler).start();
				}
			}catch(Exception e){}
		}
	}
	
	private boolean hasNotReachedMaximumConnections(){
		return numConnections <=10;
	}
	
	private void addNewConnection(ConnectionHandler handler){
		connections.add(handler);
		numConnections++;
	}
	
	public void receive(String message){
		gui.newInput(message);
		for (ConnectionHandler c : connections)
			if(c != null)
				c.sendMessage(message);
	}
	
	public void disconnect(ConnectionHandler handler){
		connections.remove(handler);
		numConnections--;
	}
}


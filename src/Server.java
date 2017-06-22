import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
class Server {
	String port = null;
	ServerSocket server;
	ServerWindow sw;
	Scanner s;
	List<ConnectionHandler> chList;
	int chNum;
	
    public static void main(String[] args) {    	
    	new Server();
    }
    
    public Server(){
    	s = new Scanner(System.in);
    	startTheServer();		
    	initializeServerProperties();
		watchForNewClientConnections();
    }
    
    public void receive(String s){
    	sw.addText(s);
    	for(ConnectionHandler i : chList)
    		if(i != null)
    			i.send(s);
    }
    
    public void disconnect(ConnectionHandler ch){
    	chList.remove(ch);
    	chNum--;
    }
    
    private void initializeServerProperties(){
		sw = new ServerWindow(this);
		chList = new ArrayList<ConnectionHandler>();
		chNum = 0;
    }
    
    private void startTheServer(){
    	try {
			getDesiredPort();
			setDefaultPortIfNeeded();
			server = new ServerSocket(Integer.parseInt(port));
        } catch (IOException ioe) {
            System.err.println("Couldn't run " + "server on port " + port);
        }
    }
    
    private void getDesiredPort(){
    	System.out.print("Enter port number:");
		port = s.nextLine();
    }
    
    private void setDefaultPortIfNeeded(){
		if(port.isEmpty())
			port = "8888";
    }
    
    private void watchForNewClientConnections(){
    	while(true) {
			try {
				if(chNum <= 10){
					Socket connection = server.accept();
					ConnectionHandler handler = new ConnectionHandler(connection, this);
					chList.add(handler);
					chNum++;
					new Thread(handler).start();
				}
			} catch (IOException ioe1) {}
		}
    }
}

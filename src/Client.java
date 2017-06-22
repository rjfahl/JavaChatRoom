import java.net.*;
import java.util.Scanner;
import java.io.*;
class Client {
	Scanner s;
	String hostname, port, username;
	DataOutputStream out;
	DataInputStream in;
	ChatWindow ch;
	Socket connection = null;
	
    public static void main(String[] args) {
    	new Client();
    }
    
    public Client(){
    	s = new Scanner(System.in);

        getHostName();
        getPort();
        getUsername();
        connectToServer();
        addTextToChatWindow();
    }
    
    public void send(String s){
    	try {
			out.writeUTF(username+": "+s);
			out.flush();
    		if(s.equals("/quit"))
    			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getUserName(){    	
    	return username;
    }
    
    private void getHostName(){
    	System.out.println("Enter hostname:");
        hostname = s.nextLine();
        if(hostname.isEmpty())
        	hostname = "localhost";
    }
    
    private void getPort(){
    	System.out.println("Enter port:");
        port = s.nextLine();
        if(port.isEmpty())
			port = "8888";
    }
    
    private void getUsername(){
        System.out.println("Enter username:");
        username = s.nextLine();
    }
    
    private void connectToServer(){
        try {
            connection = new Socket(hostname, Integer.parseInt(port));
            System.out.println("connected to "+hostname+" on port "+port+" as "+username);
        } catch (IOException ioe) {
            System.err.println("Connection failed");
        }
    }
    
    private void addTextToChatWindow(){
    	ch = new ChatWindow(this);
        try {
        	out = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
        	in = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
        	out.writeUTF("user "+username+" connected");
        	out.flush();
        	while(true){
        		String input = in.readUTF();
        		ch.addText(input);
        	}
        } catch (IOException ioe1) {}
    }
    
}

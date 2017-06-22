import java.net.*;
import java.io.*;
class ConnectionHandler implements Runnable {
	private DataOutputStream out;
	private DataInputStream in;
    private Socket connection;
    private Server server;
    
	public ConnectionHandler(Socket c, Server s) {
		server = s;
        connection = c;
    }   
	
	public void run() {
        try {
        	out = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
        	in = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
        	boolean done = false;
        	while(!done){
        		String input = in.readUTF();
        		done = input.endsWith("/quit");
        		if(done)
        			server.receive(input.substring(0, input.indexOf(":"))+" has logged out");
        		else
        			server.receive(input);
        	}
        	server.disconnect(this);
		} catch (IOException ioe) {}
    }
	
	public void send(String s){
		try {
			out.writeUTF(s);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

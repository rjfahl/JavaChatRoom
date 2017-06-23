
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel topPanel, bottomPanel;
	private JButton send;
	private JScrollPane scroll;
	private JTextField chatInput;
	private JTextArea chatRoom;
	private String chat;
	private Server server;
	private Client client;
	
	public GUI(Server server){
		this.server = server;
		client = null;
		setTitle("Server");
		generateFrameDetails();		
	}
	
	public GUI(Client client){
		this.client = client;
		server = null;
		setTitle("Client Chat");
		generateFrameDetails();
	}

	private void generateFrameDetails(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(500,400);
		
		generatePanels();
		addPanelsToFrame();
		
		bottomPanel.getRootPane().setDefaultButton(send);	
		setVisible(true);
	}
	
	private void generatePanels(){
		generateBottomPanel();
		generateTopPanel();
	}
	
	private void addPanelsToFrame(){
		add(bottomPanel, BorderLayout.SOUTH);
		add(topPanel, BorderLayout.CENTER);
	}
	
	private void generateBottomPanel(){
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		chatInput = new JTextField();
		send = new JButton("Send");
		
		createSendButtonActionListener();
		
		bottomPanel.add(chatInput, BorderLayout.CENTER);
		bottomPanel.add(send, BorderLayout.EAST);
	}
	
	private void createSendButtonActionListener(){
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == send){
					String text = chatInput.getText();
					if(!text.trim().isEmpty()){
						if(server != null)
							server.receive("server: " + text);
						else 
							client.sendMessage(text);
					}
					chatInput.setText("");
				}
			}
		});
	}
	
	private void generateTopPanel(){
		chat = "";
		topPanel = new JPanel(new BorderLayout());
		chatRoom = new JTextArea();
		scroll = new JScrollPane(chatRoom);
		chatRoom.setEditable(false);
		topPanel.add(scroll, BorderLayout.CENTER);
	}
	
	public void newInput(String input){
		chat += input + "\n";
		chatRoom.setText(chat);
		chatRoom.setCaretPosition(chatRoom.getDocument().getLength());
	}
}

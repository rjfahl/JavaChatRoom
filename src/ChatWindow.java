import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel chatPanel, bottomPanel;
	private JButton sendButton;
	private JScrollPane chatScroll;
	private JTextField chatBox;
	private JTextArea chatDisplay;
	private String chatText;
	private Client client;
	
	public ChatWindow(Client c) {
		client = c;
		setTitle("Chat Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(500, 400);
		buildBottomPanel();
		buildChatPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		add(chatPanel, BorderLayout.CENTER);
		bottomPanel.getRootPane().setDefaultButton(sendButton);
		setVisible(true);
	}
	
	public void addText(String input){
		chatText += input + "\n";
		chatDisplay.setText(chatText);
	    chatDisplay.setCaretPosition(chatDisplay.getDocument().getLength());
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == sendButton) {
				sendValidChatMessageToServer();
				clearChatBox();
			}
		}
		
		private void sendValidChatMessageToServer(){
			if(!chatBox.getText().trim().isEmpty())
				client.send(chatBox.getText());
		}
		
		private void clearChatBox(){
			chatBox.setText("");
		}
	}
	
	private void buildChatPanel() {
		chatText = "";
		chatPanel = new JPanel(new BorderLayout());
		chatDisplay = new JTextArea();
		chatScroll = new JScrollPane(chatDisplay);
		chatDisplay.setEditable(false);
		chatPanel.add(chatScroll, BorderLayout.CENTER);
	}
	
	private void buildBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		chatBox = new JTextField();
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ButtonListener());
		bottomPanel.add(chatBox, BorderLayout.CENTER);
		bottomPanel.add(sendButton, BorderLayout.EAST);
	}
}

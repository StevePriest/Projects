import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Simple client GUI
public class Client extends JFrame implements ActionListener {
	JTextArea result = new JTextArea(20,40);	
	JTextField userInput = new JTextField(30);
	JTextField serverInput = new JTextField("localhost", 10);
	JTextField portInput = new JTextField("5555", 5);
	JButton connectButton = new JButton("Connect");
	JButton sendButton = new JButton("Send");
	JLabel errors = new JLabel();
	JLabel server = new JLabel("Server:");
	JLabel port = new JLabel("Port:");
	JScrollPane scroller = new JScrollPane();
	Socket socket;
	BufferedReader in;
	PrintStream out;
	Thread thread;
	ImageIcon icon1 = new ImageIcon("Smiley2");
	JLabel iconLabel = new JLabel(icon1);
	
	public Client() {
		setLayout(new java.awt.FlowLayout());
		setSize(500,430);
		setTitle("Lab 5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		result.setEditable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		scroller.getViewport().add(result);
		add(scroller);
		add(userInput); userInput.addActionListener(this);
		add(sendButton); sendButton.addActionListener(this); sendButton.setEnabled(false);		
		add(server);
		add(serverInput);
		add(port);
		add(portInput);
		add(connectButton); connectButton.addActionListener(this);
		add(errors);
	}
	
	public void actionPerformed(ActionEvent evt) {
		try {			
			if (evt.getActionCommand().equals("Connect") || 
					connectButton.getText().equals("Connect") && evt.getSource() == serverInput) {				
				result.setText("");
				Scanner scanner = new Scanner(serverInput.getText());
				Scanner scannerTwo = new Scanner(portInput.getText());
				if (!scanner.hasNext()) return;
				String host = scanner.next();
				if (!scannerTwo.hasNextInt()) return;
				int port = scannerTwo.nextInt();
				socket = new Socket(host, port);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintStream(socket.getOutputStream());
				thread = new ReadThread(in, result, connectButton);
				thread.start();
				sendButton.setEnabled(true);
				connectButton.setText("Disconnect");
				userInput.setText("");
			}			
			else if (evt.getActionCommand().equals("Disconnect")) {				
				out.println("/quit");
				thread.interrupt();							
				socket.close();
				in.close();
				out.close();
				sendButton.setEnabled(false);
				connectButton.setText("Connect");
			}
			else if (evt.getActionCommand().equals("Send") || 
						sendButton.isEnabled() && evt.getSource() == userInput) {
				out.print(userInput.getText() + "\r\n");
				out.flush();				
				userInput.setText("");
			}
		} catch(UnknownHostException uhe) {
			errors.setText(uhe.getMessage() + "- invalid server, please enter valid server ID");
		} catch(IOException ioe) {
			errors.setText(ioe.getMessage() + " Please enter valid port no.");
		}
		
		String buttonText = connectButton.getText();
		if (buttonText.equals("Disconnect")) {
			errors.setText("");
		}
	}
	
	public static void main(String[] args) {		
		Client display = new Client();
		display.setVisible(true);
	}
}

class ReadThread extends Thread {
	BufferedReader in;
	JTextArea display;	
	JButton button;	
	
	public ReadThread(BufferedReader br, JTextArea jta, JButton jb) {
		in = br;
		display = jta;
		button = jb;
		
		
	}
	public void run() {
		String s;
		try {
			while ((s = in.readLine()) != null) {
				display.append(s + '\n');				
				
				if (s.contains("*** Bye ")) {
					button.setText("Connect");
				}
			}
		} catch (IOException ioe) {
			System.out.println("Error reading from socket");
		}
	}
}
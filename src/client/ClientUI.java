package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;

/**
 * @author Yunfei Peng 987588
 *
 */

public class ClientUI {

	private JFrame frame;
	private JTextField textField;
	private static int port;
	private static String ip;
	private static String word;
	private static String meaning;
	private JButton btnDelete;
	private JButton btnAdd;
	private static JTextArea textArea;
	private Socket socket;
	private static DataOutputStream os;
	private static DataInputStream is;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Client needs 2 arguments, port and server ip address.");
			System.exit(0);
		}else {
			 try{
				 port = Integer.parseInt(args[1]);
				 System.out.println("port is "+ port);
			 }catch(NumberFormatException e) {
				 System.out.println("Port must be a number.");
				 System.exit(0);
			 }
		}
		
		ip = args[0];
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnFind = new JButton("find");
		
		
		JButton btnAdd = new JButton("add");
		
		JButton btnDelete = new JButton("delete");
		
		textArea = new JTextArea();
		
		
		if(!connectServer(socket)) {
			textArea.setText("");
			textArea.setText("Server is not online, you cannot get any result from any action.");
		}
		

		btnFind.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Client find");
				word = textField.getText();
				if(isEmpty(word)) {
					textArea.setText("");
					textArea.setText("Please enter a word.");
				}
				else if(word.split(" ").length != 1) {
					textArea.setText("Please enter single word.");
				}
				else {
					System.out.println("find: " + word);
					ClientProcess.findWord(ip, port, word, socket, os, is);
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Client add");
				word = textField.getText();
				meaning = textArea.getText();
				if(isEmpty(word)) {
					textArea.setText("");
					textArea.setText("Please enter a word.\n");
				}
				if(isEmpty(meaning)) {
					textArea.setText("Please enter meaning for word");
					//System.out.println("Please enter meaning for word");
				}
				if(!isEmpty(meaning) && !isEmpty(word)) {
					System.out.println("add: " + word + ", " + meaning);
					ClientProcess.addWord(ip, port, word, meaning, socket, os, is);
				}
				
			}
		});
		
		btnDelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Client delete");
				word = textField.getText();
				if(isEmpty(word)) {
					textArea.setText("");
					textArea.setText("Please enter a word.");
				}
				else {
					System.out.println("delete: " + word);
					ClientProcess.deleteWord(ip, port, word, socket, os, is);
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnFind)
							.addGap(102)
							.addComponent(btnAdd)
							.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
							.addComponent(btnDelete))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFind)
						.addComponent(btnDelete)
						.addComponent(btnAdd))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(9, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private boolean isEmpty(String str) {
		if(str.length()==0) return true;
		return false;
	}
	
	public static void setTextArea(String str) {
		textArea.setText(str);
	}
	
	public static Boolean connectServer(Socket socket) {
		try {
			socket = new Socket(ip,port);
			os = new DataOutputStream(socket.getOutputStream());
			is = new DataInputStream(socket.getInputStream());
			textArea.setText("");
			textArea.setText("Client now is connected to server.");
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			textArea.setText("IP address is incorrect.");
			//e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			textArea.setText("Server is not online.");
			//e.printStackTrace();
			return false;
		}
	}
	
}

package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientProcess {

	
	public static String findWord(String ip, int port, String word) {
		String result = "";
		try {
			Socket socket = new Socket(ip,port);
			String sendData = "find " + word;
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			os.writeUTF(sendData);
			DataInputStream is = new DataInputStream(socket.getInputStream());
			result = is.readUTF().toString();
			System.out.println("find result: " + result.length());
			if(result.length()==0) {
				ClientUI.setTextArea("Find fail, the word is not in the dictionary.");
			}
			else {
				ClientUI.setTextArea(result);
			}
			socket.close();
			os.close();
			is.close();
		} catch (UnknownHostException e) {
			ClientUI.setTextArea("IP address is incorrect.");
			e.printStackTrace();
		} catch (IOException e) {
			ClientUI.setTextArea("Cannot connect to server.");
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static Boolean addWord(String ip, int port, String word, String meaning) {
		Boolean result = false;
		try {
			Socket socket = new Socket(ip,port);
			String sendData = "add " + word + " " + meaning;
			System.out.println("client process add: " + word + ", " + meaning);
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			os.writeUTF(sendData);
			DataInputStream is = new DataInputStream(socket.getInputStream());
			result = is.readBoolean();
			if(result) {
				ClientUI.setTextArea("Successfully add word.");
			}
			else {
				ClientUI.setTextArea("You cannot add the word, because it is already in the dictionary.");
			}
			socket.close();
			os.close();
			is.close();
		} catch (UnknownHostException e) {
			ClientUI.setTextArea("IP address is incorrect.");
			e.printStackTrace();
		} catch (IOException e) {
			ClientUI.setTextArea("Cannot connect to server.");
			e.printStackTrace();
		}
		
		return false;
	}
	
public static Boolean deleteWord(String ip, int port, String word) {
	Boolean result = false;
	try {
		Socket socket = new Socket(ip,port);
		String sendData = "delete " + word;
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		os.writeUTF(sendData);
		DataInputStream is = new DataInputStream(socket.getInputStream());
		result = is.readBoolean();
		if(result) {
			ClientUI.setTextArea("Successfully delete word.");
		}
		else {
			ClientUI.setTextArea("You cannot delete the word, because it is not in the dictionary.");
		}
		socket.close();
		os.close();
		is.close();
	} catch (UnknownHostException e) {
		ClientUI.setTextArea("IP address is incorrect.");
		e.printStackTrace();
	} catch (IOException e) {
		ClientUI.setTextArea("Cannot connect to server.");
		e.printStackTrace();
	}
		return false;
	}
}

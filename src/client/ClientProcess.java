package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientProcess {

	
	public static String findWord(String ip, int port, String word, Socket socket, DataOutputStream os, DataInputStream is) {
		String result = "";
		try {
			String sendData = "find " + word;
			os.writeUTF(sendData);
			result = is.readUTF().toString();
			System.out.println("find result: " + result);
			if(result.length()==0) {
				ClientUI.setTextArea("Find fail, the word is not in the dictionary.");
			}
			else {
				ClientUI.setTextArea(result);
			}
		} catch (UnknownHostException e) {
			ClientUI.setTextArea("IP address is incorrect.");
			//e.printStackTrace();
		} catch (IOException e) {
			if(!ClientUI.connectServer(socket)) ClientUI.setTextArea("Cannot connect to server. Try to connect in next request.");
			//e.printStackTrace();
		} catch(java. lang. NullPointerException e) {
			if(!ClientUI.connectServer(socket)) ClientUI.setTextArea("Cannot connect to server. Try to connect in next request.");
		}
		
		return "";
	}
	
	public static Boolean addWord(String ip, int port, String word, String meaning, Socket socket, DataOutputStream os, DataInputStream is) {
		Boolean result = false;
		try {
			String sendData = "add " + word + " " + meaning;
			System.out.println("client process add: " + word + ", " + meaning);
			os.writeUTF(sendData);
			result = is.readBoolean();
			if(result) {
				ClientUI.setTextArea("Successfully add word.");
			}
			else {
				ClientUI.setTextArea("You cannot add the word, because it is already in the dictionary.");
			}
		} catch (UnknownHostException e) {
			ClientUI.setTextArea("IP address is incorrect.");
			//e.printStackTrace();
		} catch (IOException e) {
			if(!ClientUI.connectServer(socket)) ClientUI.setTextArea("Cannot connect to server. Try to connect in next request.");
			//e.printStackTrace();
		} catch(java. lang. NullPointerException e) {
			if(!ClientUI.connectServer(socket)) ClientUI.setTextArea("Cannot connect to server. Try to connect in next request.");
		}
		
		return false;
	}
	
public static Boolean deleteWord(String ip, int port, String word, Socket socket, DataOutputStream os, DataInputStream is) {
	Boolean result = false;
	try {
		String sendData = "delete " + word;
		os.writeUTF(sendData);
		result = is.readBoolean();
		if(result) {
			ClientUI.setTextArea("Successfully delete word.");
		}
		else {
			ClientUI.setTextArea("You cannot delete the word, because it is not in the dictionary.");
		}
	} catch (UnknownHostException e) {
		ClientUI.setTextArea("IP address is incorrect.");
		//e.printStackTrace();
	} catch (IOException e) {
		if(!ClientUI.connectServer(socket)) ClientUI.setTextArea("Cannot connect to server. Try to connect in next request.");
		//e.printStackTrace();
	} catch(java. lang. NullPointerException e) {
		if(!ClientUI.connectServer(socket)) ClientUI.setTextArea("Cannot connect to server. Try to connect in next request.");
	}
		return false;
	}
}

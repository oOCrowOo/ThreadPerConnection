package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientConnection extends Thread{
	
	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private DataInputStream is;
	private DataOutputStream out;
	private String path;
	
	public ClientConnection(Socket clientSocket, String path) {
		this.clientSocket = clientSocket;
		this.path = path;
		try{
			 is = new DataInputStream(clientSocket.getInputStream());
			 out = new DataOutputStream(clientSocket.getOutputStream());
		}catch(Exception e){
			System.out.println("Data transfer error");
		}
	}


	@Override
	public void run() {
		System.out.println("One client is connected to server.");
		try {
			while(true) {
				String request = new String(is.readUTF());
				System.out.println("Get request from client:" + request);
				String[] requestAry = request.split(" ");
				if(requestAry[0].equals("find")) {
					String result = ServerProcess.findProcess(requestAry[1],path);
					out.writeUTF(result);
					out.flush();
				}
				else if(requestAry[0].equals("add")) {
					String meaning = "";
					for(int i = 2; i < requestAry.length; i++) {
						meaning += requestAry[i] + " ";
					}
					meaning = meaning.substring(0, meaning.length()-1);
					Boolean result = ServerProcess.addPorcess(requestAry[1], meaning, path);
					out.writeBoolean(result);
					out.flush();
				}
				else if(requestAry[0].equals("delete")) {
					Boolean result = ServerProcess.deletePorcess(requestAry[1], path);
					out.writeBoolean(result);
					out.flush();
				}
			}
			
		} catch (IOException e) {
			System.out.println("Cannot read request from client.");
			//e.printStackTrace();
		}
		
		
	}

}

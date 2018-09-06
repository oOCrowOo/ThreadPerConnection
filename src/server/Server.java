package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Server {
	
	private static int port;
	private static String path;
	
	
	public static void main(String[] args) {
		
		ServerSocket listeningSocket = null;
		
		if(args.length != 2){
			System.out.println("Server needs 2 arguments, port and dict address.");
			System.exit(0);
		}
		else {
			 try{
				 port = Integer.parseInt(args[0]);
				 System.out.println("port is "+ port);
			 }catch(NumberFormatException e) {
				 System.out.println("Port must be a number.");
				 System.exit(0);
			 }
		}
		path = args[1];
		
		File file = new File(path);
		if (file.isDirectory()) {
			 path = path + "/dict.txt";
			 System.out.println("The path is not a file.");
			 if(file.exists()) {
					System.out.println("dict.txt exits in the path.");
			 }
			 else {
				 System.out.println("dict.txt not exits in the path, server will create one.");
				 System.out.println("File not exits, create one now.");
					Map<String, String> map = new HashMap<String, String>();
					JSONObject jb = new JSONObject(map);
					BufferedWriter out;
					try {
						out = new BufferedWriter(new FileWriter(path));
						out.write(jb.toString());
						out.close();
					} catch (IOException e) {
						System.out.println("Cannot open the file in the path: " + path);
						//e.printStackTrace();
						System.exit(0);
					}
			 }	
		}
		else {
			if(file.exists()) {
				System.out.println("File exits");
			}
			else {
				System.out.println("File not exits, create one now.");
				Map<String, String> map = new HashMap<String, String>();
				JSONObject jb = new JSONObject(map);
				BufferedWriter out;
				try {
					out = new BufferedWriter(new FileWriter(path));
					out.write(jb.toString());
					out.close();
				} catch (IOException e) {
					System.out.println("Cannot open the file in the path: " + path);
					//e.printStackTrace();
				}
			}
		}
		
		
		try {
			listeningSocket = new ServerSocket(port);
			System.out.println("Server listening on port " + port + " for a connection");
			
			while(true) {
				Socket clientSocket = listeningSocket.accept();
				ClientConnection clientConnection = new ClientConnection(clientSocket, path);
				clientConnection.start();
			}
		} catch (IOException e) {
			System.out.println("Cannot create server socket.");
			//e.printStackTrace();
		} finally {
			if(listeningSocket != null) {
				try {
					System.out.println("server close");
					listeningSocket.close();
				}catch (IOException e) {
					System.out.println("listeningSocket close fail.");
					//e.printStackTrace();
				}
			}
		}
		
	}

}

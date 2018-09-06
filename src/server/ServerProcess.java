package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Yunfei Peng 987588
 *
 */

public class ServerProcess {
	
	
	public synchronized static String findProcess(String word, String path) {
		
		String line = readFile(path);
		JSONObject jb = new JSONObject(line);
		if(jb.isNull(word)) {
			return "";
		}
		else {
			System.out.println("find:" + word);
			return jb.getString(word).toString();
		}
	}
	
	public synchronized static Boolean addPorcess(String word, String meaning, String path) throws IOException {
		
		String line = readFile(path);
		JSONObject jb = new JSONObject(line);
		if(!jb.isNull(word)) {
			return false;
		}
		else {
			System.out.println("add:" + word);
			jb.put(word, meaning);
			updateFile(jb,path);
			return true;
		}
	}
	
	public synchronized static Boolean deletePorcess(String word, String path) throws IOException {
		
		String line = readFile(path);
		JSONObject jb = new JSONObject(line);
		if(jb.isNull(word)) {
			return false;
		}
		else {
			System.out.println("delete:" + word);
			jb.remove(word);
			updateFile(jb,path);
			return true;
		}
	}
	
	private static void updateFile(JSONObject jb, String path) throws IOException {
		System.out.println("Word process update: " + jb.toString());
		File file = new File(path);
		BufferedWriter out = new BufferedWriter(new FileWriter(path));
		out.write(jb.toString());
		out.close();
	}
	
	private static String readFile(String path) {
		String result = "";
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(path));
			//System.out.println("3: " + br.readLine()); {}
			while((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot read the file from " + path);
			//e.printStackTrace();
		}catch(IOException e) {
			System.out.println("File reading error.");
			//e.printStackTrace();
		}
		return result;
	}
	
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author victor
 */

public class MySocket {

	private Socket socket;
	private BufferedReader bufferedReader; 
	private PrintWriter printWriter;
	//private String nick; 

	public MySocket(String hostAddress, int hostPort){
		try{ 
			//this.nick=nick;
			this.socket = new Socket(hostAddress, hostPort);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true); //Activem l'autoflush
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public MySocket(Socket s){
		try{
			this.socket = s;
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void write(String text){
		printWriter.println(text);
	}


	public String readLine(){
	
		String text = null;
		try{
			text = bufferedReader.readLine();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return text;
	}

	public void close(){
	/*
	Hem de tancar la connexió, pel que també tanquem el BufferedReader i PrintWriter corresponents
	*/
		try{
			bufferedReader.close();
			printWriter.close();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}


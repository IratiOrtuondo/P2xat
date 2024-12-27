/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

/**
 *
 * @author victor
 */

import java.io.*;
import java.net.*;



public class MyServerSocket{

	private ServerSocket serverSocket;

	public MyServerSocket(int port){
		try{
			this.serverSocket = new ServerSocket(port);
			System.out.println("Server is listening on port <" + port + ">");
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public MySocket accept(){
		try{
			return new MySocket(serverSocket.accept());
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}


	public void close(){
		try{
			this.serverSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}


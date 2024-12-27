/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

/**
 *
 * @author victor
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;




public class Server{
	private static ConcurrentHashMap<String, MySocket> users = new ConcurrentHashMap<>();
	
	public static void main(String[] args){

		if (args.length != 1) {
			System.out.println("Usage: java Server <port>");
			return;
			
		}

		int port = Integer.parseInt(args[0]);
		MyServerSocket serverSocket = new MyServerSocket(port);
		System.out.println("Server started.");

		while(true){
			MySocket client = serverSocket.accept();

			new Thread(){
				public void run(){
                                  
				
					String nick = client.readLine();
					client.write("Succesfully connected. Type message to send:");
					addUser(nick, client);
					String text;
					while(!"null".equals(text = client.readLine())){
                                            if(text==null)  {
                                                break;
                                                          } 
						broadcast(text, nick);
						System.out.println(nick + " says: "+ text);
                                    
					
                                       
				}
                                         client.write(null);
					removeUser(nick);
					client.close();
                                }
			}.start();
		}
	}


	public static void addUser(String user, MySocket s){
		System.out.println(user+" is on the chat");
		users.put(user, s);
	}


	public static void removeUser(String user){
		System.out.println(user+" left the chat");
		users.remove(user);
	}


	public static void broadcast(String message, String nick){
		MySocket client= users.get(nick);
		
		for (Map.Entry<String, MySocket> entry : users.entrySet()) {
            String actualUser = entry.getKey();
            MySocket actualSocket = entry.getValue();
			if(actualUser!=nick){
				actualSocket.write(nick+"> "+message);
			}
		}
	}
}

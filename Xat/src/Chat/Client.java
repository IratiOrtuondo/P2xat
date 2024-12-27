/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

/**
 *
 * @author victor
 */
import java.net.UnknownHostException;
import java.io.*;




public class Client {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Client <host> <port> <name>");
			return;

		}
                
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
                String name = args[2];
		MySocket s = new MySocket(hostname,port); // host, port
                s.write(name);

		// Thread d'entrada
		new Thread() {
                        @Override
			public void run() {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String message;
				try {
					while ((message = in.readLine()) != null) {
                                            
						s.write(message);
					}
                                
                                        s.write(message);
                                        
					s.close();
                                        
				} catch (UnknownHostException ex){
					System.out.println("Server not found: " + ex.getMessage());
				
				} catch (IOException ex) {
				}
			}
		}.start();

		// Thread de sortida
		new Thread() {
                        @Override
			public void run() {
				String message;
				while(!"null".equals(message =s.readLine())) {
					System.out.println(message);
				}
                                 
			}
                      
		}.start();
	}

}

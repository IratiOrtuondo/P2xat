/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

/**
 *
 * @author victor
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
    private static final Map<String, MySocket> users = new HashMap<>(); // Mapa básico
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // Bloqueo de lectura/escritura

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        MyServerSocket serverSocket = new MyServerSocket(port);
        System.out.println("Server started.");

        while (true) {
            MySocket client = serverSocket.accept();

            new Thread() {
                public void run() {

                    String nick = client.readLine();
                    client.write("Successfully connected. Type message to send:");
                    addUser(nick, client);
                    String text;
                    while (!"null".equals(text = client.readLine())) {
                        if (text == null) { // Desconexión abrupta con control c
                            break;
                        }
                        broadcast(text, nick);
                        System.out.println(nick + " says: " + text);
                    }
                    client.write(null);
                    removeUser(nick);
                    client.close();
                }
            }.start();
        }
    }

    public static void addUser(String user, MySocket s) {
        lock.writeLock().lock(); // Adquirir el bloqueo de escritura
        try {
            System.out.println(user + " is on the chat");
            users.put(user, s);
        } finally {
            lock.writeLock().unlock(); // Liberar el bloqueo de escritura
        }
    }

    public static void removeUser(String user) {
        lock.writeLock().lock(); // Adquirir el bloqueo de escritura
        try {
            System.out.println(user + " left the chat");
            users.remove(user);
        } finally {
            lock.writeLock().unlock(); // Liberar el bloqueo de escritura
        }
    }

    public static void broadcast(String message, String nick) {
        lock.readLock().lock(); // Adquirir el bloqueo de lectura
        try {
            for (Map.Entry<String, MySocket> entry : users.entrySet()) {
                String actualUser = entry.getKey();
                MySocket actualSocket = entry.getValue();
                if (!actualUser.equals(nick)) {
                    actualSocket.write(nick + "> " + message);
                }
            }
        } finally {
            lock.readLock().unlock(); // Liberar el bloqueo de lectura
        }
    }
}
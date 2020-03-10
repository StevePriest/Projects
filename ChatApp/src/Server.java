/*
	Program Overview:
		This program creates a chat server in which clients can send
		public and private messages. A new client is prompted for his or her
		name, assigned that as the user-name and provided with a short welcome
		message. All connected clients are notified	when a new client joins or 
		departs the chat server. Clients may terminate a connection by entering
		"/quit" or clicking the disconnect button from the client chat application.
	
	Input:
		Client enters text to be broadcast to all connected clients, or "@user-name"
		to send a private message routed to the intended client only.

	Output:
		Client messages and server initiated notifications*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// Chat server in which clients can send public and private messages
public class Server {
	
  // Create server socket and client socket
  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;
  
  // Create ArrayList used to store client threads
  private static final ArrayList<clientThread> threads = new ArrayList<clientThread>();
  
  public static void main(String args[]) {
	  
    // Set port number
    int portNumber = 5555;
    // Open server socket on portNumber
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }
    // Open a client socket for each connection and pass it to a new client thread
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        threads.add(null);
        int i = 0;     
        for (i = 0; i < threads.size(); i++) {        	       	
            if (threads.get(i) == null) {            
            clientThread temp = new clientThread(clientSocket, threads);              
            temp.start();                        
            threads.add(i, temp);            
            break;
            }
          }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}
// Class clientThread creates input/output streams for each client, prompts new
// clients for name and assigns user-name, broadcasts public messages, routes 
// private messages to the intended client, notifies all connected users when a
// new client joins or a client departs chat server.
class clientThread extends Thread {

  private String clientName = null;
  private PrintStream os = null;
  private Socket clientSocket = null; 
  private BufferedReader is;
  private final ArrayList<clientThread> threads;
  
  public clientThread(Socket clientSocket, ArrayList<clientThread> threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;    
  }
  
  public void run() {
	// ArrayList containing client threads
    ArrayList<clientThread> threads = this.threads;
    
    try {
      
      // Create client input/output streams      
      os = new PrintStream(clientSocket.getOutputStream());
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));      
      // Prompts new client for name. Name may not contain '@' character, as this
      // is a special character used for sending private messages
      String name;									  
      while (true) {
        os.println("Name?");
        name = is.readLine().trim();
        if (name.startsWith("/quit")) {
        	os.println("*** Bye ");
        	int index = threads.indexOf(clientThread.currentThread());
        	threads.set(index, null);
        	is.close();
            os.close();
            clientSocket.close();       	        	
        }
        if (name.indexOf('@') == -1) {
          break;
        } else {
          os.println("Name may not contain '@' character.");
        }
      } 
      // Display welcome message to new client only
      os.println("Welcome to our chat room, " + name + "!!!" + 
      "\nTo leave enter \"/quit\" in a new line.\nEnter \"@name message\" to send PM\nHave fun!!!");
      // Synchronize thread and add '@' character to name
      synchronized (this) {
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) != null && threads.get(i) == this) {
            clientName = "@" + name;            
            break;
          }
        }
        // Notify all connected clients that a new client has joined
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) != null && threads.get(i) != this && !name.startsWith("/quit")) {            
            threads.get(i).os.println("*** New user " + name
                    + " has entered the chat room !!! ***");
          }
        }
      }
      // Begin chat. Entering "/quit" will terminate that client's thread
      // and connections
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {        	
          break;
        }
        // When a private message is sent 
        if (line.startsWith("@")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < threads.size(); i++) {
                  if (threads.get(i) != null && threads.get(i) != this
                      && threads.get(i).clientName != null
                      && threads.get(i).clientName.equals(words[0])) {                    
                    threads.get(i).os.println("<" + name + "> private message: " + words[1]);
                    
                    // Echo private message to sender only
                    this.os.println(">" + name + "> PM to " + words[0] + ": " + words[1]);
                    break;
                  }
                }
              }
            }
          }
        } else {
          // Public message broadcast to all connected clients with user-names 
          synchronized (this) {
            for (int i = 0; i < threads.size(); i++) {
              if (threads.get(i) != null && threads.get(i).clientName != null) {                            	
                threads.get(i).os.println("<" + name + "> " + line);                
              }
            }
          }
        }
      }
      // Notify all connected clients with user-names when another client leaves
      synchronized (this) {
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) != null && threads.get(i) != this
              && threads.get(i).clientName != null) {                    	
            threads.get(i).os.println("*** " + name
                    + " has left the chat room !!! ***");
          }
        }
      }
      // Display to departing client only
      os.println("*** Bye " + name + " ***");
      
      // Set departing client's thread in ArrayList to null
      synchronized (this) {
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) == this) {            
            threads.set(i, null);       	
          }
        }
      }
      // Close input/output streams and client socket
      is.close();
      os.close();
      clientSocket.close();     
    } catch (IOException e) {
    }
  }
}
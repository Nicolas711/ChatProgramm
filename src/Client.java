import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client{
    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;
 
    public void startConnection(String ip, int port) {
    	try {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	}catch(Exception e) {
    		System.out.println("Server nicht erreichbar!");
    	}
    }
 
   public void sendMessage(String name) {
    	String eingabe;
    	Scanner scanner = new Scanner(System.in);    	
    	
		try {
			while(true) {     
				eingabe = scanner.nextLine();
			
		        out.println(name + ": " + eingabe);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
	        		
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
    
    public void receiveMessage() {
    	String resp = null;
    	try {
    		while(true) {
    			resp = in.readLine();
    			if(!resp.equals("")) {
    				System.out.println(resp);
    			}    			
    			try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    			
		} catch (Exception e) {
			System.out.println("Verbindung unterbrochen!");
		}
    }
 
    public void stopConnection() {
        try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }   
}

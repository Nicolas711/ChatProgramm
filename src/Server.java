import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Server{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private PrintWriter out;
    private static BufferedReader in;
    
    public void start(int port) {
    	try {
    		
            serverSocket = new ServerSocket(6666);
            clientSocket = serverSocket.accept();
            
            System.out.println("Connection startet");
            
			
			    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
 
    public void read()
    {
    	
    		String greeting;
    		  
			try {
				
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				while(true)
				{
					
					greeting = in.readLine();
					if(greeting != null && !greeting.equals("")) {
						System.out.println(greeting);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		

    }
    public void write(String name)
    {
    	
    	Scanner scanner = new Scanner(System.in);
    	 try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			while(true)
			{
				String answer = scanner.nextLine();
				
				out.println(name + ": " + answer);
				
				
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				 
			}
				
			
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void stop() {
    	try {
    		in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        
    }
    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("Server oder Client?");
    	String soc = scanner.next();
    	
    	soc = soc.toLowerCase();
    	
    	
    	if(soc.equals("server"))
    	{
    	    Server server=new Server();
            
    	    System.out.print("Dein Name: ");
 		   	String name = scanner.next();
            
            server.start(6666);
           
            new Thread() {
            	public void run()
            	{
            		server.read();
            	}
            }.start();
            new Thread() {
            	public void run()
            	{
            		server.write(name);
            	}
            }.start(); 
    	}
    	else if(soc.equals("client"))
    	{
    		   Client client = new Client();
    		   
    		   
    		   System.out.print("Dein Name: ");
    		   String name = scanner.next();
    		   
    		   System.out.print("Ziel IP: ");
    		   String zielIP = scanner.next();
    		   
    		   
    	        
    	       client.startConnection(zielIP, 6666);

    	       
    	        new Thread() {
    	        	public void run() {
    	        		client.receiveMessage();
    	        	}
    	        }.start();
    	        
    	        new Thread() {
    	        	public void run() {
    	        		client.sendMessage(name);
    	        	}
    	        }.start();
    	}
    	
    	
    
        

    }
}

import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {

	public static void main(String[] args) throws IOException {

		String mcastAddr = args[0];
		int mcastPort = Integer.parseInt(args[1]);
		String oper = args[2];
		InetAddress ip = InetAddress.getByName(mcastAddr);

		String receivedMessage = getMessage(ip, mcastPort);
		
		String splited[] = receivedMessage.trim().split(" ");
		String receivedAddress = splited[0];		
		String receivedPort = splited[1];	
		
		int port = Integer.parseInt(receivedPort);
		
		InetAddress ipAddress = InetAddress.getByName(receivedAddress);
			

		System.out.println("Received port: " + port);		
				
		String message = oper + " ";

		System.out.println(oper);

		if (oper.equals("register")) {
			message += args[3] + " ";
			message += args[4];
			/*else
				System.out.println("Error on register");*/
		}
		else if (oper.equals("lookup")){
			message += args[3];
			/*	else
				System.out.println("Error on Lookup");*/
		}


		byte[] buf = new byte[256];
		buf = message.getBytes();
		DatagramSocket s = new DatagramSocket();
		DatagramPacket p = new DatagramPacket(buf, buf.length, ipAddress, port);

		s.send(p);

		s.close();
		
		s = new DatagramSocket();

		//waits for answer
		byte[] bufr = new byte[256]; 
		
		p = new DatagramPacket(bufr, bufr.length);		

		s.receive(p);

		String response = new String(p.getData());

		System.out.println(response);	
		
		s.close();

	}
	
	/**
	 * Joins the multicastSocket group and gets its message
	 * @param ip IP Address to join group
	 * @param mcastPort MulticastPort to be used in the MulticastSocket
	 * @return Returns the server port from the message
	 * @throws IOException
	 */
	public static String getMessage(InetAddress ip, int mcastPort) throws IOException {
		
		MulticastSocket mcastSocket = new MulticastSocket(mcastPort);

		System.out.println("Joining multicast group");

		byte[] buf = new byte[256];
		
		DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
		
		mcastSocket.joinGroup(ip);		
		mcastSocket.receive(msgPacket);		
		mcastSocket.leaveGroup(ip);		
		mcastSocket.close();
		
		byte[] data = msgPacket.getData();

		String receivedPort = new String(data);			
		 
		return receivedPort;
		
	}
}



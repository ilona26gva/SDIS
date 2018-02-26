
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Vector;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws IOException {

		String mcastAddr = args[0];
		int mcastPort = Integer.parseInt(args[1]);
		String oper = args[2];

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

		byte[] buf = message.getBytes();
		DatagramSocket s = new DatagramSocket();
		DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getByName(host), port);

		s.send(p);

		s.close();
		
		s = new DatagramSocket(port);

		//waits for answer
		byte[] bufr = new byte[256]; 
		
		p = new DatagramPacket(bufr, bufr.length);		

		s.receive(p);

		String response = new String(p.getData());

		System.out.println(response);	
		
		s.close();

	}
}



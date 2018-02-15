
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Vector;
import java.net.InetAddress;

public class Client {

	String host;
	int port;
	String oper;
	Vector<String> args;	

	Client(String host, int port, String oper, Vector<String> args) throws IOException{

		String message = oper + " ";
		
		String thisisatest = "";
		

		System.out.println(thisisatest);
		
		if (oper == "register") {
			if (args.size() == 2) {
				message += args.get(0) + " ";
				message += args.get(1).substring(0, 256);
			}
			else
				System.out.println("Error on register");
		}
		else if (oper == "lookup"){
			if (args.size() == 1)
				message += args.get(0);
			else
				System.out.println("Error on Lookup");

		}
		
		byte[] buf = message.getBytes();
		DatagramSocket s = new DatagramSocket(port);
		DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getByName(host), port);
		
		s.send(p);
		
		//waits for answer
		s.receive(p);
		
		String response = p.getData().toString();
		
		System.out.println(response);		
		
	}

}

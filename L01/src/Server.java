
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Hashtable;

public class Server {

	Hashtable<String, String> table;

	Server(int port) throws IOException{

		DatagramSocket s = new DatagramSocket(port);
		DatagramPacket p = null;		
		
		s.receive(p);
		
		InetAddress ip = p.getAddress();

		byte[] data = p.getData();

		String request = new String(data);
		String splited[] = request.split(" ");
		String oper = splited[0];		
		String owner = table.get(splited[1]);

		String result = "Invalid operation!";

		if (oper == "register") {
			if (owner == null) {	
				table.put(splited[1], splited[2]);
				result = "Register successful!";
			}
			else
				result = "-1";
		}
		else if (oper == "lookup") {
			if (owner == null)
				result = "That plate does not exist!";
			else
				result = splited[1] + " " + owner;
		}
		
		byte[] response = result.getBytes();
		
		p = new DatagramPacket(response, response.length, ip, port);
		s.send(p);
		
		//infinite loop
	}

}

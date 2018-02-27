
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;


class Advertiser implements Runnable {

	private int port;
	private String mcastAddr;
	private int mcastPort;

	public Advertiser(int port, String mcastAddr, int mcastPort) {
		this.port = port;
		this.mcastAddr = mcastAddr;
		this.mcastPort = mcastPort;
	}

	@Override
	public void run() {

		InetAddress addr = null;

		try {
			addr = InetAddress.getByName(mcastAddr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DatagramSocket serverSocket = new DatagramSocket();

			String message = Integer.toString(port);
			byte[] msgBytes = message.getBytes();

			DatagramPacket packet = new DatagramPacket(msgBytes, msgBytes.length, addr, mcastPort);
			serverSocket.send(packet);

			serverSocket.close();
			
			System.out.println("Port sent");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class Server {

	private static Hashtable<String, String> table = new Hashtable<String,String>();

	private static int TIME_INTERVAL = 1000;

	public static void main(String[] args) throws IOException {		

		addHashValues();

		int port = Integer.parseInt(args[0]);
		String mcastAddr = args[1];
		int mcastPort = Integer.parseInt(args[2]);

		Advertiser advertiser = new Advertiser(port, mcastAddr, mcastPort);
		Thread thread = new Thread(advertiser);

		byte[] buf = new byte[256];
		

		while(true) {

			scheduleThread(thread);

			DatagramSocket s = new DatagramSocket(port);
			DatagramPacket p = new DatagramPacket(buf, buf.length);

			s.receive(p);

			s.close();

			s = new DatagramSocket();

			byte[] data = p.getData();
			String a = processMessage(data);
			byte[] response = a.getBytes();

			p = new DatagramPacket(response, response.length, ip, port);

			//serves as multicast
			System.out.println("multicast: " + mcastAddr + "" + mcastPort + ":" + port);

			s.send(p);

			s.close();

		}
	}	

	/**
	 * Schedules the thread
	 * @param thread Thread to be scheduled
	 */
	public static void scheduleThread(Thread thread) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				thread.start();
			}
		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 1 * TIME_INTERVAL; 

		// schedules the task to be run in an interval 
		timer.scheduleAtFixedRate(task, delay,
				intevalPeriod);
	}

	/**
	 * Processes message received by a Client
	 * @param data Data received
	 * @return Processed string
	 */
	public static String processMessage(byte[] data) {

		String request = new String(data);

		System.out.println("Request: " + request);

		String splited[] = request.trim().split(" ");
		String oper = splited[0];		
		String owner = table.get(splited[1]);

		String result = "Invalid operation!";

		if (oper.equals("register")) {
			if (owner == null) {	
				table.put(splited[1], splited[2]);
				result = "Register successful!";
			}
			else
				result = "-1";
		}
		else if (oper.equals("lookup")){
			if (owner == null)
				result = "That plate does not exist!";
			else
				result = splited[1] + " " + owner;
		}

		System.out.println(result);

		return result;
	}
	
	/**
	 * Adds values to the HashTable
	 */
	public static void addHashValues() {
		table.put("24-21-JL", "Quintino");
		table.put("53-56-LK", "Washington");
		table.put("24-65-JF", "Edward");
	}
}

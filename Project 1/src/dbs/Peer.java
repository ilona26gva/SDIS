package dbs;

public class Peer {
	static int id; //How to make it unique?
	
	public static void main(String[] args)
	{
		String protocolVersion = args[0];
		String APaddr = args[2];						//AP stands for Access Point
		int APport = Integer.parseInt(args[3]);
		String MCaddr = args[4];
		int MCport = Integer.parseInt(args[5]);
		String MDBaddr = args[6];
		int MDBport = Integer.parseInt(args[7]);
		String MDRaddr = args[8];
		int MDRport = Integer.parseInt(args[9]);
		
		id = Integer.parseInt(args[1]);
	}
}
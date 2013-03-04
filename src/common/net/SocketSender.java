package common.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketSender {
	private int port = 9050;
	private Socket socket = null;
	private DataOutputStream dataoutputstream = null;
	
	SocketSender( int port ) {
		this.port = port;
		
		try {
			socket = new Socket("localhost", this.port);
			dataoutputstream = new DataOutputStream( socket.getOutputStream() );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getPort() {
		return this.port;
	}
	public DataOutputStream getDataOutputStream() {
		return this.dataoutputstream;
	}
	
	public static void main( String[] agrs ) {
		SocketSender ss = new SocketSender(9050);
		DataOutputStream dos = ss.getDataOutputStream();
		String s = "First String";
		try {
			dos.write( s.getBytes() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ss = new SocketSender(7000);
		dos = ss.getDataOutputStream();
		s = "Second String";
		try {
			dos.write( s.getBytes() );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

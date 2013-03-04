package common.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/*
 * SocketListener is a base class.
 * It is used to listen to a certain port, (usually one module has to listen several ports) 
 * which get the information from the previous module.
 * Listener co-works with Sender.
 * While listener may listen to a certain port, sender can send information to multi-port. 
 */

public class SocketListener implements Runnable{
	int port = 9050;	//	the port to listen, the default port is 9050
	private ServerSocketChannel ssc = null;
	private ServerSocket ss = null;
	private InputStream inputStream = null;
	private ByteBuffer buffer = null;
	private OutputStream outputStream = null;
	private Selector selector = null;
	
	SocketListener() {
		this(9050);
	}
	
	SocketListener( int port ) {
		this.port = port;
		this.buffer = ByteBuffer.allocate(20);
		try {
			this.selector = Selector.open();
			this.ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ss = ssc.socket();
			InetSocketAddress address = new InetSocketAddress(this.port);
			ss.bind(address);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean setPort( int port ) {
		this.port = port;
		return true;
	}
	
	public int getPort( int port ) {
		return this.port;
	}
	
	//	need to check if the inputstream is null or not
	public InputStream getInputStream() {
		return this.inputStream;
	}
	
	@Override
	public void run() {
		while (true ) {	
			try {
				Thread.sleep(1000);
				selector.select();
			}
			catch (InterruptedException e) {	
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
			
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			SocketChannel sc;
			while ( iter.hasNext()) {
				SelectionKey key = iter.next();
				if ( (key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT ) {
					ServerSocketChannel subssc = (ServerSocketChannel) key.channel();
					try {
						sc = subssc.accept();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
					} catch (IOException e) {
						e.printStackTrace();
					}
					iter.remove();
				}
				else if ( (key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ )  {
					sc = (SocketChannel) key.channel();
					int len = 1;
					while ( len != 0 ) {
						//	Use a buffer to write into the basic class of DataStream
						len = 0;
						int flag = 1;
						while ( flag == 1 ) {
							synchronized( buffer ) {
								if ( buffer.hasRemaining() ) {
									flag = 1;
								}
								else {
									flag = 0;
								}
								if ( flag == 0 ) {
									try {
										len = sc.read(buffer);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
	public static void main( String[] args ) {
		SocketListener sl = new SocketListener();
		Thread t = new Thread(sl);
		t.start();
		InputStream dis = sl.getInputStream();
		
	}
}

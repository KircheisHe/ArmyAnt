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

import common.writable.StringWritable;
import common.writable.Writable;

/*
 * SocketListener is a base class.
 * It is used to listen to a certain port, (usually one module has to listen several ports) 
 * which get the information from the previous module.
 * Listener co-works with Sender.
 * While listener may listen to a certain port, sender can send information to multi-port. 
 */

public class SocketListener implements Runnable, Listener{
	int port = 9050;	//	the port to listen, the default port is 9050
	private ServerSocketChannel ssc = null;
	private ServerSocket ss = null;
	private ByteBuffer buffer = null;
	private Selector selector = null;
	private Writable stringwritable = null;
	private boolean isClose;
	private boolean isEnd;
	
	public SocketListener() {
		this(9050);
	}
	
	public SocketListener( int port ) {
		this.port = port;
		this.isClose = false;
		this.buffer = ByteBuffer.allocate(1024);
		this.stringwritable = new StringWritable();
		this.isEnd = false;
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
	
	/*
	 * used to get the writable interface.
	 */
	@Override
	public Writable getWritable() {
		return this.stringwritable;
	}
	
	@Override
	public boolean isEnd() {
		return this.isEnd;
	}
	
	/*
	 * used to indicate to close the listener.
	 */
	public void close() {
		this.isClose = true;
	}
	
	/*	main logic to read the material from the socket.
	 *  Loop until isClose is set true.
	 */
	@Override
	public void run() {
		// System.out.println("Socket Listener");
		while ( true && !isClose) {	
			//	Sleep some time before actually doing something.
			//	in a loop.
			try {
				Thread.sleep(50);
				selector.select();
			}
			catch (InterruptedException e) {	
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
			
			//	NIO's way to listen and receive information.
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
					try {
						{
							len = sc.read( buffer );
							if ( len > 0 ) {
								// System.out.println("buffer is " + new String(buffer.array()));
							
								buffer.flip();
								//	Loop in the method to wait for more space.
								stringwritable.write( buffer );
								buffer.clear();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	//	Main function is used to test the Socket Listener
	//	Along with Writable
	public static void main( String[] args ) {
		SocketListener sl = new SocketListener();
		Thread t = new Thread(sl);
		t.start();
		Writable sw = sl.getWritable();
		while ( true ) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String s = sw.read();
			if ( s != null  ) {
				System.out.println(s);
			}
		}
	}

	
}

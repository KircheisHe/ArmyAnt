package common.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketSender implements Runnable {
	private int port = 9050;
	private SocketChannel socketChannel = null;
	private StringWritable stringWritable = null;
	private ByteBuffer buffer = null;
	private Boolean isClose;
	
	SocketSender() {
		this(9050);
	}
	
	SocketSender( int port ) {
		this.port = port;
		this.isClose = false;
		try {
			this.stringWritable = new StringWritable();
			this.socketChannel = SocketChannel.open(new InetSocketAddress("localhost", port));
			this.socketChannel.configureBlocking(false);
			this.buffer = ByteBuffer.allocate(50);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getPort() {
		return this.port;
	}
	
	public StringWritable getWritable() {
		return this.stringWritable;
	}
	
	public void close() {
		this.isClose = true;
	}
	
	@Override
	public void run() {
		while ( true && !isClose) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String s = stringWritable.read();
			if ( s != null ) {
				buffer.clear();
				buffer.put(s.getBytes());
				buffer.flip();
				try {
					this.socketChannel.write(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main( String[] agrs ) throws InterruptedException {
		SocketSender ss = new SocketSender();
		Thread t = new Thread(ss);
		t.start();
		StringWritable sw = ss.getWritable();
		sw.write(ByteBuffer.wrap("First String".getBytes()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sw.write(ByteBuffer.wrap("After 1 seconds, two Strings".getBytes()));
		sw.write(ByteBuffer.wrap("After 1 seconds, 3".getBytes()));
		sw.write(ByteBuffer.wrap("After 1 seconds, four Strings".getBytes()));
		
		Thread.sleep(10000);
		ss.close();
	}

	
}

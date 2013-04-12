package runtime.node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import common.net.FileListener;
import common.net.Listener;
import common.net.Sender;
import common.net.SocketListener;
import common.net.SocketSender;
import common.writable.Writable;

import parser.core.InputNode;
import parser.core.ParseNode;

/*
 * RuntimeNode is an executor that runs during the execution.
 * It has an main method, to test the functions.
 */
public class RuntimeInputNode implements Runnable{
	private InputNode inputNode;
	private FileListener listener;
	private SocketSender sender;
	
	public RuntimeInputNode( InputNode in ) {
		this.inputNode = in;
		this.listener = new FileListener( in.getFile() );
		// System.out.println(in.getSingleNext().getSocketPort());
		}
	
	// 	It is easy, to do with the input node,
	//	Since the only output of the input node is to another socket port.
	//	And input node doesn't do any execution.
	@Override
	public void run() {
		// System.out.println("InputNode: " + this.inputNode.getName() + " File: " + this.inputNode.getFile());
		this.sender = new SocketSender( this.inputNode.getSingleNext().getSocketPort() );
		Thread t1 = new Thread(this.listener);
		t1.start();
		Thread t2 = new Thread(this.sender);
		t2.start();
		Writable fw = this.listener.getWritable();
		Writable sw = this.sender.getWritable();
		while ( !this.listener.isEnd() || fw.hasMore() ) {
			String s = fw.read();
			if ( s != null) {
				// System.out.println(s);
				sw.write( s );
			}
			else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	

}

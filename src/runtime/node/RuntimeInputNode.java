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
	private Listener listener;
	private Sender sender;
	
	public RuntimeInputNode( InputNode in ) {
		this.inputNode = in;
		this.listener = new FileListener( in.getFile() );
		this.sender = new SocketSender( in.getSingleNext().getSocketPort() );
	}
	
	// 	It is easy, to do with the input node,
	//	Since the only output of the input node is to another socket port.
	//	And input node doesn't do any execution.
	@Override
	public void run() {
		Writable fw = this.listener.getWritable();
		Writable sw = this.sender.getWritable();
		while ( !this.listener.isEnd() ) {
			sw.write( fw.read() );
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	

}

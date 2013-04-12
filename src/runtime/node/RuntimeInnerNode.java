package runtime.node;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Vector;

import parser.core.InnerNode;
import parser.core.MapList;
import parser.core.ParseNode;

import common.net.FileListener;
import common.net.Listener;
import common.net.Sender;
import common.net.SocketListener;
import common.net.SocketSender;
import common.writable.Writable;

/*
 * InnerNode must have multiple output, with only one input.
 */
public class RuntimeInnerNode implements Runnable {
	private InnerNode innerNode;
	private SocketListener listener;
	private MapList<String, SocketSender> mapSender;
	private MapList<String, Writable> mapWritable;
	private Method executor;
	private Method distributor;
	
	public RuntimeInnerNode( InnerNode in ) {
		this.innerNode = in;
		
		this.listener = new SocketListener( in.getSocketPort());
		Thread t1 = new Thread(this.listener);
		t1.start();
		this.mapSender = new MapList<String,SocketSender>();
		
	}
	
	
	// 	It is easy, to do with the input node,
	//	Since the only output of the input node is to another socket port.
	//	And input node doesn't do any execution.
	@Override
	public void run() {
		this.mapWritable = new MapList<String, Writable>();
		MapList<String, ParseNode> msp = this.innerNode.getNext();
		SocketSender socketSender;
		for ( String ss : msp.keySet() ) {
			Vector<ParseNode> vpn = msp.get(ss);
			for ( ParseNode pn : vpn ) {
				socketSender = new SocketSender(pn.getSocketPort());
				Thread t2 = new Thread(socketSender);
				t2.start();
				mapSender.addPair(ss, socketSender);
				mapWritable.addPair(ss, socketSender.getWritable());
			}
		}
		Writable lw = this.listener.getWritable();
		String sr, sd;
		Vector<Writable> vw;
		while ( !this.listener.isEnd() ) {
			sr = lw.read();
			if ( sr != null ) {
				sr = this.innerNode.execute(sr);
				// System.out.println(sr);
				sd = this.innerNode.distribute(sr);
				// System.out.println(sd);
				vw = this.mapWritable.get(sd);
				if ( vw != null ) {
					for ( Writable writable : vw ) {
						writable.write( sr );
						// System.out.println(sr);
					}
				}
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

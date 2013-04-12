package runtime.node;

import common.net.FileSender;
import common.net.Listener;
import common.net.Sender;
import common.net.SocketListener;
import common.writable.Writable;

import parser.core.OutputNode;

public class RuntimeOutputNode implements Runnable {
	private OutputNode outputNode;
	private SocketListener listener;
	private FileSender sender;
	
	public RuntimeOutputNode( OutputNode on) {
		this.outputNode = on;
		this.listener = new SocketListener(on.getSocketPort());
		this.sender = new FileSender(on.getFile());
	}
	
	@Override
	public void run() {
		Thread t1 = new Thread(this.listener);
		t1.start();
		Thread t2 = new Thread(this.sender);
		t2.start();
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

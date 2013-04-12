package common.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import common.writable.StringWritable;
import common.writable.Writable;

/*
 * FileListener is a class that wrap the file reader into writable interface.
 * Just like SocketListener does for socket.
 */
public class FileListener implements Runnable, Listener {
	private String fileName;
	private File file;
	private FileReader fileReader;
	private BufferedReader br;
	private Writable writable = null;
	private CharBuffer buffer;
	private boolean isClose;
	private boolean isEnd;
	
	public FileListener( String name) {
		this.fileName = name;
		this.isClose = false;
		this.isEnd = false;
		this.buffer = CharBuffer.allocate(1024);
		this.writable = new StringWritable();
		try {
			this.fileReader = new FileReader( new File(name) );
			this.br = new BufferedReader(this.fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		this.isClose = true;
	}
	
	public Writable getWritable() {
		return this.writable;
	}
	
	public boolean isEnd() {
		return this.isEnd;
	}
	
	@Override
	public void run() {
		// System.out.println("File Listener");
		int len = 1;
		String s = null;
		while ( true && !isClose ) {
			try {
				s = br.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if ( s != null ) {
				System.out.println(s);
				this.writable.write(s);
			}
			else {
				this.isEnd = true;
				break;
			}
		}
	}
	
	/**
	 * Test the logic.
	 */
	public static void main(String[] args) {
		FileListener fl = new FileListener("/home/kircheis/Playground/github/ArmyAnt/README");
		Thread t = new Thread(fl);
		t.start();
		Writable sw = fl.getWritable();
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

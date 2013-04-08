package common.net;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import common.writable.StringWritable;
import common.writable.Writable;

public class FileSender implements Runnable, Sender {
	private Writable writable = null; 
	private FileWriter fileWriter;
	private boolean isClose;
	
	public FileSender( String name ) {
		try {
			this.fileWriter = new FileWriter( new File(name), true );
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.writable = new StringWritable();
		this.isClose = false;
	}
	
	public void close() {
		this.isClose = true;
	}
	
	@Override
	public void run() {
		int count = 0;
		while ( true && !isClose ) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String s = this.writable.read();
			if ( s != null ) {
				try {
					this.fileWriter.write(s);
					this.fileWriter.write("\n");
					count++;
					if ( count > 100 ) {
						this.fileWriter.flush();
						count = 0;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			this.fileWriter.flush();
			this.fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Writable getWritable() {
		return this.writable;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileSender fs = new FileSender("/home/kircheis/Playground/github/ArmyAnt/aa");
		Thread t = new Thread(fs);
		t.start();
		Writable sw = fs.getWritable();
		sw.write(ByteBuffer.wrap("First String".getBytes()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sw.write(ByteBuffer.wrap("After 1 seconds, two Strings".getBytes()));
		sw.write(ByteBuffer.wrap("After 1 seconds, 3".getBytes()));
		sw.write(ByteBuffer.wrap("After 1 seconds, four Strings".getBytes()));
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fs.close();
	}

}

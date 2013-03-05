package common.net;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.LinkedList;

/*
 * 	Sychronized Class to store String object.
 * 	Two main method.
 *  implements the Writable interface.
 * 	write to get the information from the ByteBuffer.
 * 	read to get the first String from the class for further work.
 * 
 * 	String writable assumes the usage of UTF-8 Charset,
 * 	And use it to convert buffer to String and vise-versa.
 */
public class StringWritable implements Writable {
	//	Actually place to store the Strings
	private LinkedList<String> listString = null;
	
	//	And indicator, default is 100
	private int capacity;
	
	//	Three of the below is used to convert buffer to string.
	private CharBuffer charBuffer;
	private Charset charSet;
	private CharsetDecoder decoder;
	
	StringWritable() {
		this(100);
	}
	
	StringWritable( int cap ) {
		this.capacity = cap;
		this.listString = new LinkedList<String>(); 
		this.charSet =  Charset.forName( "UTF-8" );
		this.decoder  =  this.charSet.newDecoder();
	}
	
	/*
	 * write the buffer into a string.
	 * @see common.net.Writable#write(java.nio.ByteBuffer)
	 */
	@Override
	public void write( ByteBuffer buffer ) {
		try {
			charBuffer = decoder.decode(buffer);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		String s = charBuffer.toString();
		while( true ) {
			synchronized( this.listString ) {
				if ( listString.size() < capacity ) {
					this.listString.add(s);
					return;
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * return and remove the first String in the list.
	 * return NULL if no String.
	 */
	@Override
	public String read() {
		synchronized( this.listString ) {
			return this.listString.pollFirst();
		}
	}
	
	/**
	 * @param args
	 * @throws CharacterCodingException 
	 */
	public static void main(String[] args) throws CharacterCodingException {
		CharBuffer cb;
		Charset charset;
		ByteBuffer bb = ByteBuffer.wrap(("This is a bytebuffer".getBytes()));
		charset  =  Charset.forName( "UTF-8" );
		CharsetDecoder decoder  =  charset.newDecoder();
		cb =  decoder.decode(bb);
		System.out.println(cb.toString());
		
		bb.clear();
		bb.put("No".getBytes());
		bb.flip();
		cb =  decoder.decode(bb);
		System.out.println(cb.toString());	
	}
}

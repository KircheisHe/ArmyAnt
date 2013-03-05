package common.writable;

import java.nio.ByteBuffer;

/*
 * Writable is the interface to use over the socket.
 * Two main function is included.
 * @write() and 
 * @read()
 */
public interface Writable {
	
	/*
	 * write is a function used to convert the buffer into
	 * String and store in the class.
	 */
	public void write( ByteBuffer buffer);
	
	/*
	 * read is convert the inner string out.
	 */
	public String read();
}

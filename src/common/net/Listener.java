package common.net;

import common.writable.Writable;

/*
 * 
 */
public interface Listener {
	
	public boolean isEnd();
	
	public Writable getWritable();
}

package parser.core;

import java.lang.reflect.Method;

/*
 * InnerNode is one basic kind of node in the DAG.
 * It is not the input nor the output.
 * So, InnerNode will need a executor to do the processing and a distributor to distribute
 * the data.
 * 
 * check() is used to check whether the corresponding class is correct or not.
 */

public class InnerNode extends ParseNode {
	private MapList<String, String> next;
	
	public InnerNode() {
		super();
		this.next = new MapList<String, String>();
	}
	
	public void addKV(String key, String value) {
		this.next.addPair(key, value);
	}
	
	public MapList<String, String> getKVs(){
		return this.next;
	}
	
	@Override
	public boolean check() {
		// use super.check() to check the executor, then to check the distributor.
		if ( super.check() ) {
			try {
				this.distributor = Class.forName(this.dis);
			} catch (ClassNotFoundException e) {
				return false;
			}
			try {
				this.disMethod = this.distributor.getMethod("distribute", String.class);
			} catch (SecurityException e) {
				return false;
			} catch (NoSuchMethodException e) {
				return false;
			}
			if ( checkNext() ) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	//	to check whether the distributor is correct.
	//	to do.
	private boolean checkNext() {
		return true;
	}
	
	public static void main(String[] args) {
		
	}

}

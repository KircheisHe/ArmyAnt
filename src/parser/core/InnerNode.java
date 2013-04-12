package parser.core;

import java.lang.reflect.InvocationTargetException;
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
	private MapList<String, String> nextString;
	
	public InnerNode() {
		super();
		this.nextString = new MapList<String, String>();
	}
	
	public String execute( String input ) {
		try {
			return (String) ( this.proMethod.invoke( this.executor.newInstance(), input));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String distribute( String input ) {
		try {
			return (String) ( this.disMethod.invoke( this.distributor.newInstance(), input));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//	Print the InnerNode
	public void print() {
		System.out.println( "Node Name: " + this.name );
		for ( String ss : this.next.keySet() ) {
			System.out.println("Key: " + ss);
			System.out.print("Value: ");
			for ( ParseNode pn : this.next.get(ss)) {
				System.out.print(pn.getName() + " ");
			}
			System.out.println();
		}
		System.out.println( "End Node");
	}
	
	public void addKV(String key, String value) {
		this.nextString.addPair(key, value);
	}
	
	public MapList<String, String> getKVs(){
		return this.nextString;
	}
	
	@Override
	public boolean check() {
		// use super.check() to check the executor, then to check the distributor.
		if ( super.check() ) {
			try {
				this.distributor = Class.forName(this.dis);
			} catch (ClassNotFoundException e) {
				System.out.println("InnerNode " + this.name + " : " + " Distributor " + this.dis + " cannot be found.");
				return false;
			}
			try {
				this.disMethod = this.distributor.getMethod("distribute", String.class);
			} catch (SecurityException e) {
				System.out.println("InnerNode " + this.name + " : " + " Distributor " + this.dis + "'s ditribute method cannot be reached due to security problems.");
				return false;
			} catch (NoSuchMethodException e) {
				System.out.println("InnerNode " + this.name + " : " + " Distributor " + this.dis + "'s ditribute method cannot be found.");
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

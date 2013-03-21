package parser.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/*
 * ParseNode is a basic node for representing a certain node 
 * in the DAG.
 * 
 * InputNode, OutputNode, InnerNode are all son-classs of the ParseNode.
 * 
 */
public class ParseNode {
	private String name;
	private String exe;
	private Class<?> executor;
	private Vector<ParseNode> next;
	
	// Constructor of a ParseNode.
	ParseNode() {
		this.name = null;
		this.exe = null;
		this.next = new Vector<ParseNode>();
	}
	
	ParseNode( String name ) {
		this();
		this.name = name;
	}
	
	ParseNode( String name, String exe ) {
		this(name);
		this.exe = exe;
	}
	
	/*
	 * ParseNode need to check whether the jar class can be find.
	 * By check(), ParseNode reflects the String into a java class.
	 */
	boolean check() {
		try {
			this.executor = Class.forName(this.exe);
		} catch (ClassNotFoundException e) {
			return false;
		}
		try {
			Method method = this.executor.getMethod("processor", String.class);
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {

	}

}

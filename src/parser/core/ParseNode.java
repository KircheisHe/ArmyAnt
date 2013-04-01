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
 * check(), addNext() are two main class.
 * 
 */
public class ParseNode {
	// Node Name
	protected String name;
	
	//	Executor Name
	protected String exe;
	
	//	Executor class, by reflecting the String exe.
	protected Class<?> executor;
	
	//	Distributor Name
	protected String dis;
	
	//	Distributor Class
	protected Class<?> distributor;
	
	//	The next ParseNode that this node is connected.
	protected Vector<ParseNode> next;
	
	// process method
	protected Method proMethod;
	
	// distribute method
	protected Method disMethod;
	
	// Constructor of a ParseNode.
	public ParseNode() {
		this.name = null;
		this.exe = null;
		this.next = new Vector<ParseNode>();
	}
	
	public ParseNode( String name ) {
		this();
		this.name = name;
	}
	
	public ParseNode( String name, String exe ) {
		this(name);
		this.exe = exe;
	}
	
	/*
	 * Set the distribution class.
	 * For the output node, the distribution class may not be applicable.
	 */
	public void setDis(String ss) {
		this.dis = ss;
		return;
	}
	
	/*
	 * ParseNode need to check whether the jar class can be find.
	 * By check(), ParseNode reflects the String into a java class.
	 */
	public boolean check() {
		// First, check the executor
		try {
			this.executor = Class.forName(this.exe);
		} catch (ClassNotFoundException e) {
			return false;
		}
		try {
			this.proMethod = this.executor.getMethod("processor", String.class);
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
		return true;
	}
	
	/*
	 * Add a new ParseNode as one next node of the current node.
	 */
	public void addNext( ParseNode pn ) {
		this.next.add(pn);
		return;
	}
	
	public static void main(String[] args) {

	}

}
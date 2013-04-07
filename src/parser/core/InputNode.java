package parser.core;

import java.io.File;

/*
 * A input node,
 * which get the information from a certain file or something else.
 * It is different from the InnerNode or the OutputNode.
 * InputNode basically will not have any executor or distributor.
 * 
 * InputNode should have only one output to a certain node.
 */
public class InputNode extends ParseNode {
	private String input;
	private File file;
	private ParseNode next;
	private String nextString;
	
	public InputNode() {
		this.input = null;
		this.next = null;
	}
	
	public InputNode(String inputFile) {
		this.input = inputFile;
		this.next = null;
	}
	
	//	Print the InputNode
	public void print() {
		System.out.println( "Node Name: " + this.name );
		System.out.println( "File Name: " + this.input );
		System.out.println( "Next Node Name: " + this.next.getName() );
		System.out.println( "End Node");
	}
	
	// Set file
	public boolean setFile(String fileName) {
		this.input = fileName;
		return true;
	}
	
	public String getFile() {
		return this.input;
	}
	
	// Three method deals with the next String
	public boolean addNext(ParseNode pn) {
		this.next = pn;
		return true;
	}
	
	public boolean setNextString(String ss) {
		this.nextString = ss;
		return true;
	}
	
	public String getNextString() {
		return this.nextString;
	}
	/*
	 *	to check whether the target file is exist or not;
	 */
	@Override
	public boolean check() {
		this.file = new File(input);
		if( !this.file.exists() ) {
			return false;
		}
		if ( this.next == null ) return false;
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputNode in = new InputNode("/home/kircheis/Playground/github/ArmyAnt/aa");
		if ( in.check() == false ) {
			System.out.println("The file doesn't exist");
		}
		else {
			System.out.println("Exists");
		}
	}

}

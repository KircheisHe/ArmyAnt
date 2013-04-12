package parser.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
	private FileReader fileReader;
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
	
	public ParseNode getSingleNext() {
		return this.next;
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
		if ( this.next == null ) {
			return false;
		}
		try {
			this.fileReader = new FileReader(this.input);
		} catch (FileNotFoundException e) {
			System.out.println("InputNode " + this.name + " :" + " File " + this.input + " cannot be found");
			return false;
		}
		return true;
	}
	
	public FileReader getFileReader() {
		return this.fileReader;
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

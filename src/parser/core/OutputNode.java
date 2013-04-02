package parser.core;

import java.io.File;

/*
 * OutputNode, is used to write output into a certain file.
 */
public class OutputNode extends ParseNode {
	private String output;
	private File file;
	
	public OutputNode( String ss ) {
		this.output = ss;
	}
	
	@Override
	public boolean check() {
		this.file = new File(this.output);
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}

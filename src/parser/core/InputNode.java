package parser.core;

import java.io.File;

/*
 * A input node,
 * which get the information from a certain file or something else.
 * It is different from the InnerNode or the OutputNode.
 * InputNode basically will not have any executor or distributor.
 */
public class InputNode extends ParseNode {
	private String input;
	private File file;
	
	public InputNode(String inputFile) {
		this.input = inputFile;
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

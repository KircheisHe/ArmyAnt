package parser.xml;

import parser.core.InputNode;

public class InputParser {
	private XmlParser xp;
	
	public InputParser(XmlParser xmlParser) {
		this.xp = xmlParser;
	}
	
	public InputNode parse() {
		InputNode inputNode = new InputNode();
		String ss;
		while ( true ) {
			ss = this.xp.getNextString();
			if ( ss.equals("/Input")) {
				return inputNode;
			}
			else if ( ss.equals("Name")) {
				inputNode.setName(this.xp.getNextString());
				this.xp.getNextString();
			}
			else if ( ss.equals("File")) {
				inputNode.setFile(this.xp.getNextString());
				this.xp.getNextString();
			}
			else if ( ss.equals("Next")) {
				inputNode.setNextString(this.xp.getNextString());
				this.xp.getNextString();
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

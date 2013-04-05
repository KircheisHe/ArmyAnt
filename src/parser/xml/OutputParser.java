package parser.xml;

import parser.core.InputNode;
import parser.core.OutputNode;

public class OutputParser {
private XmlParser xp;
	
	public OutputParser(XmlParser xmlParser) {
		this.xp = xmlParser;
	}
	
	public OutputNode parse() {
		OutputNode outputNode = new OutputNode();
		String ss;
		while ( true ) {
			ss = this.xp.getNextString();
			if ( ss.equals("/Output")) {
				return outputNode;
			}
			else if ( ss.equals("Name")) {
				outputNode.setName(this.xp.getNextString());
				this.xp.getNextString();
			}
			else if ( ss.equals("File")) {
				outputNode.setFile(this.xp.getNextString());
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

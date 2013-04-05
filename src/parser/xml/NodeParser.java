package parser.xml;

import parser.core.InnerNode;


public class NodeParser {
private XmlParser xp;
	
	public NodeParser(XmlParser xmlParser) {
		this.xp = xmlParser;
	}
	
	public InnerNode parse() {
		InnerNode innerNode = new InnerNode();
		String ss;
		while ( true ) {
			ss = this.xp.getNextString();
			if ( ss.equals("/Node")) {
				return innerNode;
			}
			else if ( ss.equals("Name")) {
				innerNode.setName(this.xp.getNextString());
				this.xp.getNextString();
			}
			else if ( ss.equals("Executor")) {
				innerNode.setExe(this.xp.getNextString());
				this.xp.getNextString();
			}
			else if ( ss.equals("Dispatcher")) {
				while ( true ) {
					ss = this.xp.getNextString();
					if ( ss.equals("/Dispatcher")) {
						break;
					}
					else if ( ss.equals("Class")) {
						innerNode.setDis(this.xp.getNextString());
						this.xp.getNextString();
					}
					else if ( ss.equals("KV")) {
						String key = null, value = null;
						for ( int i = 0; i < 2; ++i ) {
							ss = this.xp.getNextString();
							if ( ss.equals("Key")) {
								key = this.xp.getNextString();
							}
							else {
								value = this.xp.getNextString();
							}
							this.xp.getNextString();
						}
						innerNode.addKV(key, value);
						this.xp.getNextString();
					}
				}
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

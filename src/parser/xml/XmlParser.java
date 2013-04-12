package parser.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import parser.core.InnerDAG;
import parser.core.InnerNode;
import parser.core.InputNode;
import parser.core.MapList;
import parser.core.OutputNode;
import parser.core.ParseNode;

/*
 * XmlParser reads the XML, and converts the XML into an InnerDAG.
 * 
 */
public class XmlParser {
	private File configurationFile;
	private FileReader fr;
	
	public XmlParser() {
		this("/home/kircheis/Playground/github/ArmyAnt/conf/example.xml");
	}
	
	public XmlParser( String fileName ) {
		this.configurationFile = new File(fileName);
	}
	
	/*
	 * To do, to check whether the target XML is valid or not.
	 */
	public boolean check() {
		return true;
	}
	
	/*
	 * Get the Key String between '<' and '>'
	 */
	public String getKeyString() {
		String s = new String();
		int state = 1;
		char ch;
		while ( true ) {
			try {
				ch = (char)this.fr.read();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			if ( ch == ' ') {
				state = 0;
			}
			else if ( ch == '<' ) {
				state = 1;
				s = new String();
			}
			else if ( ch == '>' ) {
				return s;
			}
			else if ( state == 1 ){
				s += ch;
			}
		}
	}
	
	public String getString() {
		String s = new String();
		int state = 1;
		char ch;
		while ( true ) {
			try {
				ch = (char)this.fr.read();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			//System.out.print(ch);	
			if ( ch == ' ') {
				state = 0;
			}
			else if ( ch == '<' ) {
				return s;
			}
			else if ( ((ch > 'a' && ch < 'z') || ( ch > 'A' && ch < 'Z') || ( ch == '.'))){
				if ( state == 0 ) {
					s = new String();
					state = 1;
				}
				else {
					s += ch;
				}
			}
		}
	}
	
	/*
	 * get the next String in the XML file.
	 * Return null, if we get to the end.
	 */
	public String getNextString() {
		String s = new String();
		int state = 0;
		int now;
		char ch;
		while ( true ) {
			try {
				now = this.fr.read();
				if ( now == -1 ) return null;
				ch = (char)now;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			if ( ch == ' ' || ch == '\r') {
				state = 0;
				continue;
			}
			else if ( ch == '<' ) {
				//	At the beginning of the File.
				if ( s.equals("") ) {
					state = 1;	// start to parse the key string.
					continue;
				}
				else {
					// end of the current String, return;
					// System.out.println(s);
					return s;
				}
			}
			else if ( ch == '>' ) {
				// end of the current key string.
				// System.out.println(s);
				return s;
			}
			else if ( state == 1 ) {
				s += ch;
				continue;
			}
			else if ( ((ch >= 'a' && ch <= 'z') || ( ch >= 'A' && ch <= 'Z') || ( ch == '.')) || ( ch >= '0' && ch <= '9') || ( ch == '/')) {
				s += ch;
				continue;
			}
		}
	}
	
	/*
	 * Main Logic of parse the XML file.
	 * return null, if it meet some thing wrong with the next clause.
	 */
	public InnerDAG parse() {
		this.fr = null;
		InnerDAG iDAG = new InnerDAG();
		//	First, read the file, using FileReader.
		try {
			this.fr = new FileReader(this.configurationFile);
		} catch (FileNotFoundException e) {
			System.out.println("File " + this.configurationFile.getPath() +" can not be found.");
			return null;
		}
		
		//	Read the file, using getNextString() function.
		char ch;
		Map<String, ParseNode> tempMap = new HashMap<String, ParseNode>();
		Vector<InputNode> inputNodes = new Vector<InputNode>();
		Vector<InnerNode> innerNodes = new Vector<InnerNode>();
		Vector<OutputNode> outputNodes = new Vector<OutputNode>();
		
		//	Parse the XML file, and build up the InnerDAG object.
		while ( true ) {
			String s = getNextString();
			if ( s == null ) {
				break;
			}
			else if ( s.equals("XML") ) {
				continue;
			}
			else if ( s.equals("/XML")) {
				break;
			}
			else if ( s.equals("Input")) {
				InputParser inputParser = new InputParser(this);
				InputNode in = inputParser.parse();
				iDAG.addInput(in);
				
				tempMap.put(in.getName(), in);	//	add the name and the node into the map.
				
				// System.out.println(tempMap.get(in.getName()).getName());
				inputNodes.add(in);
				// System.out.println(in.getFile());
				// System.out.println(in.getName());
				// System.out.println(in.getNextString());
			}
			else if ( s.equals("Node")) {
				NodeParser nodeParser = new NodeParser(this);
				InnerNode in = nodeParser.parse();
				iDAG.addInner(in);
				
				// System.out.println(in.getName());
				tempMap.put(in.getName(), in);
				innerNodes.add(in);

				/*
				System.out.println(in.getName());
				System.out.println(in.getExe());
				System.out.println(in.getDis());
				Vector<String> output = in.getKVs().get("1");
				for ( String ss : output) {
					System.out.println(ss);
				}
				output = in.getKVs().get("2");
				for ( String ss : output) {
					System.out.println(ss);
				}
				break;
				*/
			}
			else if ( s.equals("Output")) {
				OutputParser outputParser = new OutputParser(this);
				OutputNode on = outputParser.parse();
				iDAG.addOutput(on);
				
				// System.out.println(on.getName());
				tempMap.put(on.getName(), on);
				outputNodes.add(on);
				// System.out.println(on.getFile());
				// System.out.println(on.getName());
			}		
			
		}
		
		
		//	Then, Build up the edge in the iDAG.
		
		//for ( String ss : tempMap.keySet() ) {
		//	System.out.println(ss);
		//}
		
		//	add next to the input nodes
		for ( InputNode in : inputNodes) {
			// System.out.println(tempMap.get(in.getNextString()).getName());
			if ( tempMap.get(in.getNextString()) == null ) {
				return null;
			}
			else {
				in.addNext(tempMap.get(in.getNextString()));
			}
		}
		
		// 	and also to the inner nodes
		for ( InnerNode in : innerNodes) {
			MapList<String, String> maplist = in.getKVs();
			for ( String ss : maplist.keySet() ) {
				Vector<String> vs = maplist.get(ss);
				for ( String st : vs ) {
					// System.out.println(st);
					if ( tempMap.get(st) == null ) {
						return null;
					}
					in.addNext(ss, tempMap.get(st));
				}
			}
		}
		
		//	After parse, print the nodes for check.
		/*
		for ( InputNode in : inputNodes) {
			in.print();
		}
		for ( InnerNode in : innerNodes) {
			in.print();
		}
		*/
		
		/*
		 * After parse the ParseNode,
		 * we need to give every node a socket port for it to receive information.
		 * InputNode doesn't need these port.
		 * InnerNode and OutputNode need such port for each of them.
		 */
		int currentPort = 10100;
		for ( InnerNode in : innerNodes ) {
			// 	First, test the current is port.
			//	If the port is in use, port++;
			while ( isUsed(currentPort) ) {
				currentPort++;
			}
			//	Now, the current port is now not in use, assign it to the inner node.
			in.setSocketPort(currentPort);
			currentPort++;
		}
		for ( OutputNode on : outputNodes ) {
			while ( isUsed(currentPort) ) {
				currentPort++;
			}
			on.setSocketPort(currentPort);
			currentPort++;
		}
		
		
		//	After assigning socket number to every node, the parse part can be done.
		//	Return the iDAG as a whole.
		return iDAG;
	}
	
	/*
	 * Test a certain port is used or not.
	 * Try to new a class of ServerSocket and to see whether it will throw an exception.
	 * If an exception is thrown, then the port is in use, we need to move on.
	 * After all, we need to close the server.
	 */
	private boolean isUsed( int port ) {
		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(port);
		}
		catch( Exception e ) {
			return true;
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlParser xp = new XmlParser();
		xp.parse();
		
		/*
		if ( !xp.isUsed(10100) ) {
			System.out.println("10100 is not used, first");
		}
		if ( !xp.isUsed(10100)) {
			System.out.println("10100 is not used, second");
		}
		*/
	}

}

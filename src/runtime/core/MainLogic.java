package runtime.core;

import java.util.Vector;

import parser.core.InnerDAG;
import parser.core.InnerNode;
import parser.core.InputNode;
import parser.core.OutputNode;
import parser.xml.XmlParser;
import runtime.node.RuntimeInnerNode;
import runtime.node.RuntimeInputNode;
import runtime.node.RuntimeOutputNode;

/*
 * A entrance class, to work on all the things.
 */
public class MainLogic {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector<Thread> threads = new Vector<Thread>();
		String xmlFile = "/home/kircheis/Playground/github/ArmyAnt/conf/example.xml";
		XmlParser xmlParser = new XmlParser(xmlFile);
		
		// System.out.println("First Part");
		
		//	First, check the XML is valid or not.
		if ( !xmlParser.check() ) {
			return;
		}
		
		// System.out.println("Second Part");
		
		//	Parse the XML file, and make an iDAG object.
		InnerDAG iDAG = xmlParser.parse();
		if ( !iDAG.check() ) {
			return;
		}
		else {
			for ( InputNode in : iDAG.getInput() ) {
				RuntimeInputNode rin = new RuntimeInputNode(in);
				// System.out.println(in.getName() + " " + in.getSocketPort());
				threads.add(new Thread(rin));
			}
			for ( InnerNode in : iDAG.getInner()) {
				RuntimeInnerNode rin = new RuntimeInnerNode(in);
				threads.add(new Thread(rin));
			}
			for ( OutputNode on : iDAG.getOutput()) {
				RuntimeOutputNode ron = new RuntimeOutputNode(on);
				threads.add(new Thread(ron));
			}
		}
		for ( Thread t : threads ) {
			t.start();
		}
		while ( true ) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

package parser.core;

import java.util.HashSet;
import java.util.Vector;

/*
 * InnerDAG is a graph that store the sequence of the data stream.
 * By parsing the XML or other files, the parser generate a instance of InnerDAG.
 * After checking the DAG's feasibility, other process may start the processing.
 * 
 * InnerDAG contains three kinds of node,
 * InputNode, InnerNode and OutputNode.
 * As the innerNode is containing the distribution information,
 * InnerDAG needs only store the node's information, and the start node.
 * 
 * The engine needs to start with the InnerDAG, and build the process.
 * 
 */
public class InnerDAG {
	
	private Vector<InputNode> input;
	private Vector<InnerNode> inner;
	private Vector<OutputNode> output;
	
	
	public InnerDAG() {
		this.input = new Vector<InputNode>();
		this.inner = new Vector<InnerNode>();
		this.output = new Vector<OutputNode>();
	}
	
	/*
	 * add an new input node.
	 */
	public boolean addInput( InputNode in ) {
		if ( this.input.contains(in) ) {
			return false;
		}
		this.input.add(in);
		return true;
	}
	
	/*
	 * add an new inner node.
	 */
	public boolean addInner( InnerNode in ) {
		if ( this.inner.contains(in)) {
			return false;
		}
		this.inner.add(in);
		return true;
	}
	
	/*
	 * add an new output node.
	 */
	public boolean addOutput( OutputNode on ) {
		if ( this.output.contains(on) ) {
			return false;
		}
		this.output.add(on);
		return true;
	}
	
	/*
	 * use ParseNode's check to check every node.
	 * 
	 * by check(), InnerDAG calls the ParseNode's check() method.
	 * 
	 */
	public boolean check() {
		for (InputNode in : this.input) {
			if (!in.check()) {
				// System.out.println("Input Node " + in.getName() + " is not valid");
				return false;
			}
		}
		for (InnerNode in : this.inner) {
			if (!in.check()) {
				// System.out.println("Inner Node " + in.getName() + " is not valid");
				return false;
			}
		}
		for (OutputNode on : this.output) {
			if (!on.check()) {
				System.out.println("Output Node " + on.getName() + " is not valid");
				return false;
			}
		}
		return true;
	}
	
	public Vector<InputNode> getInput() {
		return this.input;
	}
	
	public Vector<InnerNode> getInner() {
		return this.inner;
	}
	
	public Vector<OutputNode> getOutput() {
		return this.output;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

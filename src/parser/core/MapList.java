package parser.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/*
 * MapList is a class to store key-value pairs, in which, one key may correspond to multiple values.
 */
public class MapList<Key, Value> {
	private Map<Key, Vector<Value> > maplist;
	
	public MapList() {
		this.maplist = new HashMap<Key, Vector<Value> >();
	}
	
	public Set<Key> keySet() {
		return this.maplist.keySet();
	}
	
	/*
	 * addPair is used to add a <Key, Value> into the MapList.
	 */
	public boolean addPair(Key k, Value v ) {
		if ( !this.maplist.containsKey(k) ) {
			this.maplist.put(k, new Vector<Value>());
		}
		if ( this.maplist.get(k).contains(v) ) {
			return false;
		}
		this.maplist.get(k).add(v);
		return true;
	}
	
	/*
	 * get Method is used to get all the Values that are related to the certain key.
	 * Specifically, if there is no such value, return a empty Vector<Value>.
	 */
	public Vector<Value> get(Key key) {
		if ( !this.maplist.containsKey(key) ) {
			return new Vector<Value>();
		}
		return this.maplist.get(key);
	}
	
	public static void main( String args[] ) {
		MapList mp = new MapList<Integer, String>();
		mp.addPair(1, "What is this");
		mp.addPair(1, "What is that");
		mp.addPair(1, "What is this");
		mp.addPair(2, "Haha");
		Vector<String> output = mp.get(1);
		for (String ss : output) {
			System.out.println(ss);
		}
	}
}

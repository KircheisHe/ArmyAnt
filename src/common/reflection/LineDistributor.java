package common.reflection;

import java.util.Map;

/*
 * Line Distributor is a distributor that classifies a String
 * into an integer, which indicates its classification.
 */
public interface LineDistributor {
	/*
	 * 
	 */
	public Map<Integer, String > distribute( String sin );
}

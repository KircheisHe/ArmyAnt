package common.reflection;

import java.util.HashMap;
import java.util.Map;

/*
 * An example of distributor.
 */
public class LineDistributorExample implements LineDistributor {
	@Override
	public Map<Integer, String> distribute(String s) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, s);
		return map;
	}

}

package example.first;

import common.reflection.LineProcessor;

public class FirstStep implements LineProcessor {

	@Override
	public String processor(String sin) {
		// System.out.println(sin);
		return sin.toLowerCase();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

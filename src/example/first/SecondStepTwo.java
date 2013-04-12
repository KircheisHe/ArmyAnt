package example.first;

import common.reflection.LineProcessor;

public class SecondStepTwo implements LineProcessor {

	@Override
	public String processor(String sin) {
		// System.out.println(sin);
		return sin.toUpperCase();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

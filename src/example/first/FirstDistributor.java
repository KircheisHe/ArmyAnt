package example.first;

import common.reflection.LineDistributor;

public class FirstDistributor implements LineDistributor {

	@Override
	public String distribute(String sin) {
		//System.out.println(sin.charAt(0));
		if ( sin.charAt(0) < 'a'+13 ) {
			// System.out.println(sin + " 1");
			return "1";
		}
		else {
			// System.out.println(sin + " 2");
			return "2";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char ch = 'a';
		char ch2 = 'z';
		if ( ch2 < 'a' + 13) {
			System.out.println(ch2);
		}
		else {
			System.out.println(ch);
		}

	}

}

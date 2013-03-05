package common.reflection;

/*
 * A single example of LinPreProcessor
 * Which replace all the 'a's in the string with 'b'
 */
public class LineProcessorExample implements LineProcessor {

	@Override
	public String processor(String sin) {
		return sin.replace('a', 'b');
	}
}

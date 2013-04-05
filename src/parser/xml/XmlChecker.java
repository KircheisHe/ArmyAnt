package parser.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
 * XmlChecker is used to check the validity of the certain XML file.
 */

public class XmlChecker {
	private File file;
	private FileReader fr;
	
	public XmlChecker() {
		this("/home/kircheis/Playground/github/ArmyAnt/conf/example.xml");
	}
	
	public XmlChecker(String FileName ) {
		this.file = new File(FileName);
		
	}
	
	/*
	 * To do, check whether the Xml file is valid.
	 */
	public boolean check() {
		try {
			this.fr = new FileReader(this.file);
		} catch (FileNotFoundException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

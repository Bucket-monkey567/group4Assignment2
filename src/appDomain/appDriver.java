package appDomain;

import java.io.File;

public class appDriver {
	
	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		File f = null;
		String fileName = args[0];
		try {
			if (fileName.startsWith("-")) {
				fileName = fileName.substring(1);
			}
			 f = XMLValidator.check(fileName);
			 parser.parse(f);
		} catch(Exception e) {
			System.out.println("System Error");
		}
		
	}
}

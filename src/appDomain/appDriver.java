package appDomain;

import java.io.File;

public class appDriver {
	
	public static void main(String[] args) {
		
		XMLValidator v = new XMLValidator();
		XMLParser parser = new XMLParser();
		
		File f = XMLValidator.check("sample2.xml");
		parser.parse(f);
	}
}

package appDomain;

import java.io.File;

public class appDriverAddedForTest {
	public static void main(String[] args) {
		XMLValidator xmlValidator = new XMLValidator();
		XMLParser p = new XMLParser();
		File file = XMLValidator.check("sample2.xml");
		p.parse(file);
	}
}

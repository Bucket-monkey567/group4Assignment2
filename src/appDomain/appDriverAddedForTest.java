package appDomain;

import java.io.File;

public class appDriverAddedForTest {
	public static void main(String[] args) {
		XMLValidator xmlValidator = new XMLValidator();
		File file = XMLParser.checkFileName("sample2.xml");
		xmlValidator.parse(file);
	}
}

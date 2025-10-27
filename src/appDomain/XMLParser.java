package appDomain;

import java.io.File;

public class XMLParser {
	
	static public File checkFileName(String fileName) {
	    if (fileName == null || fileName.isEmpty()) {
	        System.out.println("Invalid file name.");
	        return null;
	    }
	
	    // Clean up possible quotes or spaces
	    fileName = fileName.replace("\"", "").trim();
	    File file = new File(fileName);
	
	    // Try alternate paths if file not found in working directory
	    if (!file.exists()) {
	        File userDirRelative = new File(System.getProperty("user.dir"), fileName);
	        if (userDirRelative.exists()) return file = userDirRelative;
	
	        File resRelative = new File(System.getProperty("user.dir") + File.separator + "res", fileName);
	        if (resRelative.exists()) {
	        	return file = resRelative;
	        }
	
	        // Attempt to locate relative to JAR execution path
	        try {
	            String jarDir = new File(XMLValidator.class.getProtectionDomain()
	                    .getCodeSource()
	                    .getLocation()
	                    .toURI())
	                    .getParent();
	            File jarRelative = new File(jarDir, fileName);
	            if (jarRelative.exists()) return file = jarRelative;
	        } catch (Exception e) {
	            return null;
	        }
	    }
		return null;	
	}

}


package appDomain;

import java.io.File;

/**
 * Utility class for parsing XML file-related command-line arguments
 * and validating file existence across multiple fallback locations.
 *
 * <p>This class is designed for apps that are run either from IntelliJ,
 * a terminal, or a packaged JAR, where the XML file may be located
 * in different folders (working directory, /res folder, or JAR directory).</p>
 */
public class XMLValidator {

    /**
     * Parses the command-line arguments and attempts to extract
     * the first valid XML filename.
     *
     * <p>This method filters out irrelevant Java runtime arguments
     * (such as "java", "-jar", "*.jar") and returns the first argument
     * that ends with ".xml". It also normalizes characters that users
     * sometimes enter incorrectly such as fancy quotes or long dashes.</p>
     *
     * @param args Command-line arguments passed to the program
     * @return The extracted XML filename, or {@code null} if none found
     */
    public static String parseArgs(String args[]) {
        if (args == null || args.length == 0) return null;

        for (String arg : args) {
            if (arg == null || arg.isEmpty()) continue;

            // Normalize special characters to avoid parsing issues
            arg = arg.replace('–', '-')        // long dash
                     .replace('—', '-')        // em dash
                     .replace("\"", "")        // remove quotes
                     .replace("“", "")
                     .replace("”", "")
                     .trim()
                     .replace("\\", "/");      // unify slashes

            String lower = arg.toLowerCase();

            // Ignore arguments related to Java execution itself
            if (lower.equals("java") || lower.equals("-jar") || lower.endsWith(".jar"))
                continue;

            // Return first argument that looks like an XML file
            if (lower.endsWith(".xml"))
                return arg.trim();
        }
        return null;
    }

    /**
     * Attempts to validate the supplied filename and resolve
     * its physical location by checking several fallback paths:
     *
     * <ol>
     *   <li>Current working directory</li>
     *   <li>Working directory + /res/ folder</li>
     *   <li>Directory where the running JAR is located</li>
     * </ol>
     *
     * @param fileName The XML file name provided by the user or CLI parser
     * @return A {@link File} object pointing to the actual file, or {@code null} if not found
     */
    public static File check(String fileName) {
        // Basic null/empty validation
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Invalid file name.");
            return null;
        }

        // Clean input (remove quotes, trim spaces)
        fileName = fileName.replace("\"", "").trim();
        File file = new File(fileName);

        // 1. Check: current working directory
        if (!file.exists()) {

            // 2. Check relative to user working directory (IntelliJ / terminal)
            File userDirRelative = new File(System.getProperty("user.dir"), fileName);
            if (userDirRelative.exists())
                return userDirRelative;

            // 3. Check /res folder under working directory
            File resRelative = new File(System.getProperty("user.dir") + File.separator + "res", fileName);
            if (resRelative.exists())
                return resRelative;

            // 4. Check JAR execution directory (if running from a packaged jar)
            try {
                String jarDir = new File(XMLValidator.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI())
                        .getParent();

                File jarRelative = new File(jarDir, fileName);
                if (jarRelative.exists())
                    return jarRelative;

            } catch (Exception e) {
                // JAR path may not be retrievable when running from an IDE
            }
        }

        // If still not found, print full absolute path for debugging
        if (!file.exists()) {
            System.out.println("❌ File not found: " + file.getAbsolutePath());
            return null;
        }

        return file;
    }
}

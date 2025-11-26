package appDomain;

import java.io.File;

/**
 * appDriver
 * ---------------------------------------------------------------
 * Entry point for the XML Validator + Parser application.
 *
 * Responsibilities:
 *   1. Read and validate command-line arguments
 *   2. Use XMLValidator to:
 *        - Extract XML filename from args
 *        - Locate the physical file on disk
 *   3. Pass the valid File object to XMLParser for processing
 *
 * Expected usage:
 *     java -jar XMLValidator.jar -f example.xml
 *
 * Error handling:
 *     Any unexpected exception results in the message: "System Error"
 */
public class appDriver {

    public static void main(String[] args) {

        XMLParser parser = new XMLParser();
        File file = null;

        try {
            // Extract filename from arguments (e.g., -f test.xml)
            String fileName = XMLValidator.parseArgs(args);

            // Locate the file on disk (searches working dir, /res, jar path)
            file = XMLValidator.check(fileName);

            // If file not found, stop execution
            if (file == null) {
                System.out.println("Unable to locate file: " + fileName);
                return;
            }

            // Begin XML parsing
            parser.parse(file);

        } catch (Exception e) {
            System.out.println("System Error");
        }
    }
}

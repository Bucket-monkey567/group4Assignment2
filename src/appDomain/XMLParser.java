package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;

import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;

/**
 * XMLParser
 * ---------------------------------------------------------------
 * A lightweight XML validator that:
 *  - Reads an XML file line-by-line
 *  - Tracks opening tags using a stack
 *  - Detects mismatched, missing, or extra closing tags
 *  - Uses two queues:
 *      errorQ  → stores missing/mismatched opening tags
 *      extrasQ → stores extra closing tags
 *
 * The parser extracts tags in the form <tag> or </tag>.
 */
public class XMLParser {

    // Stack of expected closing tags (stores Tag objects)
    MyStack<Tag> stack;

    // Queue of unmatched/missing opening tags
    MyQueue<Tag> errorQ;

    // Queue of extra/mismatched closing tags
    MyQueue<Tag> extrasQ;

    // Tracks current line number
    int counter;

    public XMLParser() {
        counter = 1;
        stack = new MyStack<>();
        errorQ = new MyQueue<>();
        extrasQ = new MyQueue<>();
    }

    /**
     * Parses an XML file and processes each line.
     * Skips commented lines (<!-- -->) and XML declaration (<?xml ...)
     *
     * @param file the XML file to parse
     */
    public void parse(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                counter++;
                line = line.trim();

                // Ignore comments & xml declarations
                if (!(line.startsWith("<!--") && line.endsWith("-->"))
                        && !line.startsWith("<?xml")) {
                    processXMLLine(line);
                }
            }

            // After file is finished, handle leftover unmatched tags
            processRemaining();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts tags from a line using < and > positions.
     *
     * @param line the XML line
     */
    private void processXMLLine(String line) {
        int openSymbol = line.indexOf("<");
        int closeSymbol = line.indexOf(">");

        // Extract all tags in the current line
        while (openSymbol != -1 && closeSymbol != -1) {
            String tag = line.substring(openSymbol, closeSymbol + 1);

            processTag(tag, counter);

            openSymbol = line.indexOf("<", openSymbol + 1);
            closeSymbol = line.indexOf(">", closeSymbol + 1);
        }

        // If a < or > is missing, it's an error
        if (!((openSymbol != -1) == (closeSymbol != -1))) {
            System.out.println("Line " + counter + ": extra symbol found");
        }
    }

    /**
     * Processes a single XML tag, determines if it is:
     *   - opening tag     <tag>
     *   - closing tag     </tag>
     *   - self-closing    <tag/>
     *
     * @param tag full XML tag string
     * @param lineNumber current line
     */
    private void processTag(String tag, int lineNumber) {

        // ---------- Opening Tag ----------
        if (tag.startsWith("<") && !tag.endsWith("/>") && !tag.startsWith("</")) {

            tag = handleOpeningTag(tag); // remove attributes
            stack.push(new Tag(tag, lineNumber));
        }

        // ---------- Closing Tag ----------
        else if (tag.startsWith("</") && tag.endsWith(">")) {

            // Convert closing tag </a> into normalized opening form <a>
            String tagName = "<" + tag.substring(2, tag.length() - 1) + ">";

            // Case 1: Perfect match on stack top
            if (!stack.isEmpty() && stack.peek().tag.equals(tagName)) {
                stack.pop();
            }

            else try {

                // Case 2: The closing tag matches something waiting in errorQ
                if (!errorQ.isEmpty() && tagName.equals(errorQ.peek().tag)) {
                    errorQ.dequeue();
                }

                // Case 3: No opening tags exist → extra closing tag
                else if (stack.isEmpty()) {
                    stack.push(new Tag(tag, lineNumber));
                }

                // Case 4: Search the stack for a matching opening tag
                else {
                    boolean found = false;
                    MyStack<Tag> temp = new MyStack<>();

                    // Copy of the error queue (used for rollback)
                    MyQueue<Tag> originalErrorQ = new MyQueue<>(errorQ);

                    // Search downwards through the stack
                    while (!stack.isEmpty()) {
                        Tag t = stack.pop();

                        if (t.tag.equals(tagName)) {
                            found = true;

                            // Everything popped before finding the match is an error
                            while (!temp.isEmpty()) {
                                Tag tempTag = temp.pop();
                                System.out.println("Error at line "
                                        + tempTag.line + ": " + tempTag.tag);
                            }
                            break;
                        } else {
                            // Unmatched opening tags go to errorQ
                            errorQ.enqueue(t);
                            temp.push(t);
                        }
                    }

                    // If still no match found → extra/unexpected closing tag
                    if (!found) {
                        // Restore stack and errorQ to original
                        while (!temp.isEmpty()) stack.push(temp.pop());
                        errorQ = originalErrorQ;

                        // Store extra closing tag
                        extrasQ.enqueue(new Tag(tag, lineNumber));
                    }
                }
            } catch (NullPointerException | EmptyStackException | EmptyQueueException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles remaining unmatched tags after file ends.
     * Anything left in stack = missing closing tags.
     * Anything left in extrasQ = unmatched closing tags.
     */
    private void processRemaining() {

        // Move all leftover stack tags into error queue
        while (!stack.isEmpty()) {
            errorQ.enqueue(stack.pop());
        }

        // Try to match errorQ and extrasQ entries
        while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
            try {
                if (!errorQ.peek().tag.equals(extrasQ.peek().tag.replace("/", ""))) {
                    System.out.println(errorQ.dequeue().tag +
                            " does not match " + extrasQ.peek().tag);
                } else {
                    errorQ.dequeue();
                    extrasQ.dequeue();
                }
            } catch (EmptyQueueException e) {
                e.printStackTrace();
            }
        }

        // Print remaining entries
        System.out.println();
        while (!(errorQ.isEmpty() == extrasQ.isEmpty())) {

            while (!errorQ.isEmpty()) {
                try {
                    System.out.println("ErrorQ: " + errorQ.dequeue().tag);
                } catch (EmptyQueueException e) {
                    e.printStackTrace();
                }
            }

            while (!extrasQ.isEmpty()) {
                try {
                    System.out.println("ExtrasQ: " + extrasQ.dequeue().tag);
                } catch (EmptyQueueException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // -----------------------------------------------
        // SUCCESS MESSAGE
        // -----------------------------------------------
        if (errorQ.isEmpty() && extrasQ.isEmpty()) {
            System.out.println("XML is well-formed,no errors found");
        }
    }

    /**
     * Removes attributes from tags.
     * Example: <note id="1"> → <note>
     */
    private String handleOpeningTag(String openingTag) {
        if (openingTag.split(" ").length > 1) {
            return openingTag.split(" ")[0] + ">";
        }
        return openingTag;
    }
}

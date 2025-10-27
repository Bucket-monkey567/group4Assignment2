package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
	
public class XMLParser {
    // Stack and queues store Tag objects instead of plain strings
    Stack<Tag> stack;
    LinkedList<Tag> errorQ;
    LinkedList<Tag> extrasQ;

    int counter;

    public XMLParser() {
        counter = 1;
        stack = new Stack<>();
        errorQ = new LinkedList<>();
        extrasQ = new LinkedList<>();
    }

    public void parse(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                counter++;
                line = line.trim();
                if (!(line.startsWith("<!--" )&& line.endsWith("-->"))
                		&& !(line.startsWith("<?xml")))
                processXMLLine(line);
            }

            processRemaining();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processXMLLine(String line) {
        int openSymbol = line.indexOf("<");
        int closeSymbol = line.indexOf(">");

        while (openSymbol != -1 && closeSymbol != -1) {
            String tag = line.substring(openSymbol, closeSymbol + 1);
            processTag(tag, counter);

            openSymbol = line.indexOf("<", openSymbol + 1);
            closeSymbol = line.indexOf(">", closeSymbol + 1);
        }

        if (!((openSymbol != -1) == (closeSymbol != -1))) {
            System.out.println("Line " + counter + ": extra symbol found");
        }
    }

    private void processTag(String tag, int lineNumber) {
        if (tag.startsWith("<") && !tag.endsWith("/>") && !tag.startsWith("</")) {
            // Opening tag
            tag = handleOpeningTag(tag);
            stack.push(new Tag(tag, lineNumber));
        } else if (tag.startsWith("</") && tag.endsWith(">")) {
            // Closing tag
            String tagName = "<" + tag.substring(2, tag.length() - 1) + ">"; // normalized opening tag
            if (!stack.isEmpty() && stack.peek().tag.equals(tagName)) {
                // Normal match
                stack.pop();
            } else if (!errorQ.isEmpty() && tagName.equals(errorQ.peek().tag)) {
            	errorQ.poll();
            } else if (stack.isEmpty()) {
            	stack.add(new Tag(tag, lineNumber));
            } else {
                // Look for the matching opening tag in the stack
            	boolean found = false;
            	Stack<Tag> temp = new Stack<>();
            	LinkedList<Tag> originalErrorQ = new LinkedList<>(errorQ); // create a copy of errorQ

            	while (!stack.isEmpty()) {
            	    Tag t = stack.pop();
            	    if (t.tag.equals(tagName)) {
            	        found = true;
            	        // print errors for any tags that were popped before finding the match
            	        while (!temp.isEmpty()) {
            	            Tag tempTag = temp.pop();
            	            System.out.println("Error at line " + tempTag.line + ": " + tempTag.tag);
            	        }
            	        break; // found match, stop popping
            	    } else {
            	        // unmatched tag → add to errorQ
            	        errorQ.add(t);
            	        temp.push(t);
            	    }
            	}

            	if (!found) {
            	    // no matching opening tag → restore stack and revert errorQ to original
            	    while (!temp.isEmpty()) stack.push(temp.pop());
            	    errorQ = originalErrorQ; // restore errorQ
            	    extrasQ.add(new Tag(tag, lineNumber));
            	}
            }
        }
    }

    private void processRemaining() {
        while (!stack.isEmpty()) {
            errorQ.add(stack.pop());
        }

        while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
            if (!errorQ.peek().tag.equals(extrasQ.peek().tag.replace("/", ""))) {
            	System.out.println(errorQ.poll().tag + " does not match " + extrasQ.peek().tag);
            } else {
            	errorQ.poll();
            	extrasQ.poll();
            }
        }
        
        System.out.println();
        while (!(errorQ.isEmpty() == extrasQ.isEmpty())) {
        	while (!errorQ.isEmpty()) {
        		System.out.println("ErrorQ: " + errorQ.poll().tag);
        	}
        	while (!extrasQ.isEmpty()) {
        		System.out.println("ExtrasQ: " + extrasQ.poll().tag);
        	}        	
        }
    }

    private String handleOpeningTag(String openingTag) {
        if (openingTag.split(" ").length > 1) {
            return openingTag.split(" ")[0] + ">";
        }
        return openingTag;
    }
}


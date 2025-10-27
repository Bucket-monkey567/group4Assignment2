package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class XMLValidator {
    Stack<Tag> stack;
    LinkedList<Tag> errorQ;
    LinkedList<Tag> extrasQ;
    
    boolean errorFound;
    ArrayList<Integer> extraSymbols;
    int counter;

    public XMLValidator() {
        counter = 0; // start at 0, increment when reading lines
        errorFound = false;
        extraSymbols = new ArrayList<>();
        stack = new Stack<>();
        errorQ = new LinkedList<>();
        extrasQ = new LinkedList<>();
    }

    public void parse(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                counter++;
                line = line.trim();
                if (!(line.startsWith("<!--") && line.endsWith("-->"))
                		&& !(line.startsWith("<?xml"))) 
                	processXMLLine(line);               
            }

            for (Integer lineNumber : extraSymbols) {
            	System.out.println("Line " + lineNumber + ": extra symbol");
            }
            processRemaining();
            if (!errorFound) System.out.println("No error found");
        } catch (Exception e) {
            System.out.println("File not found");
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
            extraSymbols.add((Integer)counter);
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
            } else if (!errorQ.isEmpty() && errorQ.peek().tag.equals(tagName)) {
            	errorQ.poll();
            } else if (stack.empty()) {
            	errorQ.add(new Tag(tagName, lineNumber));
            } else {
            	boolean found = false;
            	Stack<Tag> temp = new Stack<>();
            	LinkedList<Tag> originalErrorQ = new LinkedList<>(errorQ); // create a copy of errorQ

            	while (!stack.isEmpty()) {
            	    Tag t = stack.pop();
            	    if (t.tag.equals(tagName)) {
            	        found = true;
            	        errorFound = true;
            	        // print errors for any tags that were popped before finding the match
            	        while (!temp.isEmpty()) {
            	            Tag tempTag = temp.pop();
            	            System.out.println("Error at line " + tempTag.line + ": " + tempTag.tag
            	            		+ " is not constructed correctly");
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

        System.out.println();
    	while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
    		if (errorQ.peek().tag.equals(extrasQ.peek().tag)) {
    			errorQ.poll();
    			extrasQ.poll();
    		} else {	
    			System.out.println("ErrorQ: " + errorQ.poll().tag + " does not match " + extrasQ.peek().tag);
    		}
    	}        	
   
    	System.out.println();
    	while (!errorQ.isEmpty()) {
    		Tag tag = errorQ.poll();
    		System.out.print("ErrorQ: "+ tag.tag + " at line " + tag.line);
    	}
    	while (!extrasQ.isEmpty()) {
    		Tag tag = extrasQ.poll();
    		System.out.println("ExtrasQ: " + tag.tag + " at line " + tag.line);
    	}        	
        
    }

    private String handleOpeningTag(String openingTag) {
        if (openingTag.split(" ").length > 1) {
            return openingTag.split(" ")[0] + ">";
        }
        return openingTag;
    }
}
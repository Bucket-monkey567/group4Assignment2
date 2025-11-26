package appDomain;

/**
 * Tag
 * ---------------------------------------------------------------
 * A simple data structure representing a single XML tag and
 * the line number where it appeared in the file.
 *
 * This is used throughout the XMLParser to track:
 *   - Opening tags pushed onto the stack
 *   - Tags stored in the error queue (errorQ)
 *   - Extra closing tags stored in extrasQ
 *
 * Fields:
 *   tag  → The exact XML tag string (e.g., "<note>", "</body>")
 *   line → Line number in the source file where the tag was found
 */
public class Tag {

    /** The tag string (formatted as <tag> or </tag>) */
    String tag;

    /** Line number where the tag appears */
    int line;

    /**
     * Constructs a Tag object.
     *
     * @param tag  the XML tag string
     * @param line the line number this tag appears on
     */
    public Tag(String tag, int line) {
        this.tag = tag;
        this.line = line;
    }

    @Override
    public String toString() {
        return tag + " (line " + line + ")";
    }
}

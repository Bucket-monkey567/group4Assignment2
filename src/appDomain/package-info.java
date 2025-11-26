/**
 * Application domain classes used by the XML parser application.
 *
 * <p>
 * This package contains the high-level parser and helper classes that operate
 * on XML files and the parser output. Key classes include:
 * <ul>
 *   <li>{@code XMLParser} — reads and parses XML files using the project's
 *       stack and queue implementations and reports structural errors.</li>
 *   <li>{@code XMLValidator} — locates and validates the input file path and
 *       attempts a few common relative lookups (working directory, res folder,
 *       JAR location).</li>
 *   <li>{@code Tag} — simple value object representing a parsed XML tag and its
 *       source line number.</li>
 *   <li>{@code appDriver} — small CLI driver that accepts a filename argument
 *       and invokes the parser.</li>
 * </ul>
 * </p>
 *
 * <p>These classes depend on the implementations package for the Stack and
 * Queue data structures and on the exceptions package for custom exceptions.</p>
 *
 * @author Alexander Raagas, Minh Tam, Mrinal Jha, Noah Zschogner
 * @since 2025
 */
package appDomain;
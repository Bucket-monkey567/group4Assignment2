/**
 * Custom exception types used across the assignment.
 *
 * <p>
 * This package contains small, focused exception classes that model error
 * conditions specific to the data structures and parser:
 * <ul>
 *   <li>{@code EmptyQueueException} — thrown when queue operations are
 *       attempted on an empty queue.</li>
 *   <li>{@code StackUnderflowException} — thrown when stack operations are
 *       attempted on an empty stack (or otherwise invalid stack operations).</li>
 * </ul>
 * </p>
 *
 * <p>Using specific exception classes (rather than only runtime exceptions)
 * helps tests and calling code precisely detect and handle expected error
 * situations.</p>
 *
 * @author Alexander Raagas, Minh Tam, Mrinal Jha, Noah Zschogner
 * @since 2025
 */
package exceptions;
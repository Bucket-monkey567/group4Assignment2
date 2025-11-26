/**
 * Implementations of the assignment's data structures.
 *
 * <p>
 * This package contains concrete implementations required by the assignment,
 * implemented without using {@code java.util} collection classes for storage
 * where the assignment forbids them. Typical classes:
 * <ul>
 *   <li>{@code MyArrayList<E>} — array-backed list implementation.</li>
 *   <li>{@code MyDLL<E>} and {@code MyDLLNode<E>} — doubly-linked list and
 *       node classes used by the list and by the queue.</li>
 *   <li>{@code MyStack<E>} — stack implementation built on {@code MyArrayList}.</li>
 *   <li>{@code MyQueue<E>} — queue implementation built on {@code MyDLL}.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Implementation notes:
 * <ul>
 *   <li>The list and node classes manually manage {@code prev} / {@code next}
 *       links and the list {@code size} field to preserve O(1) head/tail
 *       operations and predictable traversal performance.</li>
 *   <li>Where arrays are used, resizing uses {@code Arrays.copyOf} or
 *       {@code System.arraycopy} (allowed by the assignment).</li>
 *   <li>Methods follow the ADT contracts defined in {@code utilities.ListADT},
 *       including the specified exception and return-value behavior.</li>
 * </ul>
 * </p>
 *
 * @author Alexander Raagas, Minh Tam, Mrinal Jha, Noah Zschogner
 * @since 2025
 */
package implementations;

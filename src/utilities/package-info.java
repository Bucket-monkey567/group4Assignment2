/**
 * Abstract Data Type (ADT) interfaces and small utility interfaces used by the project.
 *
 * <p>
 * The utilities package declares the ADTs that the assignment requires students
 * to implement. Interfaces in this package define the behavioural contracts
 * (methods, preconditions, postconditions and expected exceptions) for concrete
 * implementations in {@code implementations}. Important interfaces include:
 * <ul>
 *   <li>{@code ListADT<E>} — generic linear list contract (add/get/remove/set/iterator/etc.).</li>
 *   <li>{@code Iterator<E>} — simple forward-only iterator used by the custom lists.</li>
 *   <li>{@code StackADT<E>} and {@code QueueADT<E>} — stack and queue contracts used by the parser.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The project follows a strict separation: the ADTs live in {@code utilities}
 * and implementations live in {@code implementations}. Tests and driver code
 * operate against the ADTs so that implementations can be swapped without
 * modifying test code.
 * </p>
 *
 * @author Alexander Raagas, Minh Tam, Mrinal Jha, Noah Zschogner
 * @since 2025
 */
package utilities;

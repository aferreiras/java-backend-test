package events.enterprise.test;

import org.jetbrains.annotations.*;

/**
 * An iterator over a metric collection
 */
public interface EventIteratorInterface extends AutoCloseable {
    /**
     * Move the iterator to the next metric, if any.
     * @return false if iterator has reached the end, true otherwise
     */
    boolean moveNext();

    /**
     * Get the current metric
     * @return the metric instance
     * @throws IllegalStateException if {@link #moveNext} was never called
     *                               or its last result was {@code false}.
     */
    @NotNull
    Event current();

    /**
     * Remove the current metric from the store
     */
    void remove();
}
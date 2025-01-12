package events.enterprise.test;

import org.jetbrains.annotations.*;

/**
 * For this challenge, you should implement an in-memory metric store.
 */
public interface EventStoreInterface {
    /**
     * Store a metric
     */
    void insert(@NotNull Event metric);

    /**
     * Remove all metrics of `name`
     */
    void removeAll(@NotNull String name);

    /**
     * Retrieve an iterator for metrics of `name` in the specified range
     * @param name      the name of the queried metrics
     * @param from      start timestamp (inclusive)
     * @param to        end timestamp (exclusive)
     * @return the iterator
     */
    @NotNull
    EventIterator query(@NotNull String name, long from, long to);
}
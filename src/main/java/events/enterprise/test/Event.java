package events.enterprise.test;

import org.jetbrains.annotations.*;

/**
 * Basic implementation of the Metric abstraction
 */
public class Event {

    @NotNull
    private final String name;
    private final long timestamp;

    public Event(@NotNull String name, long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @NotNull
    public String toString() {
        return "{name='" + name + "', timestamp=" + timestamp + "}";
    }
}

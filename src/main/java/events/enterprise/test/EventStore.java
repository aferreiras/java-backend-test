package events.enterprise.test;

import org.jetbrains.annotations.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventStore implements EventStoreInterface {
    @NotNull
    private final Map<String, List<Event>> metricMap = new ConcurrentHashMap<>();

    @Override
    public void insert(@NotNull Event metric) {
        metricMap.computeIfAbsent(metric.getName(), metricName -> new ArrayList<>()).add(metric);
    }

    @Override
    public void removeAll(@NotNull String name) {
        metricMap.remove(name);
    }

    @Override
    @NotNull
    public EventIterator query(@NotNull String name, long from, long to) {
        List<Event> metrics = metricMap.getOrDefault(name, Collections.emptyList());

        if (metrics.isEmpty()) {
            return new EventIterator(metrics);
        }

        List<Event> filteredEvents = metrics.stream().filter(metric -> metric.getTimestamp() >= from && metric.getTimestamp() < to).toList();

        return new EventIterator(filteredEvents);
    }
}

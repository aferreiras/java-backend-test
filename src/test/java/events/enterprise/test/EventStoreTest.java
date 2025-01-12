package events.enterprise.test;

import org.jetbrains.annotations.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EventStoreTest {

    @NotNull
    private EventStore store;
    @NotNull
    private final List<Event> metrics = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        store = new EventStore();
        metrics.add(new Event("temperature",1735745252));
        metrics.add(new Event("glucose",1735745252));
        metrics.add(new Event("pressure",1735745252));
    }
    @Test
    public void insertAndQueryTest() {
        store.insert(metrics.getFirst());
        store.insert(metrics.get(1));

        try (EventIterator iterator = store.query("temperature", 1735745250, 1735745255)){
            iterator.moveNext();
            assertEquals(iterator.current(), metrics.getFirst());
        }

        try (EventIterator iterator = store.query("glucose", 1735745250, 1735745255)){
            iterator.moveNext();
            assertNotEquals(iterator.current(), metrics.getFirst());
            assertEquals(iterator.current(), metrics.get(1));
        }

        try (EventIterator iterator = store.query("pressure", 1735745250, 1735745255)){
            boolean moved = iterator.moveNext();
            assertFalse(moved);
        }

    }

    @Test
    public void removeAllTest() {
        store.insert(metrics.getFirst());
        store.insert(metrics.get(1));
        Event repeatedNameMetric = new Event("glucose",1735745253);
        store.insert(repeatedNameMetric);

        try (EventIterator iterator = store.query("glucose", 1735745250, 1735745255)){
            iterator.moveNext();
            assertEquals(iterator.current(), metrics.get(1));
            iterator.moveNext();
            assertEquals(iterator.current(), repeatedNameMetric);
        }

        store.removeAll("glucose");

        try (EventIterator iterator = store.query("glucose", 1735745250, 1735745255)){
            boolean moved = iterator.moveNext();
            assertFalse(moved);
        }

    }
}
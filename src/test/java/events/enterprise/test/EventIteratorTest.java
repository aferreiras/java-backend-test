package events.enterprise.test;

import org.jetbrains.annotations.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class EventIteratorTest {
    @NotNull
    private final List<Event> metrics = new ArrayList<>();

    @BeforeEach
    void setup(){
        metrics.add(new Event("temperature",1735745252));
        metrics.add(new Event("glucose",1735745252));
        metrics.add(new Event("pressure",1735745252));
    }

    @Test
    public void moveNextTest() {
        try (EventIterator iterator = new EventIterator(metrics)) {

            boolean moved = iterator.moveNext();
            assertTrue(moved);

            moved = finishIterations(iterator);

            assertFalse(moved);
        }

    }

    @Test
    public void currentTest() {
        try (EventIterator iterator = new EventIterator(metrics)) {
            assertThrows(IllegalStateException.class, iterator::current);

            boolean moved = iterator.moveNext();
            assertEquals(iterator.current(),metrics.getFirst());

            moved = finishIterations(iterator);

            assertFalse(moved);
            assertThrows(IllegalStateException.class, iterator::current);
        }

    }

    @Test
    public void removeTest() {
        try (EventIterator iterator = new EventIterator(metrics)) {
            assertThrows(IllegalStateException.class, iterator::remove);

            iterator.moveNext();
            assertEquals(iterator.current(), metrics.getFirst());
            iterator.remove();
            assertEquals(iterator.current(), metrics.get(1));
            finishIterations(iterator);

            assertThrows(IllegalStateException.class, iterator::current);
        }
    }

    private boolean finishIterations(@NotNull EventIterator iterator) {
        boolean moved = true;
        for (Event event : metrics) {
            moved = iterator.moveNext();
        }

        return moved;
    }
}


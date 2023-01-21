import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.shakhov.eventstatistic.EventStatistic;
import io.shakhov.eventstatistic.EventStatisticImpl;
import org.junit.Before;
import org.junit.Test;

public class EventStatisticImplTest {

    private static final double EPS = 1e-6;

    private EventStatistic eventStatistic;
    private ControllableClock clock;

    @Before
    public void setUp() {
        clock = new ControllableClock();
        eventStatistic = new EventStatisticImpl(clock);
    }

    @Test
    public void testEmptyStatistic() {
        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        assertTrue(statistic.isEmpty());
    }

    @Test
    public void testNotOccurredEvent() {
        double statistic = eventStatistic.getEventStatisticByName("not_occurred");
        assertEquals(0, statistic, EPS);
    }

    @Test
    public void testSimultaneousEvents() {
        Instant now = LocalDateTime.of(2023, 1, 21, 18, 29).toInstant(ZoneOffset.UTC);
        clock.setInstant(now);
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("B");

        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        assertEquals(2, statistic.size());
        assertEquals(2 / 30., statistic.get("A"), EPS);
        assertEquals(1 / 30., statistic.get("B"), EPS);
    }

    @Test
    public void testEventEviction() {
        Instant now = LocalDateTime.of(2023, 1, 21, 18, 29).toInstant(ZoneOffset.UTC);
        clock.setInstant(now);
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("B");

        clock.setInstant(clock.instant().plus(1, ChronoUnit.HOURS));
        eventStatistic.incEvent("C");

        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        assertEquals(1, statistic.size());
        assertEquals(1 / 30., statistic.get("C"), EPS);
    }

    @Test
    public void testStatisticAtHourStart() {
        Instant now = LocalDateTime.of(2023, 1, 21, 18, 0).toInstant(ZoneOffset.UTC);
        clock.setInstant(now);
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("A");

        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        assertEquals(1, statistic.size());
        assertEquals(2, statistic.get("A"), EPS);
    }

    @Test
    public void testStatisticByName() {
        Instant now = LocalDateTime.of(2023, 1, 21, 18, 29).toInstant(ZoneOffset.UTC);
        clock.setInstant(now);
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("B");

        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        for (String eventName : statistic.keySet()) {
            assertEquals(statistic.get(eventName), eventStatistic.getEventStatisticByName(eventName), EPS);
        }
        clock.setInstant(clock.instant().plus(1, ChronoUnit.HOURS));
        eventStatistic.incEvent("C");
        eventStatistic.incEvent("B");

        statistic = eventStatistic.getAllEventStatistic();
        for (String eventName : statistic.keySet()) {
            assertEquals(statistic.get(eventName), eventStatistic.getEventStatisticByName(eventName), EPS);
        }
    }

    @Test
    public void testPrintStatistic() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EventStatistic eventStatistic = new EventStatisticImpl(clock, new PrintStream(baos, true, StandardCharsets.UTF_8));
        Instant now = LocalDateTime.of(2023, 1, 21, 18, 29).toInstant(ZoneOffset.UTC);
        clock.setInstant(now);
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("B");

        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        eventStatistic.printStatistic();
        String[] lines = baos.toString(StandardCharsets.UTF_8).split(System.lineSeparator());
        assertPrintStatistic(statistic, lines);
    }

    @Test
    public void testPrintStatisticAfterEviction() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EventStatistic eventStatistic = new EventStatisticImpl(clock, new PrintStream(baos, true, StandardCharsets.UTF_8));
        Instant now = LocalDateTime.of(2023, 1, 21, 18, 29).toInstant(ZoneOffset.UTC);
        clock.setInstant(now);
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("A");
        eventStatistic.incEvent("B");

        clock.setInstant(clock.instant().plus(1, ChronoUnit.HOURS));
        eventStatistic.incEvent("C");

        Map<String, Double> statistic = eventStatistic.getAllEventStatistic();
        eventStatistic.printStatistic();
        String[] lines = baos.toString(StandardCharsets.UTF_8).split(System.lineSeparator());
        assertPrintStatistic(statistic, lines);
    }

    private void assertPrintStatistic(Map<String, Double> statistic, String[] lines) {
        assertEquals(statistic.size() + 1, lines.length);
        assertEquals("Event statistic for last hour:", lines[0]);
        for (Map.Entry<String, Double> eventEntry : statistic.entrySet()) {
            assertTrue(Arrays.stream(lines).anyMatch(line -> (eventEntry.getKey() + " = " + eventEntry.getValue() + " RPS").equals(line)));
        }
    }
}

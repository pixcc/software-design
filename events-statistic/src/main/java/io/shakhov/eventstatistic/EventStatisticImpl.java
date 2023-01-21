package io.shakhov.eventstatistic;

import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class EventStatisticImpl implements EventStatistic {

    private final Clock clock;
    private final Map<String, Queue<Instant>> eventToRequests = new HashMap<>();
    private final PrintStream output;

    public EventStatisticImpl(Clock clock) {
        this.clock = clock;
        this.output = System.out;
    }

    public EventStatisticImpl(Clock clock, PrintStream output) {
        this.clock = clock;
        this.output = output;
    }


    @Override
    public void incEvent(String name) {
        eventToRequests.computeIfAbsent(name, k -> new ArrayDeque<>()).add(clock.instant());
    }

    @Override
    public double getEventStatisticByName(String name) {
        return getEventStatistic(name, clock.instant());
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> allEventStatistic = new HashMap<>();
        Instant now = clock.instant();
        for (String eventName : eventToRequests.keySet()) {
            double eventStatistic = getEventStatistic(eventName, now);
            if (eventStatistic != 0) {
                allEventStatistic.put(eventName, eventStatistic);
            }
        }
        return allEventStatistic;
    }

    private double getEventStatistic(String name, Instant now) {
        Queue<Instant> requests = eventToRequests.get(name);
        if (requests == null) {
            return 0;
        }

        ZonedDateTime nowDateTime = ZonedDateTime.ofInstant(now, clock.getZone());
        Instant cutOff = nowDateTime
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .toInstant();
        while (!requests.isEmpty() && requests.peek().isBefore(cutOff)) {
            requests.poll();
        }

        return (double) requests.size() / (nowDateTime.getMinute() + 1);
    }

    @Override
    public void printStatistic() {
        output.println("Event statistic for last hour:");
        Map<String, Double> allEventStatistic = getAllEventStatistic();
        for (Map.Entry<String, Double> eventEntry : allEventStatistic.entrySet()) {
            output.println(eventEntry.getKey() + " = " + eventEntry.getValue() + " RPS");
        }
    }
}

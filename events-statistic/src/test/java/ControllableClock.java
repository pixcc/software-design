import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ControllableClock extends Clock {

    private final Clock systemClock = Clock.systemUTC();
    private Instant instant;

    @Override
    public ZoneId getZone() {
        return systemClock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return systemClock.withZone(zone);
    }

    @Override
    public Instant instant() {
        if (instant == null) {
            return systemClock.instant();
        }
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}

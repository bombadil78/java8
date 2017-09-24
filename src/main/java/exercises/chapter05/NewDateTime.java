package exercises.chapter05;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.util.function.Predicate;

public class NewDateTime {

    public static LocalDate getProgrammersDay(int year) {
        LocalDate firstOfJanuary = LocalDate.of(year, 1, 1);
        return firstOfJanuary.plus(Period.ofDays(255));
    }

    public static TemporalAdjuster next(Predicate<LocalDate> someDate) {
        return (x) -> {
            if (!(x instanceof LocalDate)) throw new UnsupportedOperationException("Use LocalDate!");
            LocalDate myDate = (LocalDate) x;
            do {
                myDate = myDate.plusDays(1);
            } while (!someDate.test(myDate));
            return myDate;
        };
    }

    public static LocalDateTime getArrival(ZonedDateTime from, Duration duration, ZoneId toTimeZone) {
        return from.plus(duration).withZoneSameInstant(toTimeZone).toLocalDateTime();
    }
}

package exercises.chapter05;

import java.time.LocalDate;
import java.time.Period;
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
}

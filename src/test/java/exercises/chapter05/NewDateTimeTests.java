package exercises.chapter05;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;

import static exercises.chapter05.NewDateTime.getProgrammersDay;
import static exercises.chapter05.NewDateTime.next;
import static junit.framework.TestCase.assertEquals;

public class NewDateTimeTests {

    @Test
    public void programmersDay() {
        assertEquals(LocalDate.of(2013, Month.SEPTEMBER, 13), getProgrammersDay(2013));
        assertEquals(LocalDate.of(2014, Month.SEPTEMBER, 13), getProgrammersDay(2014));
        assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 13), getProgrammersDay(2015));
        assertEquals(LocalDate.of(2016, Month.SEPTEMBER, 12), getProgrammersDay(2016));
        assertEquals(LocalDate.of(2017, Month.SEPTEMBER, 13), getProgrammersDay(2017));
    }

    @Test
    public void customAdjuster_lambdaGeneratingFunction() {
        LocalDate bDay = LocalDate.of(1978, 8, 13);
        LocalDate xMas = LocalDate.of(2017, 12, 25);

        TemporalAdjuster nextMonday = next(x -> x.getDayOfWeek().equals(DayOfWeek.MONDAY));

        assertEquals(LocalDate.of(1978, 8, 14), bDay.with(nextMonday));
        assertEquals(LocalDate.of(2018, 1, 1), xMas.with(nextMonday));
    }
}

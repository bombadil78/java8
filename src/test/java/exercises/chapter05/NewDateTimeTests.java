package exercises.chapter05;

import org.junit.Test;

import java.time.*;
import java.time.temporal.TemporalAdjuster;

import static exercises.chapter05.NewDateTime.getArrival;
import static exercises.chapter05.NewDateTime.getProgrammersDay;
import static exercises.chapter05.NewDateTime.next;
import static java.time.temporal.ChronoUnit.DAYS;
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

    @Test
    public void daysAlive() {
        LocalDate born = LocalDate.of(1978, 8, 13);
        LocalDate today = LocalDate.now();
        System.out.println(DAYS.between(born, today));
    }

    @Test
    public void friday13th() {
        LocalDate start = LocalDate.of(1899, 12, 31);
        LocalDate end = LocalDate.of(2000, 1, 1);

        LocalDate day = start;
        while (!day.isEqual(end)) {
            if (day.getDayOfWeek().equals(DayOfWeek.FRIDAY) && day.getDayOfMonth() == 13) {
                System.out.println(day);
            }
            day = day.plusDays(1);
        }
    }

    @Test
    public void tzOne() {
        ZonedDateTime startInLosAngeles = ZonedDateTime.of(
                LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(15, 5)),
                ZoneId.of("America/Los_Angeles")
        );
        Duration flightDuration = Duration.ofHours(10).plus(Duration.ofMinutes(50));
        ZoneId berlinTimeZone = ZoneId.of("Europe/Berlin");

        LocalDateTime localArrivalTimeIs = getArrival(startInLosAngeles, flightDuration, berlinTimeZone);
        LocalDateTime localArrivalTimeShould = LocalDateTime.of(2000, 1, 2, 10, 55);
        assertEquals(localArrivalTimeShould, localArrivalTimeIs);
    }

}

package exercises;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class Chapter02Test {

    @Test
    public void streamCreation_of() {
        Stream<String> s = Stream.of("a", "b", "c");
        assertNotNull(s);
    }

    @Test
    public void streamCreation_generate() {
        Stream<String> s = Stream.generate(this::randomString).limit(100);
        s.forEach(System.out::println);
    }

    @Test
    public void streamCreation_iterate() {
        Stream<Integer> i = Stream.iterate(0, n -> n + 1);
        i.forEach(System.out::println);
    }

    private String randomString() {
        Random rnd = new Random();
        char c = (char) rnd.nextInt(100);
        return String.valueOf(c);
    }

    // Exercise 1)
    @Test
    public void noOfProcessors() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void simpleLoop() {
        int size = 300000000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
    }

    @Test
    public void parallelLoop() {
        int size = 300000000;
        int noOfProcessors = getNoOfProcessors();
        int[] arr = new int[size];

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < getNoOfProcessors(); i++) {
            int from = getFrom(i, size, noOfProcessors);
            int to = getTo(i, size, noOfProcessors);
            System.out.println(String.format("from=%d, to=%d", from, to));
            executorService.execute(new Looper(arr, from, to));
        }
    }

    // Predicate<T super t1> (=> same as Function<T super t1, Boolean>
    @Test
    public void filter_filtersUsingPredicates() {
        Stream<Integer> s = Stream.of(1, 2, 3, null, 4, null, 6);
        Predicate<Integer> cond1 = this::p1;
        Predicate<Object> cond2 = this::p2;
        Predicate<Integer> condAll = cond1.and(cond2);
        List<Integer> found = s.filter(condAll).collect(Collectors.toList());
        assertEquals(3, found.size());
    }

    // Function<T super t1, R extends t2> func
    @Test
    public void map_mapsUsingFunctionWithTAsInputAndRAsOutput() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        Stream<Integer> ss = Stream.of(1, 2, 3);
        List<Object> l = s.map(x -> String.valueOf(x*x)).collect(Collectors.toList());
        List<String> ll = ss.map(x -> String.valueOf(x*x)).collect(Collectors.toList());
        assertEquals(Arrays.asList("1", "4", "9"), ll);
    }

    // Function<T super t1, ? extends Stream<? super t2>
    @Test
    public void flatMap_flatMapsUsing() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        Stream<String> ss = s.flatMap(this::doDouble);
        assertEquals(Arrays.asList("1", "1", "2", "2", "3", "3"), ss.collect(Collectors.toList()));
    }

    // Reductions
    @Test
    public void reduction_general() {
        Stream<Integer> i = Stream.of(1, 2, 3);
        Integer sum = i.reduce(0, (x, y) -> x + y); // identity value and accumulator
        assertEquals(6, sum.intValue());
    }

    @Test
    public void reduction_homeGrownAny() {
        Stream<Integer> i = Stream.of(1, 2, 3);
        Stream<Integer> ii = Stream.of(1, 3, 5);
        boolean containsEven = i.map(x -> x%2== 0).reduce(false, (x, y) -> x || y);
        boolean doesNotContainEven = ii.map(x -> x%2 == 0).reduce(false, (x, y) -> x || y);
        assertTrue(containsEven);
        assertFalse(doesNotContainEven);
    }

    @Test
    public void reduction_outOfTheBoxReductions() {
        Stream<Integer> i0 = Stream.of(1, 2, 3);
        Stream<Integer> i1 = Stream.of(1, 2, 3);
        Stream<Integer> i2 = Stream.of(1, 2, 3);
        Stream<Integer> i3 = Stream.of(1, 2, 3);

        assertFalse(i0.anyMatch(x -> x == 99));
        assertTrue(i1.allMatch(x -> x < 10));
        assertEquals(1, i2.findFirst().get().intValue());
    }

    @Test
    public void optionals() {
        Stream<Integer> i = Stream.of(1, 2, 3);
        Stream<Integer> ii = Stream.of(1, 2, 3);
        Optional<Integer> neverFound = i.filter(x -> x > 10).findAny();
        Optional<Integer> found = ii.filter(x -> x%2 == 0).findAny();

        // check whether found
        assertFalse(neverFound.isPresent());

        // provide default value
        assertEquals(999, neverFound.orElse(999).intValue());
        assertEquals(2, found.orElse(999).intValue());
    }

    // complex reductions like sum, min, max, ...
    @Test
    public void complex_reductions() {

    }

    private Stream<String> doDouble(Object obj) {
        return Stream.of(obj.toString(), obj.toString());
    }

    private boolean p1(Integer i) {
        return i == null ? false : i%2 == 0;
    }

    private boolean p2(Object obj) {
        return obj != null;
    }

    private int getFrom(int i, int size, int noOfProcessors) {
        return i * (size / noOfProcessors);
    }

    private int getTo(int i, int size, int noOfProcessors) {
        return Math.min(getFrom(i + 1, size, noOfProcessors) - 1, size - 1);
    }

    private static class Looper implements Runnable {

        private final int[] arr;
        private final int from;
        private final int to;

        public Looper(int[] arr, int from, int to) {
            this.arr = arr;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            for (int i = from; i < to; i++) {
                arr[i] = i;
            }
        }
    }

    private int getNoOfProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}

package exercises;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

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

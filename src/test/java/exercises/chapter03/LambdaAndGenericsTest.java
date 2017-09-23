package exercises.chapter03;

import excercises.chapter03.LambdaAndGenerics;
import excercises.chapter03.MyColor;
import excercises.chapter03.MyImage;
import excercises.chapter03.Pictures;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static excercises.chapter03.LambdaAndGenerics.map;
import static junit.framework.TestCase.assertEquals;

public class LambdaAndGenericsTest {

    @Test
    public void compose_test() {
        UnaryOperator op1 = (x) -> x;
        UnaryOperator op2 = (x) -> x;
        Function f = (x) -> x;
        MyImage myImage = new MyImage();
        // #1) Legal with own implementation
        // Pictures.transform(myImage, Pictures.compose(op1, op2)); is OK

        // #2) compiles, but gives class cast exception => illegal downcast
        // Pictures.transform(myImage, (UnaryOperator<MyColor>) op1.compose(op2));

        // UnaryOperator<T> extends Function<T, T> while Function<T, T> offers Function<T, T> compose(...)
    }

    @Test
    public void genericsTest() {
        printSize(Arrays.asList(1, 2, 3));
    }

    @Test
    public void anotherGenericsTest() {
        List<String> someList = new ArrayList<>(Arrays.asList("a"));
        List<Integer> someIntList = new ArrayList<>(Arrays.asList(1, 2, 3));
        printStringCollection(someList);
        printStringCollection(new HashSet<>(someList));
        // printStringCollection(someIntList); => does not compile
    }

    @Test
    public void convertGenericLists() {
        List<String> s = new ArrayList<>(Arrays.asList("1", "2", "3"));
        List<Integer> i = map(
                s,
                (x) -> {
                    Integer val = Integer.valueOf(x);
                    return 2 * val;
                });
        assertEquals(Arrays.asList(2, 4, 6), i);
    }

    @Test
    public void someReduceTest() {
        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3"));
        Integer sum = list.stream()
                .reduce(
                        0,
                        (acc, element) -> acc + Integer.parseInt(element),
                        (part0, part1) -> part0 + part1
                );
        assertEquals(6, sum.intValue());
    }

    private static <T> void printSize(Collection<T> collection) {
        System.out.println(collection.size());
    }

    private static <T> void printStringCollection(Collection<? extends String> collection) {
        // do nth
    }
}

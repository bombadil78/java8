package excercises.chapter03;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaAndGenerics {

    public static <T, U> List<U> map(List<T> someList, Function<T, U> someFunc) {
        return someList.stream().map(el -> someFunc.apply(el)).collect(Collectors.toList());
    }

    public static <T, U> Pair<U> map(Pair<T> somePair, Function<T, U> someFunc) {
        U first = someFunc.apply(somePair.getFirst());
        U second = someFunc.apply(somePair.getSecond());
        return new Pair<>(first, second);
    }
}

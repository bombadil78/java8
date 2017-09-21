package excercises.chapter03;

import java.util.function.BiFunction;

public class LamdaGenerator {

    public static BiFunction<String, String, String> multiConcat(int times) {
        return (x, y) -> repeat(x, times) + repeat(y, times);
    }

    public static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}

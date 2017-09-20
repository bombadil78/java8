package excercises.chapter03;

import java.util.Comparator;

public class CustomizingComparator {

    public static Comparator<String> generate(
            boolean isAscending, boolean isCaseSensitive, boolean isSpaceSensitive)
    {
        return (String x, String y) -> {
            if (isSpaceSensitive) {
                x = x.replaceAll("\\s", "z");
                y = y.replaceAll("\\s", "z");
            }

            if (!isCaseSensitive) {
                x = x.toLowerCase();
                y = y.toLowerCase();
            }

            if (isAscending) {
                int res = x.compareTo(y);
                return res;
            } else {
                int res = x.compareTo(y) * -1;
                return res;
            }
        };
    }
}

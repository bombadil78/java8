package exercises.chapter03;

import excercises.chapter03.CustomizingComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CustomizingComparatorTest {

    private List<String> data = new ArrayList<>();

    @Before
    public void init() {
        data.add("A");
        data.add("B");
        data.add("a");
        data.add("a b");
        data.add("a  a");
        // order: a A bb B Bb
    }

    @Test
    public void normalSortingOrder() {
        data.sort(new Comparator<String>() {
            @Override
            public int compare(String x, String y) {
                return x.compareTo(y);
            }
        });
        System.out.println(data);
    }

    @Test
    public void compare_ascendingAndCaseSensitiveAndSpaceSensitive() {
        data.sort(CustomizingComparator.generate(true, true, true));
        assertEquals(Arrays.asList("A", "B", "a", "a b", "a  a"), data);
    }

    @Test
    public void compare_descendingAndCaseSensitiveAndSpaceInsensitive() {
        data.sort(CustomizingComparator.generate(false, true, true));
        assertEquals(Arrays.asList("a b", "a  a", "a", "B", "A"), data);
    }

    @Test
    public void compare_ascendingAndCaseInsensitiveAndSpaceSensitive() {
        data.sort(CustomizingComparator.generate(true, false, false));
        assertEquals(Arrays.asList("A", "a", "a  a", "a b", "B"), data);
    }
}
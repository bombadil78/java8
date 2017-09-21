package exercises.chapter03;

import excercises.chapter03.LamdaGenerator;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LambdaGeneratorTest {

    @Test
    public void tripleIt() {
        String s = "s";
        String t = "t";
        assertEquals("sssttt", LamdaGenerator.multiConcat(3).apply(s, t));
    }
}

package excercises.chapter03;

import java.util.function.UnaryOperator;

public class Pictures {

    public static MyImage transform(MyImage image, UnaryOperator<MyColor> op) {
        return null;
    }

    public static <T> UnaryOperator<T> compose(UnaryOperator<T> op1, UnaryOperator<T> op2) {
        return (x) -> op1.apply(op2.apply(x));
    }
}

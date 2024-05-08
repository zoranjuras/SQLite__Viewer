import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (x, y) -> {
        long result = x;
        if (x != y) {
            result = 1;
            for (long i = x; i <= y; i++) {
                result *= i;
            }
        }
        return result;
    };
}
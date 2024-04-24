package baseball.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Count {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 3;
    private static final Map<Integer, Count> FACTORY = IntStream.rangeClosed(MIN_VALUE, MAX_VALUE)
            .boxed()
            .map(Count::new)
            .collect(Collectors.toMap(Count::getValue, Function.identity()));

    private final int value;

    private Count(final int value) {
        validateCount(value);
        this.value = value;
    }

    public static Count of(final int value) {
        return FACTORY.get(value);
    }

    private void validateCount(final int count) {
        if (count < MIN_VALUE || count > MAX_VALUE) {
            throw new IllegalArgumentException("0~3개만 가능합니다.");
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isMinValue() {
        return this.value == MIN_VALUE;
    }

    public boolean isMaxValue() {
        return this.value == MAX_VALUE;
    }
}

package baseball.domain;

import java.util.List;

public class Computer {
    private final BaseBallNumbers numbers;

    public Computer(final List<Number> numbers) {
        this.numbers = new BaseBallNumbers(numbers);
    }

    public Count getStrikeCount(final BaseBallNumbers playerNumbers) {
        final int strikeCount = (int) playerNumbers.numbers().stream()
                .filter(number -> isStrike(number, playerNumbers.indexOf(number)))
                .count();

        return Count.of(strikeCount);
    }

    public Count getBallCount(final BaseBallNumbers playerNumbers, final Count strikeCount) {
        final int ballCount = (int) playerNumbers.numbers()
                .stream()
                .filter(this::isBall)
                .count();

        return Count.of(ballCount - strikeCount.getValue());
    }

    private boolean isBall(final Number number) {
        return numbers.isContains(number);
    }

    private boolean isStrike(final Number number, final int index) {
        return numbers.isSameIndexOf(number, index);
    }
}

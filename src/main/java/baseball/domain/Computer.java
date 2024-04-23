package baseball.domain;

import java.util.List;

public class Computer {
    private final BaseBallNumbers numbers;

    public Computer(final List<Number> numbers) {
        this.numbers = new BaseBallNumbers(numbers);
    }

    public int getStrikeCount(final BaseBallNumbers playerNumbers) {
        return (int) playerNumbers.numbers().stream()
                .filter(number -> isStrike(number, playerNumbers.indexOf(number)))
                .count();
    }

    public int getBallCount(final BaseBallNumbers playerNumbers, final int strikeCount) {
        final int totalBallCount = (int) playerNumbers.numbers()
                .stream()
                .filter(this::isBall)
                .count();

        return totalBallCount - strikeCount;
    }

    private boolean isBall(final Number number) {
        return numbers.isContains(number);
    }

    private boolean isStrike(final Number number, final int index) {
        return numbers.isSameIndexOf(number, index);
    }
}

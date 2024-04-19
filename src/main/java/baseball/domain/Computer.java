package baseball.domain;

import java.util.List;

public class Computer {
    private final BaseBallNumbers numbers;

    public Computer(List<Number> numbers) {
        this.numbers = new BaseBallNumbers(numbers);
    }

    public int getStrikeCount(BaseBallNumbers playerNumbers) {
        return (int) playerNumbers.numbers().stream()
                .filter(number -> isStrike(number, playerNumbers.indexOf(number)))
                .count();
    }

    public int getBallCount(BaseBallNumbers playerNumbers, int strikeCount) {
        int totalBallCount = (int) playerNumbers.numbers().stream()
                .filter(this::isBall)
                .count();

        return totalBallCount - strikeCount;
    }

    private boolean isBall(Number number) {
        return numbers.isContains(number);
    }

    private boolean isStrike(Number number, int index) {
        return numbers.isSameIndexOf(number, index);
    }
}

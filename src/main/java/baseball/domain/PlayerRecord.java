package baseball.domain;

import java.util.List;

public class PlayerRecord {
    private final BaseBallNumbers numbers;

    private final Count strikeCount;

    private final Count ballCount;

    public PlayerRecord(BaseBallNumbers numbers, int strikeCount, int ballCount) {
        this.numbers = numbers;
        this.strikeCount = new Count(strikeCount);
        this.ballCount = new Count(ballCount);
    }

    public List<Integer> getValueNumbers() {
        return numbers.getValueNumbers();
    }

    public int getStrikeCount() {
        return strikeCount.value();
    }

    public int getBallCount() {
        return ballCount.value();
    }

    public boolean isNotting() {
        return ballCount.isMinValue() && strikeCount.isMinValue();
    }

    public boolean isSuccess() {
        return strikeCount.isMaxValue();
    }
}

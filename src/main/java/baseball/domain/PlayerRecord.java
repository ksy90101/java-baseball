package baseball.domain;

import java.util.List;

public class PlayerRecord {
    private final BaseBallNumbers numbers;

    private final Count strikeCount;

    private final Count ballCount;

    public PlayerRecord(final BaseBallNumbers numbers, final Count strikeCount, final Count ballCount) {
        validateCount(strikeCount, ballCount);
        this.numbers = numbers;
        this.strikeCount = strikeCount;
        this.ballCount = ballCount;
    }

    public List<Integer> getValueNumbers() {
        return numbers.getValueNumbers();
    }

    private void validateCount(final Count strikeCount, final Count ballCount) {
        final int totalCount = strikeCount.getValue() + ballCount.getValue();
        if (totalCount > BaseBallNumbers.TOTAL_COUNT) {
            throw new IllegalArgumentException("스트라이크와 볼의 합은 3개를 넘을 수 없습니다.");
        }
    }

    public int getStrikeCount() {
        return strikeCount.getValue();
    }

    public int getBallCount() {
        return ballCount.getValue();
    }

    public boolean isNotting() {
        return ballCount.isMinValue() && strikeCount.isMinValue();
    }

    public boolean isSuccess() {
        return strikeCount.isMaxValue();
    }
}

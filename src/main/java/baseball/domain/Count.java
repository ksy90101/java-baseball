package baseball.domain;

public record Count(int value) {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 3;

    public Count {
        validateCount(value);
    }

    private void validateCount(int count) {
        if (count < MIN_VALUE || count > MAX_VALUE) {
            throw new IllegalArgumentException("0~3개만 가능합니다.");
        }
    }

    public boolean isMinValue() {
        return this.value == MIN_VALUE;
    }

    public boolean isMaxValue() {
        return this.value == MAX_VALUE;
    }
}

package baseball.domain;

import java.util.Objects;

public record PlayerTimes(
        int value
) {

    public static final int MIN_PLAYER_TIMES = 1;

    public PlayerTimes {
        validatePlayerTimes(value);
    }

    private void validatePlayerTimes(int playerTimes) {
        if (playerTimes < MIN_PLAYER_TIMES) {
            throw new IllegalArgumentException("1 이상만 가능합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerTimes that = (PlayerTimes) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

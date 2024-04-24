package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlayerRecordTest {
    private static final Count ZERO_COUNT = Count.of(0);
    private static final Count THREE_COUNT = Count.of(3);

    @DisplayName("총 볼과 스트라이크의 갯수가가 4개 이상인 경우에 예외가 발생한다.")
    @Test
    void validateCountTest() {
        final BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        
        assertThatThrownBy(() -> new PlayerRecord(plyerNumbers, Count.of(2), Count.of(3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("스트라이크와 볼의 합은 3개를 넘을 수 없습니다.");
    }

    @DisplayName("스트라이트와 볼 갯수가 0개면 낫싱이다.")
    @Test
    void isNottingTest() {
        final BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, ZERO_COUNT, ZERO_COUNT);
        assertThat(playerRecord.isNotting()).isTrue();
    }

    @DisplayName("스트라이트와 볼 갯수가 0개가 아니면 낫싱이 아니다.")
    @ParameterizedTest
    @CsvSource(value = {"1,0", "0,1", "1,1"})
    void isNottingTest2(final int ballCount, final int strikeCount) {
        final BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, Count.of(strikeCount), Count.of(ballCount));
        assertThat(playerRecord.isNotting()).isFalse();
    }

    @Test
    @DisplayName("스트라이크 갯수가 3개면 성공이다.")
    void isSuccessTest() {
        final BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, THREE_COUNT, ZERO_COUNT);
        assertThat(playerRecord.isSuccess()).isTrue();
    }

    @DisplayName("스트라이크 갯수가 3개가 아니면 성공이 아니다.")
    @ParameterizedTest
    @CsvSource(value = {"1,1", "0,2"})
    void isSuccessTest2(final int ballCount, final int strikeCount) {
        final BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, Count.of(strikeCount), Count.of(ballCount));
        assertThat(playerRecord.isSuccess()).isFalse();
    }
}

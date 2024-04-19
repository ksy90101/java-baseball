package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerRecordTest {
    @DisplayName("스트라이트와 볼 갯수가 0개면 낫싱이다.")
    @Test
    void isNottingTest() {
        BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, 0, 0);
        assertThat(playerRecord.isNotting()).isTrue();
    }

    @DisplayName("스트라이트와 볼 갯수가 0개가 아니면 낫싱이 아니다.")
    @ParameterizedTest
    @CsvSource(value = {"1,0", "0,1", "1,1"})
    void isNottingTest2(int ballCount, int strikeCount) {
        BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, strikeCount, ballCount);
        assertThat(playerRecord.isNotting()).isFalse();
    }

    @Test
    @DisplayName("스트라이크 갯수가 3개면 성공이다.")
    void isSuccessTest() {
        BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, 3, 0);
        assertThat(playerRecord.isSuccess()).isTrue();
    }

    @DisplayName("스트라이크 갯수가 3개가 아니면 성공이 아니다.")
    @ParameterizedTest
    @CsvSource(value = {"1,1", "0,2"})
    void isSuccessTest2(int ballCount, int strikeCount) {
        BaseBallNumbers plyerNumbers = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(plyerNumbers, strikeCount, ballCount);
        assertThat(playerRecord.isSuccess()).isFalse();
    }
}

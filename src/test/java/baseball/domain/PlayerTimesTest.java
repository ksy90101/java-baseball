package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PlayerTimesTest {

    @DisplayName("1 미만의 값이 주어지면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"0", "-1",})
    void validatePlayerTimesTest(int value) {
        assertThatThrownBy(() -> new PlayerTimes(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1 이상만 가능합니다.");

    }

    @DisplayName("1 이상의 값이 주어지면 정상적으로 생성된다.")
    @Test
    void createPlayerTimesTest() {
        assertDoesNotThrow(() -> new PlayerTimes(1));
    }

}

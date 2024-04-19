package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CountTest {

    @DisplayName("최소 수인 경우에는 true를 반환한다.")
    @Test
    void isMinValueTest() {
        Count count = new Count(0);
        assertThat(count.isMinValue()).isTrue();
    }

    @DisplayName("최소 수가 아닌 경우에는 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void isNotMinValueTest(int value) {
        Count count = new Count(value);
        assertThat(count.isMinValue()).isFalse();
    }

    @DisplayName("최대 수인 경우에는 true를 반환한다.")
    @Test
    void isMaxValueTest() {
        Count count = new Count(3);
        assertThat(count.isMaxValue()).isTrue();
    }

    @DisplayName("최대 수가 아닌 경우에는 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void isNotMaxValueTest(int value) {
        Count count = new Count(value);
        assertThat(count.isMaxValue()).isFalse();
    }

    @DisplayName("0~3개만 가능합니다. 에러 메시지를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 4})
    void validateCountTest(int value) {
        assertThatThrownBy(() -> new Count(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0~3개만 가능합니다.");
    }
}
